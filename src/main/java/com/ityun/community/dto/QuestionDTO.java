package com.ityun.community.dto;

import com.ityun.community.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {

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
    private User user;
}
