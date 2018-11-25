package com.killer.demo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-11-15 14:24
 */
//@Component
@WebFilter(urlPatterns = "/", filterName = "MyFilter")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter writer = servletResponse.getWriter();
        writer.append("Hello").append(" World!\n");
        writer.println("See you!");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
