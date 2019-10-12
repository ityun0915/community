//package com.ityun.community.listener;
//
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
//public class OnlineUserListener implements HttpSessionListener {
//     private int SummerConstant = 0;
//     public void sessionCreated(HttpSessionEvent event){
//                    HttpSession session=event.getSession();
//                    String id=session.getId()+session.getCreationTime();
//                    SummerConstant.UserMap.put(id,Boolean.TRUE);//添加用户
//                }
//             public void sessionDestroyed(HttpSessionEvent event){
//                   HttpSession session=event.getSession();
//                    String id=session.getId()+session.getCreationTime();
//                    synchronized(this){
//                         SummerConstant.USERNum--;//用户数减-
//                       SummerConstant.UserMap.remove(id);//从用户组中移除掉，用户组为一个map
//                    }
//                }
// }
