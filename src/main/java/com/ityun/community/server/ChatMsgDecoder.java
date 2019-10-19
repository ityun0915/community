package com.ityun.community.server;

import com.ityun.community.dto.ChatMessageDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;
 
import java.util.List;
 
public class ChatMsgDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final int length=byteBuf.readableBytes();
        final byte[] array=new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),array,0,length);
        list.add(new MessagePack().read(array, ChatMessageDTO.class));
    }
}