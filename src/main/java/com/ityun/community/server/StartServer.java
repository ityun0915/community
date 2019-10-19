package com.ityun.community.server;

public class StartServer {
	
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}