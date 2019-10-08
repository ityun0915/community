package com.ityun.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.ityun.community.community.dto.AccessTokenDTO;
import com.ityun.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    /**
     * 获取github的token值
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        /*okhttp传输*/
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //String 的值为:access_token=cd425669ad6b6375613023f7a404cecf9b44d541&scope=user&token_type=bearer
            String access_token = string.split("&")[0].split("=")[1];
            return access_token;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取github的用户信息(通过accessToken)
     * @return
     */
    public GithubUser getUser(String accessToken) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
