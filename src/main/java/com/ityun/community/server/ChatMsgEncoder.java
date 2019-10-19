package com.ityun.community.server;

import com.ityun.community.dto.ChatMessageDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;
 
public class ChatMsgEncoder extends MessageToByteEncoder<ChatMessageDTO> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ChatMessageDTO chatMessage, ByteBuf byteBuf) throws Exception {
        MessagePack msgpack=new MessagePack();
        byte[] msg=msgpack.write(chatMessage);
        byteBuf.writeBytes(msg);
    }
}
