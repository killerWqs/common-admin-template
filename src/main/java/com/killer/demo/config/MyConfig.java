package com.killer.demo.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public HttpMessageConverters customHttpMessageConverters(){

//        return new HttpMessageConverters()
        return null;
    }
}
