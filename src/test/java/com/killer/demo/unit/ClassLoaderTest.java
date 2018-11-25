package com.killer.demo.unit;

import com.killer.demo.DemoApplication;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;

import static java.beans.Introspector.USE_ALL_BEANINFO;

public class ClassLoaderTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ClassLoader classLoader = DemoApplication.class.getClassLoader();
        URL resource = classLoader.getResource("com/killer/demo/MyTest.class");

//        System.out.println(resource == null ? null : resource.toString());
        outClassLoader();
//        只有在使用这个类时该类才会被加载到classloader中 finally会在return之前执行
        MyTest myTest = new MyTest();

        URL resource1 = classLoader.getResource("com/killer/demo/MyTest.class");
//        System.out.println(resource1 == null ? null : resource1.toString());
        outClassLoader();
    }

    public static void outClassLoader() throws NoSuchFieldException, IllegalAccessException {
        Field classField = ClassLoader.class.getDeclaredField("classes");
        classField.setAccessible(true);
        Object classes = classField.get(ClassLoader.getSystemClassLoader());
        System.out.println(classes.toString());
    }

    public static void out() throws IntrospectionException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        BeanInfo introspector = Introspector.getBeanInfo(systemClassLoader.getClass());
    }
}
