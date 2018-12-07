package com.killer.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * @author wqs
 */
@SpringBootApplication
@ServletComponentScan("com.killer.demo.servlet")
@EnableWebMvc
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

        System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
    }
}
