package com.haze.springboot.web.controller;

import com.haze.springboot.Application;
import com.haze.springboot.entity.QuestionInfo;
import com.haze.springboot.zookeeper.ZooKeeperUtils;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Sofar on 2016/8/24.
 */
@Controller
public class GreetingController {

    private List<QuestionInfo> questionInfoList = new ArrayList<>();

    private ZooKeeperUtils zooKeeperUtils;

    public void setZooKeeperUtils(ZooKeeperUtils zooKeeperUtils) {
        this.zooKeeperUtils = zooKeeperUtils;
    }

    @RequestMapping("/index")
    public String index(Model model) throws Exception {
        model.addAttribute("questionList", questionInfoList);
        //查询当前服务可用信息
       // ZooKeeperUtils zk = new ZooKeeperUtils();
        //List<String> hosts = zk.getChildNode(Application.HOST_PATH, null);
        if (!model.containsAttribute("questionInfo")) {
            model.addAttribute("questionInfo", new QuestionInfo());
        }
       /* List<String> hosts = zooKeeperUtils.getChildNode("/" + Application.HOST_PATH, zooKeeperUtils);
        model.addAttribute("hosts", hosts);*/
        return "index";
    }

    @RequestMapping("/saveQuestion")
    public String saveQuestion(QuestionInfo questionInfo, Model model) {

        if (!questionInfoList.contains(questionInfo)) {
             //同步 增加
            questionInfoList.add(questionInfo);
            try {
                zooKeeperUtils.create(Application.CASE_PATH + "/" + questionInfo.getId(), questionInfo.getData(), CreateMode.PERSISTENT);
            } catch (Exception e) {
                System.out.println("创建节点失败");
            }

        } else {
            for (QuestionInfo info : questionInfoList) {
                if (info.getId().equals(questionInfo.getId()) && !info.getData().equals(questionInfo.getData())) {
                    //同步 先删除再增加

                    info.setData(questionInfo.getData());
                    try {
                        zooKeeperUtils.delete(Application.CASE_PATH + "/" + questionInfo.getId());
                        zooKeeperUtils.create(Application.CASE_PATH + "/" + questionInfo.getId(), questionInfo.getData(), CreateMode.PERSISTENT);
                    } catch (Exception e) {
                        System.out.println("创建节点失败");
                    }
                }
            }
        }
        return "redirect:/index";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id, Model model) {

        for (QuestionInfo questionInfo : questionInfoList) {
            if ( questionInfo.getId().equals(id)) {
                questionInfoList.remove(questionInfo);
                try {
                    zooKeeperUtils.delete(Application.CASE_PATH + "/" + questionInfo.getId());
                } catch (Exception e) {
                    System.out.println("创建节点失败");
                }
                break;
            }
        }
        model.addAttribute("questionInfoList", questionInfoList);
        try {
            return index(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/show/{id}")
    public String show(@PathVariable String id, Model model) {
        questionInfoList.forEach(questionInfo -> {
            if (questionInfo.getId().equals(id)) {
               model.addAttribute("questionInfo", questionInfo);
            }
        });
        model.addAttribute("questionInfoList", questionInfoList);
        try {
            return index(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void zip(Path p, ZipOutputStream out) throws IOException {
        String base = p.getFileName().toString();
        p.forEach(path -> {
            System.out.println(path);
        });
        if (Files.isDirectory(p)) {
            Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    Path r = p.relativize(file);
                    out.putNextEntry(new ZipEntry(base + "/" + r.toString()));
                    Files.copy(file, out);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir,
                                                         BasicFileAttributes attrs) throws IOException {
                    Path r = p.relativize(dir);
                    if (!r.getFileName().toString().equals("")) {
                        out.putNextEntry(new ZipEntry(base + "/" + r.toString() + "/"));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc)
                        throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                                                          IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            out.putNextEntry(new ZipEntry(p.getFileName().toString()));
            Files.copy(p, out);
        }
    }
}
