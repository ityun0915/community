package com.ityun.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String email;
    private String company;
    private String avatar_url;

}
