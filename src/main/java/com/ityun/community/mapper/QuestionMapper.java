package com.ityun.community.mapper;

import com.ityun.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Question question);
    @Select("select * from question order by gmt_create desc")
    List<Question> findQuest();
    @Select("select count(1) from question")
    Integer count();
}
