package com.killer.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.sockjs.support.SockJsHttpRequestHandler;
import org.springframework.web.socket.sockjs.transport.handler.SockJsWebSocketHandler;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-11 12:37
 */

// broker 中间人的意思
@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sockJsWebSocketHandler(), "/open").withSockJS();
    }

    @Bean
    public SockJsWebSocketHandler sockJsWebSocketHandler() {
        return new SockJsWebSocketHandler();
    }
}