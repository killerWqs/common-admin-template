package com.killer.common.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-20 12:25
 */
public class MyWebSocketHandler extends AbstractWebSocketHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // super.afterConnectionEstablished(session);
        // 需要记录一个状态表 do nothing
        logger.info("someone has established a connection with server");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // messageType 用来区分不同的信息 身份验证 websocketsession 是不支持序列化的 所以无法存储到 redis中
        logger.info("获取到信息:" + message.getPayload().toString());
    }
}
