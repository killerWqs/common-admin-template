package com.killer.demo.config;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * TODO 用来配置使用stomp的client
 *
 * @author wqs
 * @date 2018-12-20 12:13
 */
public class WSConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

    }
}
