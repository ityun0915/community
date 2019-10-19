package com.ityun.community.controller;

import com.ityun.community.dto.QuestionDTO;
import com.ityun.community.listener.NettyListener;
import com.ityun.community.model.User;
import com.ityun.community.service.QuestionService;
import com.ityun.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @GetMapping(value = "/")
    public String index(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie c : cookies) {
                //通过名称获取
                if("token".equalsIgnoreCase(c.getName())){
                    //返回
                    String token = c.getValue();
                    User user = userService.findByToken(token);
                    if (user != null){
                        // 将user存入session
                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(60*60);
                    session.setAttribute("user",user);

        System.out.println("正在登录用户信息:"+user);
        System.out.println(user.getName()+"上线,在线人数:"+ NettyListener.online);
                    }
                }
            }
        }
        //查询带有的问题信息
        List<QuestionDTO> list = questionService.findQuestionDTOs();
        model.addAttribute("list",list);

        return "index";

    }

}
