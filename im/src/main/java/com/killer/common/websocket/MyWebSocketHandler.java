package com.killer.common.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.common.utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Map;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-20 12:25
 */
public class MyWebSocketHandler extends AbstractWebSocketHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static ObjectMapper objectmapper = new ObjectMapper();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // super.afterConnectionEstablished(session);
        Message message = new Message("response", "server", "client", "connection has been " +
                "established");
        TextMessage textMessage = new TextMessage(objectmapper.writeValueAsString(message));
        session.sendMessage(textMessage);

        logger.info("someone has established a connection with server");

        // 获取用户信息 需要记录一个状态表 do nothing
        Map<String, Object> attributes = session.getAttributes();

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // messageType 用来区分不同的信息 身份验证 websocketsession 是不支持序列化的 所以无法存储到 redis中
        logger.info("获取到信息:" + message.getPayload().toString());
    }

    public static void main(String[] args) throws IOException {
        System.out.println(objectmapper.readValue("[\"lalalla\"]", String[].class));
        objectmapper.readValue("[{\"type\":\"identity\",\"from\":\"client\",\"target\":\"server\",\"content\":\"identity\"}]", Message[].class);
    }
}
