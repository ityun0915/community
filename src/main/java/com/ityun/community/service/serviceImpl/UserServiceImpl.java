package com.ityun.community.service.serviceImpl;

import com.ityun.community.dto.QuestionDTO;
import com.ityun.community.mapper.UserMapper;
import com.ityun.community.model.Question;
import com.ityun.community.model.User;
import com.ityun.community.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<QuestionDTO> findQuestionDTOs() {

        List<QuestionDTO> list = new ArrayList<>();
        List<Question> quest = userMapper.findQuest();
        //遍历List<Question>
        for (Question question:quest ){

            //每一个question都要有一个questionDTO对应
            QuestionDTO questionDTO = new QuestionDTO();

             User user = userMapper.findUser(question.getCreator());
            questionDTO.setUser(user);
            //将question的属性全部设置进questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            //将所有questionDto对象添加进list
            list.add(questionDTO);
        }
        return list;
    }

}
