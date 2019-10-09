package com.ityun.community.controller;

import com.ityun.community.dto.AccessTokenDTO;
import com.ityun.community.dto.GithubUser;
import com.ityun.community.mapper.UserMapper;
import com.ityun.community.model.User;
import com.ityun.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    private UserMapper userMapper;

    @GetMapping(value = "/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
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
        if (githubUser != null){
            //user存在
            User user = new User();
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
System.out.println(user);

            //将token值传入cookie
            response.addCookie(new Cookie("token",token));

            //将用户信息存入数据库
            userMapper.insert(user);
            // 将user存入session
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            //user不存在,重定向到错误页面
            return "redirect:/";
        }

    }
}
