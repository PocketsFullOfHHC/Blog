// 开放websocket链接
package com.hhc.blogs.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
// 该注解类似controller注解，外部可以通过访问这个websocket地址来连接websocket
@ServerEndpoint("/ws/chat/{userName}/{friendName}")
public class WebSocket {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocket.class);

    /**
     * 所有的客户端
     */
    // session是指在客户端和服务器之间建立的持久化连接
    // 当客户端与服务器建立WebSocket连接时，服务器会为每个连接创建一个session对象，用于跟踪该连接的状态和信息
    public static Map<String, Session> sessions = new HashMap<>();

    /**
     * 建立连接时的回调(前端请求一个websocket时)
     * @param session session(客户端)
     * @param userName 用户名
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userName") String userName, @PathParam("friendName") String friendName) {
        LOG.info("{}->:{}，进入聊天室" , userName, friendName);
        String chatId = userName + ":" + friendName;
        if (sessions.containsKey(chatId)){
            session.getAsyncRemote().sendText("用户名已经存在");
            return;
        }
        sessions.put(chatId, session);
        sendMessage(String.format("%s：加入群聊", chatId), userName, friendName);
        LOG.info("有新连接：chatId：{}，session id：{}，当前连接数：{}", chatId, session.getId(), sessions.size());
    }

    /**
     * 断开连接时的回调
     * @param userName 用户名
     */
    @OnClose
    public void onClose(Session session,@PathParam("userName") String userName, @PathParam("friendName") String friendName) throws IOException {
        session.close();
        LOG.info("{}，断开连接",userName);
        sessions.remove(userName);
    }

    /**
     * 发生错误时的回调
     * @param session session(客户端)
     * @param throwable 错误
     */
    @OnError
    public void onError(Session session, Throwable throwable,@PathParam("userName") String userName) throws IOException {
        LOG.info("{},出现错误", userName);
        session.close();
        sessions.remove(userName);
    }

    /**
     * 收到消息时的回调
     * @param message 消息
     * @param userName 用户名
     */
    @OnMessage
    public void onMessage(String message, @PathParam("userName") String userName, @PathParam("friendName") String friendName) {
        LOG.info("{}：{}",userName,friendName, message);
        String chatId = userName + ":" + friendName;
        sendMessage(String.format("%s：%s",chatId,message), userName, friendName);
    }

    /**
     * 发送给双方客户端
     * @param message 消息
     * @param userName 用户名
     * @param friendName 朋友名
     */
    public void sendMessage(String message,String userName, String friendName) {
        sessions.forEach((k,v) -> {
            if (k.equals(userName + ":" +friendName) || k.equals(friendName + ":" +userName)){
                v.getAsyncRemote().sendText(message);
            }
        });
    }
}