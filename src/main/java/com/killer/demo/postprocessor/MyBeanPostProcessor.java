package com.killer.demo.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

//@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    private int count;

    public MyBeanPostProcessor() {
        System.out.println("My postprocessor has been initialized!");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("已经有" + ++count + "个组件注册");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
