package com.killer.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

@Configuration
public class ApplicationConfig {

    @Bean
    public HttpMessageConverters customHttpMessageConverters(){

//        return new HttpMessageConverters()
        return null;
    }

    // bean 注释的可以自动在参数中自动注入依赖bean StringRedisTemplate 就是使用stringRedisSerializer 作为序列化工具
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        // 基本上只有一种可能了 就是不是使用该redistemplate 来存储数据的
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    // 需要强到一种境界，像王雨飞那样，人皆往之
    // 这个bean并没有起作用 并没有使用数据库1
    // 只需要继承修改 RedisHttpSessionConfiguration 并将该类排除
    @Bean
    public RedisOperationsSessionRepository sessionRepository (RedisTemplate redisTemplate
                                    , ObjectMapper objectMapper) {
        System.out.println("该bean已经被容器加载了");
        RedisOperationsSessionRepository redisOperationsSessionRepository = new RedisOperationsSessionRepository(redisTemplate);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisOperationsSessionRepository.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisOperationsSessionRepository.setDatabase(1);
        return redisOperationsSessionRepository;
    }
}
