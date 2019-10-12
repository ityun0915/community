package com.ityun.community.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Question implements Serializable {
    private static final long serialVersionUID = -7448353437209946860L;
    private Integer id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private String tag;

}
