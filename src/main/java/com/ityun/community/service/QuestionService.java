package com.ityun.community.service;

import com.ityun.community.dto.QuestionDTO;
import com.ityun.community.model.Question;

import java.util.List;

public interface QuestionService {
    void create(Question question);
    List<QuestionDTO> findQuestionDTOs();
}
