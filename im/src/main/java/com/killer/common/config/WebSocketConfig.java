package com.killer.common.config;

import com.killer.common.websocket.MyWebSocketHandler;
import com.killer.common.websocket.MyWebSocketIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.sockjs.transport.handler.DefaultSockJsService;
import org.springframework.web.socket.sockjs.transport.handler.SockJsWebSocketHandler;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

import java.util.HashMap;

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
        registry.addHandler(sockJsWebSocketHandler(), "/wsclient").
                setAllowedOrigins("*").withSockJS().setInterceptors(myWebSocketIntercepter());
    }

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/wsclient").setAllowedOrigins("*").withSockJS();
//    }

    @Bean
    public MyWebSocketIntercepter myWebSocketIntercepter() {
        return new MyWebSocketIntercepter();
    }

    @Bean
    public SockJsWebSocketHandler sockJsWebSocketHandler() {
        return new SockJsWebSocketHandler(defaultSockJsService(),
                myWebSocketHandler(),webSocketServerSockJsSession());
    }

    @Bean
    public MyWebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }

    @Bean
    public WebSocketServerSockJsSession webSocketServerSockJsSession() {
        // ServerWebSocket
        return new WebSocketServerSockJsSession("server_websocket",
                defaultSockJsService(),myWebSocketHandler(), new HashMap<String, Object>());
    }

    @Bean
    public DefaultSockJsService defaultSockJsService() {
        ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler();
        return new DefaultSockJsService(concurrentTaskScheduler);
    }
}