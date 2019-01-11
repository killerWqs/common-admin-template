package com.killer.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wqs
 */
@SpringBootApplication
@ServletComponentScan("com.killer.demo.servlet")
@EnableSwagger2
@EnableWebMvc
@EnableTransactionManagement
@EnableEurekaClient
@EnableDiscoveryClient
@EnableRedisHttpSession // spring session 基本原理 session本地存储一份，外部也会存储一份用来共享
@MapperScan("com.killer.demo.modules.main.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplicationBuilder(DemoApplication.class).build(args);
        ConfigurableApplicationContext applicationContext = springApplication.run(args);

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println(beanFactory.getClass().getName());

        String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(SpringBootApplication.class);
        for (String name : beanNamesForAnnotation) {
            System.out.println(name);
        }
//        输出已加载的beandefinitionNames
//        System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
    }
}
