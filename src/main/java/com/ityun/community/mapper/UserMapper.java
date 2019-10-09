package com.ityun.community.mapper;

import com.ityun.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user(account_id,name,token,gmt_create,gmt_modified) VALUES(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified});")
    void insert(User user);
}
