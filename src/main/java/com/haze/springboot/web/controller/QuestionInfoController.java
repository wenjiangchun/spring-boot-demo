package com.haze.springboot.web.controller;

import com.haze.springboot.dao.QuestionInfoRepository;
import com.haze.springboot.entity.QuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionInfoController {


    private QuestionInfoRepository questionInfoRepository;

    @Autowired
    public void setQuestionInfoRepository(QuestionInfoRepository questionInfoRepository) {
        this.questionInfoRepository = questionInfoRepository;
    }

    @RequestMapping("/index")
    public String index(Model model) throws Exception {
        List<QuestionInfo> questionInfoList = questionInfoRepository.findAll();
        model.addAttribute("questionList", questionInfoList);
        return "question/index";
    }

    @RequestMapping("/save")
    public String save(Model model, QuestionInfo questionInfo) throws Exception {
        this.questionInfoRepository.save(questionInfo);
        return "redirect:/question/index";
    }

    @RequestMapping("/getNames")
    public String getNames(Model model) {
        List<String> nameList = new ArrayList<>();
        nameList.add("河北");
        nameList.add("河南");
        model.addAttribute("nameList", nameList);
       return "question/getNames";
    }
}
