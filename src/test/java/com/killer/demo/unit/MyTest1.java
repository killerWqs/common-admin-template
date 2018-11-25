package com.killer.demo.unit;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

@SuppressWarnings("all")
@Component
public class MyTest1  extends MyTest implements Ordered {
    public static void main(String[] args)  {
        Annotation[] declaredAnnotations = MyTest1.class.getDeclaredAnnotations();
//        Stream.of(declaredAnnotations).reduce((annotation, index) -> {
//            System.out.println(annotation.annotationType().getName());
//            return null;
//        });
        for (Annotation declaredAnnotation : declaredAnnotations) {
            System.out.println(declaredAnnotation.annotationType().getName());
        }

        Class<?>[] interfaces = MyTest1.class.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface.getName());
        }

        Class<? super MyTest1> superclass = MyTest1.class.getSuperclass();
        System.out.println(superclass.getName());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
