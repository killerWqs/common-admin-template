package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/25 -  12:47
 **/

import com.killer.demo.modules.main.excetpion.AddUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *@description
 *@author killer
 *@date 2018/11/25 - 12:47
 */
@ControllerAdvice
public class MainControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(AddUserException.class)
    public ResponseEntity exceptionHandler(Throwable throwable) {
        // 日志太简单了
        logger.error(throwable.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(throwable.getMessage());
    }
}
