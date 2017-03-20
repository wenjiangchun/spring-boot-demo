package com.haze.springboot.web.controller;

import com.haze.springboot.dao.QuestionInfoRepository;
import com.haze.springboot.entity.QuestionInfo;
import com.haze.springboot.service.QuestionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionInfoController {

    private QuestionInfoService questionInfoService;

    @Autowired
    public void setQuestionInfoService(QuestionInfoService questionInfoService) {
        this.questionInfoService = questionInfoService;
    }

    @RequestMapping("/index")
    public String index(Model model) throws Exception {
        List<QuestionInfo> questionInfoList = questionInfoService.findAll();
        model.addAttribute("questionList", questionInfoList);
        return "question/index";
    }

    @RequestMapping("/save")
    public String save(Model model, QuestionInfo questionInfo) throws Exception {
        this.questionInfoService.save(questionInfo);
        return "redirect:/question/index";
    }

    @RequestMapping("/read/{id}")
    public String read(Model model, @PathVariable String id) {
       this.questionInfoService.read(id);
       return "redirect:/question/index";
    }
}
