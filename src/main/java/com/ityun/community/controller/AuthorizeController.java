package com.ityun.community.controller;

import com.ityun.community.dto.AccessTokenDTO;
import com.ityun.community.dto.GithubUser;
import com.ityun.community.model.User;
import com.ityun.community.provider.GithubProvider;
import com.ityun.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;


    @Value("${Client_id}")
    private String Client_id;
    @Value("${Client_secret}")
    private String Client_secret;
    @Value("${Redirect_uri}")
    private String Redirect_uri;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/callback")        //github登录回滚
    public String callback(@RequestParam(value = "code") String code,
                           @RequestParam(value = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(Redirect_uri);

        //获取access_token
        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        //获取user信息
        GithubUser githubUser = githubProvider.getUser(access_token);
        System.out.println(githubUser);
        if (githubUser != null) {
            //user存在
            User user = new User();
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setBio(githubUser.getBio());
            user.setEmail(githubUser.getEmail());
            user.setCompany(githubUser.getCompany());
            user.setImage_url(githubUser.getAvatar_url());
System.out.println("loginUser:"+user.getName()+"的所有信息:"+user);

            //将用户信息存入数据库
            userService.insert(user);
            //将token值传入cookie
            Cookie user_token = new Cookie("token", token);
            response.addCookie(user_token);

            return "redirect:/";
        } else {
            //user不存在,重定向到错误页面
            return "redirect:/";
        }

    }
}
