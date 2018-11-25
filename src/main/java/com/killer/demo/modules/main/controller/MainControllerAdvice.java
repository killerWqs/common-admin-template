package com.killer.demo.modules.main.controller;/**
 * @author killer
 * @date 2018/11/25 -  12:47
 **/

import com.killer.demo.modules.main.excetpion.AddUserException;
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

    @ExceptionHandler(AddUserException.class)
    public ResponseEntity exceptionHandler(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(throwable.getMessage());
    }
}
