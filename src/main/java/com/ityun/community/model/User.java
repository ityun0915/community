package com.ityun.community.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 7448353437209946860L;
    private Integer id;
    private String name;
    private String account_id;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
    private String bio;
    private String email;
    private String company;
    private String image_url;

}
