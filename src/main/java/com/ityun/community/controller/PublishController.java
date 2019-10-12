package com.ityun.community.controller;

import com.ityun.community.model.Question;
import com.ityun.community.model.User;
import com.ityun.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/doPublish")
    public String doPublish(Question question,
                            HttpServletRequest request,
                            Model model){

        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());

        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        User user = (User)request.getSession().getAttribute("user");

        if (user == null){
            model.addAttribute("error","用户未登陆！");
            return "publish";
        }

        question.setCreator(user.getId());
        questionService.create(question);

        return "redirect:/";
    }
}
