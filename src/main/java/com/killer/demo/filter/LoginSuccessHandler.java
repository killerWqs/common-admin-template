package com.killer.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-20 9:52
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    // 存储用户信息 这个类没什么必要了 spring security会自动将authentication存入redis中
    // 为什么这个类只有最后一个会调用
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("redis has entry the user");
        super.onAuthenticationSuccess(request, response, authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        HttpSession session = request.getSession();
        // objectMapper 是一个无状态bean 所以没有并发问题 simpledateformat 是有状态的
//        session.setAttribute("user", objectMapper.writeValueAsString(authentication));
//        Object principal = authentication.getPrincipal();
//        session.setAttribute("user", ((User)principal).getUsername());

        // TODO 本来是用来存储信息的但security自动存储了authentication
//        response.sendRedirect("/admin");
    }
}

