package com.ityun.community.dto;

import lombok.Data;
import org.msgpack.annotation.Index;
import org.msgpack.annotation.Message;

@Data
@Message
public class ChatMessageDTO {
    @Index(0)
    private String sendUser;   //发送者
    @Index(1)
    private String receiveUser;   //接受者
    @Index(2)
    private String message;     //消息
    @Index(3)
    private int messagetype;//1:初始化认证消息，2：聊天消息
}
