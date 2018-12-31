package com.killer.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import java.io.IOException;

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

    // 这个bean并没有起作用 并没有使用数据库1
    // 只需要继承修改 RedisHttpSessionConfiguration 并将该类排除
//    @Bean
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

    // 这种方式传参很正常
//    @Bean
    public SessionRepositoryBeanPostProcessor sessionRepositoryBeanPostProcessor(ObjectMapper objectMapper) {
        return new SessionRepositoryBeanPostProcessor(objectMapper);
    }

    private class SessionRepositoryBeanPostProcessor implements BeanPostProcessor {
        private ObjectMapper objectMapper;

        public SessionRepositoryBeanPostProcessor(ObjectMapper objectMapper) {
            System.out.println("My postprocessor has been initialized!");
            this.objectMapper = objectMapper;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if("sessionRepository".equals(beanName)) {
                ((RedisOperationsSessionRepository)bean).setDatabase(3);
                Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
                objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

                StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

                ((RedisOperationsSessionRepository)bean).setDefaultSerializer(jackson2JsonRedisSerializer);
            }
            return bean;
        }
    }

    // 原本使用的是jdk serializer 问题很严重啊 无法反序列化
//    @Bean
    public Jackson2JsonRedisSerializer springSessionDefaultRedisSerializer(ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    // 序列化真是一门神奇的东西
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();

//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
//                new Jackson2JsonRedisSerializer(Object.class);
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        String json = "[\"org.springframework.security.core.context.SecurityContextImpl\",{\"authentication\":[\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",{\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\"," +
                "[[\"org.springframework.security.core.authority.SimpleGrantedAuthority\",{\"role\":\"超级管理员\",\"authority\":\"超级管理员\"}]]],\"details\":[\"org.springframework.security.web.authentication.WebAuthenticationDetails\",{\"remoteAddress\":\"0:0:0:0:0:0:0:1\"," +
                "\"sessionId\":null}],\"authenticated\":true,\"principal\":[\"org.springframework.security.core.userdetails.User\",{\"password\":null,\"username\":\"18651867695\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[[\"org.springframework.security.core.authority.SimpleGrantedAuthority\"," +
                "{\"role\":\"超级管理员\",\"authority\":\"超级管理员\"}]]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true}],\"credentials\":null,\"name\":\"18651867695\"}]}]";
        try {
            // securitycontextimpl 不是usernamepasswordauthenticationtoken的子类
            objectMapper.readValue(json.getBytes(), 0, json.getBytes().length, SecurityContextImpl.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        jackson2JsonRedisSerializer.deserialize(json.getBytes());
    }
}
