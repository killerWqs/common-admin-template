package com.killer.demo.filter;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *@description
 *@author killer
 *@date 2018/12/19 - 23:53
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        super.onAuthenticationFailure(request, response, exception);

//        response.setStatus(400);
//        response.setContentType("text/plain;charset=utf-8");
//        PrintWriter writer = response.getWriter();
//
////                if(AuthenticationException.class.isAssignableFrom(exception.getClass())) {
////            writer.println("该用户名不存在！");
////        }
//        writer.println(exception.getMessage());
//        writer.flush();
        response.sendError(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage());
    }

    public static void main(String[] args) {
        System.out.println(AuthenticationException.class.isAssignableFrom(UsernameNotFoundException.class));
    }
}
