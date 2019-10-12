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
        //判断该用户是否存在
        System.out.println("判断数据库是否存在该User信息...");
        User hasUser = userMapper.findUserByAccount_id(user.getAccount_id());

        if (hasUser != null){
            //存在该用户,更新信息
            System.out.println("存在该User:"+hasUser.getName()+"正在更新User的token...");
            userMapper.updateToken(user);
        }else {
            //不存在该用户,插入
            userMapper.insert(user);
        }
    }

    @Override
    public User findByToken(String token) {
       return userMapper.findUserByToken(token);
    }


}
