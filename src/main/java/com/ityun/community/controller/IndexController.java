package com.ityun.community.controller;

import com.ityun.community.mapper.UserMapper;
import com.ityun.community.model.User;
import com.ityun.community.service.UserService;
import com.ityun.community.utils.CookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookUtils.getCookieByName("token", cookies);
        String token = cookie.getValue();
        User user = userService.findByToken(token);
System.out.println(user);

        if (user != null){
            // 将user存入session
            request.getSession().setAttribute("user",user);
        }
        return "index";

    }

}
