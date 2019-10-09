package com.ityun.community.dto;
import lombok.Data;

/**
 * okhttp网络传输 调取 github登陆接口
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
