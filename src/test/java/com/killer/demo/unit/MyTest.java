package com.killer.demo.unit;

public class MyTest {
    public static void main(String[] args) {
        System.out.println("this is a test class");

        String lock = "lock";

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println("lalala");
//             因为是同步的所以只能一个一个执行
                synchronized (Thread.currentThread()) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getId());
                }
            }).start();
        }

    }

    public static synchronized void a() {

    }
    // 线程操作的同步机制是通过锁来实现的，资源共享
}
