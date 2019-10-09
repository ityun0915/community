package com.ityun.community.service;

import com.ityun.community.dto.QuestionDTO;
import com.ityun.community.model.User;

import java.util.List;

public interface UserService {
    void insert(User user);  //添加用户信息
    User findByToken(String token); //token查询用户
    List<QuestionDTO> findQuestionDTOs();//查询所有问题
}
