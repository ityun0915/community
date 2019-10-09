package com.ityun.community.mapper;

import com.ityun.community.model.Question;
import com.ityun.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user(account_id,name,token,gmt_create,gmt_modified,bio,email,company,image_url) VALUES(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{bio},#{email},#{company},#{image_url});")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from question")
    List<Question> findQuest();

    @Select("select * from user where id=#{creator}")
    User findUser(@Param("creator")int creator);
}
