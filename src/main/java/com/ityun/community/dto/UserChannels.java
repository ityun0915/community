package com.ityun.community.dto;

import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.Map;
 
public class UserChannels {
 
    private Map<String, Channel> onlineUsers=new HashMap<String,Channel>();

    public void addOnlineUser(String username,Channel channel){
        onlineUsers.put(username,channel);
    }
    public void removeOnlineUser(String username){
        onlineUsers.remove(username);
    }
    public void removeChannel(Channel channel){
        for (Map.Entry<String, Channel> entry : onlineUsers.entrySet()) {
            if(entry.getValue()==channel){
                onlineUsers.remove(entry.getKey());
            }
        }
    }
    public Channel getChannel(String username){
        return onlineUsers.get(username);
    }
    public Map<String, Channel> getOnlineUsers() {
        return onlineUsers;
    }
 
}