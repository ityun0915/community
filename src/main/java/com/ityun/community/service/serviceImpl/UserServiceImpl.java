package com.ityun.community.service.serviceImpl;

import com.ityun.community.mapper.UserMapper;
import com.ityun.community.model.User;
import com.ityun.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
        public void insert(User user){  //添加用户信息
            userMapper.insert(user);
    }

    @Override
    public User findByToken(String token) {
       return userMapper.findByToken(token);
    }
}
