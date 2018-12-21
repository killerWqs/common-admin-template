package com.killer.common.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-21 15:59
 */
public class MyWebSocketIntercepter implements HandshakeInterceptor {

    @Autowired
    private StringRedisTemplate StringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 在这一步处理用户验证是否登录， 通过获取远程存储的session
        HttpHeaders headers = request.getHeaders();

        String cookie = headers.getFirst("Cookie");
        String[] cookies = cookie.split(";");

        String session = null;
        for (String cok : cookies) {
            if(cok.startsWith("SESSION")) {
                session = cok.split("=")[1];
            }
        }

        // 非法登入 用户的身份信息应该存储在服务器端
        if(session == null) {
            return false;
        }

        // 从redis中获取用户身份
        String httpSession = StringRedisTemplate.opsForValue().get(session);
        HttpSession httpSession1 = objectMapper.readValue(httpSession, HttpSession.class);

        return httpSession1 == null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//        wsHandler.afterConnectionEstablished();
//        建立连接之后建立映射关系
    }
}
