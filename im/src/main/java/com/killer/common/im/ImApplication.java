package com.killer.common.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description 用来作为一个分布式服务，
 *          一个浏览器客户端只能使用一个地址访问, 使用其他地址需要进行跨域 cross origin request share
 *          不过这应该不是问题
 * @author killer
 * @date 2018/12/10
 */
@SpringBootApplication
public class ImApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }

}
