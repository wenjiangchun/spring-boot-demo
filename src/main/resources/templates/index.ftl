<!DOCTYPE HTML>
<html>
<head>
<#assign ctx=springMacroRequestContext.contextPath>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${ctx}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/prism/prism.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="${ctx}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx}/prism/prism.js"></script>

</head>
<body>
<pre class="line-numbers  language-java"><code class="language-java">package com.haze.springboot;

import com.haze.springboot.zookeeper.ZooKeeperUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {

    static Logger logger = LoggerFactory.getLogger(Application.class);

    public static String FILE_PATH = "/home/hadoop/testcase";

    public static String HOST_PATH = "hosts";

    public static String CASE_PATH = "testcase";

    public static void main(String[] args) {
       /* ApplicationContext ctx = SpringApplication.run(Application.class, args);*/
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBanner((environment, sourceClass, out) -> {
            out.println("Welcome use Spring Boot!");
        });
        springApplication.run(args);
        /*if (!Files.exists(Paths.get(FILE_PATH))) {
            try {
                Files.createDirectories(Paths.get(FILE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.debug("system start completed, now register host node to zookeeper......");
        ZooKeeperUtils zk = new ZooKeeperUtils();
        try {
            zk.connect("192.168.20.207,192.168.20.208");
            zk.create(HOST_PATH + "/" + InetAddress.getLocalHost().getHostName(), InetAddress.getLocalHost().getHostName(), CreateMode.EPHEMERAL);
            logger.debug("host node registered success!");
            //zk.start();
            zk.read("zoo", zk);
            zk.getChildNode("/zoo", zk);
        } catch (InterruptedException | IOException e) {
            logger.error("connected zookeeper failure!",e);
        } catch (KeeperException e) {
            e.printStackTrace();
        }*/
    }
}

</code></pre>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <div class="panel panel-primary">
                <div class="panel-heading">数据信息</div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>id信息</th>
                            <th>用例信息</th>
                            <th>节点路径</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-primary">
                <div class="panel-heading">可用服务器信息</div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>服务器名称</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>



    <form role="form" action="${ctx}/saveQuestion" class="form-horizontal" method="post">
        <div class="form-group">
            <label for="exampleInputEmail1">节点ID</label>
            <input type="text" class="form-control" name="id" required value="${questionInfo.id!}">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">用例信息</label>
            <textarea name="data" required class="form-control">${questionInfo.data!}</textarea>
        </div>
        <button type="submit" class="btn btn btn-success">Submit</button>
    </form>
</div>


</body>
</html>