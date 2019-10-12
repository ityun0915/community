package com.ityun.community.service;

import com.ityun.community.model.User;


public interface UserService {
    void insert(User user);  //添加用户信息
    User findByToken(String token); //token查询用户
}
