package com.killer.common.websocket;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Base64;
import java.util.Map;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-21 15:59
 */
public class MyWebSocketIntercepter implements HandshakeInterceptor {

    private static final String  REDIS_NAMESPACE = "spring:session:sessions:";
    private static final String  ATTR_NAME = "sessionAttr:SPRING_SECURITY_CONTEXT";

    private Logger logger = LoggerFactory.getLogger(getClass());

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
            if(cok.trim().startsWith("SESSION")) {
                session = cok.split("=")[1];
            }
        }

        // 非法登入 用户的身份信息应该存储在服务器端
        if(session == null) {
            return false;
        }

        // 从redis中获取用户身份
        Base64.Decoder decoder = Base64.getDecoder();
        HashOperations<String, Object, Object> hashOperations = StringRedisTemplate.opsForHash();
//                .get("spring:session:sessions:"
//                + new String(decoder.decode(session)));
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        StringRedisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        SecurityContextImpl token = (SecurityContextImpl) hashOperations.get(REDIS_NAMESPACE + new String(decoder.decode(session)), ATTR_NAME);

//        SecurityContextImpl token = (SecurityContextImpl)jdkSerializationRedisSerializer.deserialize(httpSession.getBytes());
//        UsernamePasswordAuthenticationToken token = objectMapper.readValue(httpSession, UsernamePasswordAuthenticationToken.class);
//        objectmapper 是对json字符串进行操作的，序列化有序列化的方式
        if(token == null) {
            return false;
        } else {
            attributes.put("authentication", token);
            return true;
        }

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//        wsHandler.afterConnectionEstablished();
//        建立连接之后建立映射关系, 由于websocketsession无法进行序列化存储
    }

    public static void main(String[] args) {
        String session = "SESSION=MDNhZDI0ZmQtYmIyOC00NzNlLWFlZTUtMWU0NWViODg0NmI4";
        String con = null;
        if(session.startsWith("SESSION")) {
            con = session.split("=")[1];
        }

        System.out.println(con);
    }
}
