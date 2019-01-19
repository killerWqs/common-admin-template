package com.killer.demo.filter;/**
 * @author killer
 * @date 2018/12/19 -  23:53
 **/

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

        response.setStatus(400);
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter writer = response.getWriter();

        if(AuthenticationException.class.isAssignableFrom(exception.getClass())) {
            writer.println("该用户名不存在！");
        }
        writer.flush();
    }

    public static void main(String[] args) {
        System.out.println(AuthenticationException.class.isAssignableFrom(UsernameNotFoundException.class));
    }
}
