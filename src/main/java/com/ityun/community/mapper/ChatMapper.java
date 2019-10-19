package com.ityun.community.mapper;

import com.ityun.community.model.Chat;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMapper {

    @Insert("insert into chat(sendUser,receiveUser,message,messagetype,isRead) values(#{sendUser},#{receiveUser},#{message},#{messagetype},#{isRead})")
    void savaXinxi(Chat chat);

    @Select("select * from chat where sendUser=#{sendUser}")
    List<Chat> selectXinxis(@Param("sendUser") String sendUser);

    @Update("update chat set isRead=0 where sendUser=#{sendUser}")
    void updateXinxi(@Param("sendUser") String sendUser);
}
