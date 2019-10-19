package com.ityun.community.controller;


import com.ityun.community.listener.NettyListener;
import com.ityun.community.model.User;
import com.ityun.community.service.UserService;
import com.ityun.community.utils.CookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfoUI")
    public String userInfoUI(String account_id,HttpServletRequest request){

        //获取
        User user = userService.findByAccountId(account_id);
        request.getSession().setAttribute("questUser",user);
        System.out.println("questUser:"+user);

        return "user_info";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //移除存储user的session
        request.getSession().invalidate();
        //移除cookie中的token
        Cookie cookie = CookUtils.getCookieByName("token",request.getCookies());
        Cookie newCookie = new Cookie(cookie.getName(),null);
        newCookie.setMaxAge(0);
        response.addCookie(newCookie);

        System.out.println("在线人数:"+ NettyListener.online);

//        System.out.println("session:" +request.getSession().getAttribute("user"));
//        System.out.println("token:"+newCookie.getValue());
        return "redirect:/";
    }
}
