package com.killer.demo.filter;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-20 12:25
 */
public class WebSocketOpenHandler extends AbstractWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        super.afterConnectionEstablished(session);
        session.get
    }
}
