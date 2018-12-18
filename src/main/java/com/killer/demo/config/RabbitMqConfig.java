package com.killer.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-18 14:01
 */
@Configuration
@EnableRabbit
public class RabbitMqConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    public RabbitMqConfig(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, CachingConnectionFactory cachingConnectionFactory) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        // 该cachingconnectionfactory spring boot已经配置好了
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory myFactory(
    SimpleRabbitListenerContainerFactoryConfigurer configurer){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory,cachingConnectionFactory);
//        factory.setMessageConverter(myMessageConverter());
        return factory;
    }

//    @Bean
//    public RabbitMqListener rabbitMqListener() {
//        return new RabbitMqListener();
//    }
//
//    private class RabbitMqListener {
//        @RabbitListener(queues = "message queue")
//        public void processMessage(String content) {
//            logger.info("接收到的消息：" + content);
//        }
//    }
}

