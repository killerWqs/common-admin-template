package com.killer.demo.modules.blog.controller;/**
 * @author killer
 * @date 2019/2/2 -  14:52
 **/

import com.killer.demo.modules.blog.exception.UploadImageFailedException;
import com.killer.demo.modules.main.controller.LoginController;
import com.killer.demo.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *@description
 *@author killer
 *@date 2019/02/02 - 14:52
 */
@ControllerAdvice(assignableTypes = BlogController.class)
public class BlogControllerAdvice {
    @ExceptionHandler({UploadImageFailedException.class})
    public ResponseEntity exceptionHandler(Throwable throwable) {
        return ResponseEntity.status(500).body(throwable.getMessage());
    }
}
