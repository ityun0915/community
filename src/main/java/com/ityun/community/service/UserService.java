package com.ityun.community.service;

import com.ityun.community.model.User;


public interface UserService {
    void insert(User user);  //添加用户信息
    User findByToken(String token); //token查询用户
    User findByAccountId(String account_id); //account_id查询用户
}
