package com.killer.demo.filter;/**
 * @author killer
 * @date 2018/12/19 -  23:53
 **/

import org.springframework.security.core.AuthenticationException;
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
public class UsernamePasswordFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
        PrintWriter writer = response.getWriter();
        writer.println(exception.getMessage());
    }
}
