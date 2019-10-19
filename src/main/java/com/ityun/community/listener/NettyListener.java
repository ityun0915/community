package com.ityun.community.listener;

import com.ityun.community.server.NettyServer;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *    netty启动线程        xml监听器启动此类
 * **/
@WebListener
@Component
public class NettyListener implements HttpSessionListener{
	public static int online = 0;


	@Override
	public void sessionCreated(HttpSessionEvent event){
		System.err.println("[系统消息]:IM服务器开始启动~~");
		new Thread(() -> {
			try {
				NettyServer server= new NettyServer();
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();


		System.out.println("新建了一个session对象...");

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event){
		System.out.println("销毁了一个session对象...");
		synchronized(this){
			online--;//用户数减-
//			onlineUser.getOnlineUsers().remove(sessionId);//从用户组中移除掉，用户组为一个map
		}
	}

}
