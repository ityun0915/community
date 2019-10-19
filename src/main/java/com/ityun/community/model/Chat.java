package com.ityun.community.model;

import lombok.Data;
@Data
public class Chat {
    private int id;
    private String sendUser;   //发送者
    private String receiveUser;   //接受者
    private String message;     //消息
    private int messagetype;//1:初始化认证消息，2：聊天消息
    private int isRead;//1是未读,0是已读
}
