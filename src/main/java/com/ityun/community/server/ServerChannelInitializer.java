package com.ityun.community.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline=channel.pipeline();

        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
        pipeline.addLast("msgpack decoder",new ChatMsgDecoder());
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
        pipeline.addLast("msgpack encoder",new ChatMsgEncoder());
        pipeline.addLast("handler",new ServerHandler());
    }
}

