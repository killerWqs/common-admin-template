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
 * @date 2018-11-15 14:35
 */
//@Component
@WebFilter
public class MySecondFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter writer =
                servletResponse.getWriter();
        writer.println("oh my god");
//        dofilter 最后会调用servlet
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
