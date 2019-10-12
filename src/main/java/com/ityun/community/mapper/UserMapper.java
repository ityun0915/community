package com.ityun.community.mapper;

import com.ityun.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,bio,email,company,image_url) values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{bio},#{email},#{company},#{image_url})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findUserByToken(@Param("token") String token);

    @Select("select * from user where account_id=#{account_id}")
    User findUserByAccount_id(@Param("account_id") String account_id);

    @Select("select * from user where id=#{creator}")
    User findUserByCreator(@Param("creator") Integer creator);

    @Update("update user set token=#{token} where account_id=#{account_id}")
    void updateToken(User user);
}
