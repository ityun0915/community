package com.ityun.community.service.serviceImpl;

import com.ityun.community.mapper.QuestionMapper;
import com.ityun.community.model.Question;
import com.ityun.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public void create(Question question) {
        questionMapper.create(question);
    }
}
