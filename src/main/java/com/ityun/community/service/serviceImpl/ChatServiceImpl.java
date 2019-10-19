package com.ityun.community.service.serviceImpl;

import com.ityun.community.mapper.ChatMapper;
import com.ityun.community.model.Chat;
import com.ityun.community.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public void saveXinxi(Chat chat) {
        chatMapper.savaXinxi(chat);
    }

    @Override
    public List<Chat> selectXinxis(String sendUser) {
        return chatMapper.selectXinxis(sendUser);
    }

    @Override
    public void updateXinxi(String sendUser) {
        chatMapper.updateXinxi(sendUser);
    }
}
