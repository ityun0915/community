package com.ityun.community.mapper;

import com.ityun.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT into question(title,description,gmt_create,gmt_modified, creator,tag) VALUES(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Question question);
}
