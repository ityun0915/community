package com.ityun.community.service;

import com.ityun.community.model.Chat;

import java.util.List;

public interface ChatService {
    void saveXinxi(Chat chat);//保存信息
    List<Chat> selectXinxis(String sendUser);//根据发送者查询所有离线消息
    void updateXinxi(String sendUser);//根据发送者修改离线消息状态(从未读到已读1->0)
}
