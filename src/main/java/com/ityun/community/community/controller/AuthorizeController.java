package com.ityun.community.community.controller;

import com.ityun.community.community.dto.AccessTokenDTO;
import com.ityun.community.community.dto.GithubUser;
import com.ityun.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    @GetMapping(value = "/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(Redirect_uri);

        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(access_token);
        System.out.println(user);
        return "index";
    }
}
