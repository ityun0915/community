package com.ityun.community.server;

import com.ityun.community.dto.ChatMessageDTO;
import com.ityun.community.dto.UserChannels;
import com.ityun.community.model.Chat;
import com.ityun.community.service.ChatService;
import com.ityun.community.utils.ApplicationContextHelper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Map;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    ChatService chatService = ApplicationContextHelper.popBean(ChatService.class);

    public static UserChannels uc = new UserChannels();
    //新客户端进入时，将其加入channel队列

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【"+channel.remoteAddress() +" 上线了】");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【"+channel.remoteAddress() +" 下线了】");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();  //其实相当于一个connection

        /**
         * 调用channelGroup的writeAndFlush其实就相当于channelGroup中的每个channel都writeAndFlush
         *
         * 先去广播，再将自己加入到channelGroup中
         */
        channels.writeAndFlush(" 【服务器】 ---" +channel.remoteAddress() +" 加入---");
        channels.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx)throws Exception{
        Channel channel = ctx.channel();
        channels.writeAndFlush(" 【服务器】 ---" +channel.remoteAddress() +" 离开---");

        //验证一下每次客户端断开连接，连接自动地从channelGroup中删除调。
        System.out.println(channels.size());
        //当客户端和服务端断开连接的时候，下面的那段代码netty会自动调用，所以不需要人为的去调用它
        //channelGroup.remove(channel);
    }


    //如果有客户端有写数据，则转发给其他人
    //如果用户是来认证的 发送请求获得数据库中离线信息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
        Channel newchannel=ctx.channel();
        ChatMessageDTO cmsg=(ChatMessageDTO)msg;
        System.out.println("输出："+cmsg);
        for (Map.Entry<String, Channel> entry : uc.getOnlineUsers().entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        if(cmsg.getMessagetype()==1){//如果是初始化认证消息，则将该用户加入在线用户
            uc.addOnlineUser(cmsg.getSendUser(),newchannel);
            System.out.println(uc.getOnlineUsers());
            //  ChatMessage cmwarning=new ChatMessage("服务器", cmsg.getSendUser(),"欢迎你，"+cmsg.getSendUser() ,2);


            //从数据库中读取离线信息 返回给客户端
            List<Chat> list = chatService.selectXinxis(cmsg.getSendUser());

            //  cmwarning.setListChat(list);
            if(list.size()==0 || list==null) {
                //	newchannel.writeAndFlush(cmwarning);
            }else {

                for(int i=0;i<list.size();i++) {
                    System.out.println(list.get(i));
                    ChatMessageDTO huifu = new ChatMessageDTO();
                    huifu.setSendUser(list.get(i).getSendUser());
                    huifu.setReceiveUser(list.get(i).getReceiveUser());
                    huifu.setMessage(list.get(i).getMessage());
                    huifu.setMessagetype(list.get(i).getMessagetype());
                    newchannel.writeAndFlush(huifu);
                }
            }
            //newchannel.writeAndFlush(cmwarning);

            //把数据库中离线未读消息修改为已读消息
            chatService.updateXinxi(cmsg.getSendUser());
        }else if(cmsg.getMessagetype()==2){//如果是聊天消息，则判断发送的对象

            if(cmsg.getReceiveUser().equals("")){//发给所有人
                for(Channel ch:channels) {
                    ch.writeAndFlush(cmsg);
                }
            }else{//发给指定用户
                //判断信息是否是用户下线通知
                if(cmsg.getMessage().equals("我下线了")) {
                    //断开连接
                    uc.removeOnlineUser(cmsg.getSendUser());
                }else {
                    if(uc.getChannel(cmsg.getReceiveUser())==null){
                        System.out.println("此用户不在线，保存离线消息。。。。。");
                        //保存消息
                        Chat chat = new Chat();
                        chat.setIsRead(1);
                        chat.setMessage(cmsg.getMessage());
                        chat.setMessagetype(cmsg.getMessagetype());
                        chat.setSendUser(cmsg.getSendUser());
                        chat.setReceiveUser(cmsg.getReceiveUser());
                        System.out.println(chat);

                        //保存离线消息
                        chatService.saveXinxi(chat);
                    }else{
                        System.out.println("用户在线，回复消息。。。。。。");
	                	/*//保存消息
	                	Chat Chat = new Chat();
	                    Chat.setIsRead(1);
	                    Chat.setMessage(cmsg.getMessage());
	                    Chat.setMessagetype(cmsg.getMessagetype());
	                    Chat.setSendUser(cmsg.getSendUser());
	                    Chat.setReceiveUser(cmsg.getReceiveUser());
	                    System.out.println(Chat);
	                    chattingService.saveXinxi(Chat);*/
                        System.out.println("回复消息到："+uc.getChannel(cmsg.getReceiveUser()).remoteAddress());
                        uc.getChannel(cmsg.getReceiveUser()).writeAndFlush(cmsg);
                        //回复过去没有报异常则修改数据库中状态
                        //  chattingService.updateXinxi(cmsg.getSendUser());
                    }
                }
            }
        }
    }

    //用于所有异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel newchannel=ctx.channel();
        System.out.println("["+newchannel.remoteAddress()+"]：通讯异常");
        System.out.println(cause.getMessage());
        newchannel.close();
    }
}