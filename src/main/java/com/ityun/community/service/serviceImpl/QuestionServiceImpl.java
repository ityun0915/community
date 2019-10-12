package com.ityun.community.service.serviceImpl;

import com.ityun.community.dto.QuestionDTO;
import com.ityun.community.mapper.QuestionMapper;
import com.ityun.community.mapper.UserMapper;
import com.ityun.community.model.Question;
import com.ityun.community.model.User;
import com.ityun.community.service.QuestionService;
import com.ityun.community.utils.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void create(Question question) {     //创建question
        questionMapper.create(question);
        //将redis中的questionDTOs全部弹出
        for (int i=0;i<questionMapper.count();i++){
//            System.out.println("弹出了...");
            redisTemplate.opsForList().rightPop("questionDTOs");
        }
    }
    @Override
    public List<QuestionDTO> findQuestionDTOs() {   //查询所有的questionDTO对象

        List<QuestionDTO> list = new ArrayList<>();

        //判断redis是否存在questions
        boolean haskey = redisUtil.exists("questionDTOs");
        if (haskey){

            List<QuestionDTO> questionDTOs = redisTemplate.opsForList().range("questionDTOs", 0, -1);

System.out.println("redis存在questionDTOs...");
System.out.println("questionDTOs:" + questionDTOs);
            return questionDTOs;

        }else {
    System.out.println("redis不存在questionDTOs...");
            List<Question> quest = questionMapper.findQuest();
            //遍历List<Question>
            for(Question question:quest){
                //每一个question都要有一个questionDTO对应
                QuestionDTO questionDTO = new QuestionDTO();

                User user = userMapper.findUserByCreator(question.getCreator());
                questionDTO.setUser(user);
                //将question的属性全部设置进questionDTO
                BeanUtils.copyProperties(question,questionDTO);

                //将question的id作为key,question作为值 存入redis
//                redisTemplate.opsForValue().set("questionDTO_"+questionDTO.getId(),questionDTO);
                redisTemplate.opsForList().rightPush("questionDTOs",questionDTO);
                //将所有questionDto对象添加进list
                list.add(questionDTO);
            }
            return list;
        }
    }
}
