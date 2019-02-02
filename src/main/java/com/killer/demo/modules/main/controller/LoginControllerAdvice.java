package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/18 -  20:58
 **/

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *@description
 *@author killer
 *@date 2018/11/18 - 20:58
 */
// 指定专用类，否则会给所有controller执行
// 提取公共代码到一个类中
@ControllerAdvice(assignableTypes = LoginController.class)
public class LoginControllerAdvice {
    // modelAttribute 会先处理
    @ModelAttribute("message")
    public String message() {
        return "hello world";
    }

    @ModelAttribute(value = "jessionId")
    public String jsessionId(@CookieValue(value = "JSESSIONID", required = false) String jsessionId) {
        return jsessionId;
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity exceptionHandler(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable.getMessage());
    }
}
