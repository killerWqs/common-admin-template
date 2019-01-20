package com.killer.demo.filter;

import com.killer.demo.filter.exception.VerifyCodeErrorException;
import com.killer.demo.filter.exception.VerifyCodeExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * TODO...
 *
 * @author wqs
 * @date 2019-01-02 10:16
 */
public class CaptchaFilter extends AbstractAuthenticationProcessingFilter {

    public CaptchaFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public CaptchaFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String captcha = request.getParameter("vercode");
        HttpSession session = request.getSession();

        if(session.getAttribute("verifyCode") == null) {
        // 解决 长时间未刷新页面 session过期问题
//            抛出验证码过期异常
            throw new VerifyCodeExpiredException("验证码已经过期");
        }

        String verifyCode = session.getAttribute("verifyCode").toString();

        LocalDateTime verifyCodeExpireTime = (LocalDateTime)(session.getAttribute("verifyCodeExpireTime"));

        ArrayList<GrantedAuthority> authorites = new ArrayList<>();
        authorites.add(new CaptchaAuthority());
        CaptchaAuthentication captchaAuthentication = new CaptchaAuthentication(authorites);

        // 判断验证码是否过期
        // security的设计 授权通过返回授权，不通过不是返回null 而是抛出异常
        if(verifyCodeExpireTime.isBefore(LocalDateTime.now())) {
            throw new VerifyCodeExpiredException("验证码已经过期");
        }

        if(!captcha.equalsIgnoreCase(verifyCode)) {
            throw new VerifyCodeErrorException("验证码错误");
        }

        captchaAuthentication.setAuthenticated(true);

        return captchaAuthentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        // 如果验证码授权通过则可以继续授权
        if(authResult.isAuthenticated()) {
            chain.doFilter(request, response);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);
        PrintWriter writer = response.getWriter();
        writer.write(failed.getMessage());
        writer.flush();
        // 在这儿关闭不报错？？？
        writer.close();
    }
}
