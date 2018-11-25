package com.killer.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * @author wqs
 */
@SpringBootApplication
@ServletComponentScan("com.killer.demo.servlet")
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
