package com.killer.demo.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        String verifyCode = session.getAttribute("verifyCode").toString();
        LocalDateTime verifyCodeExpireTime = (LocalDateTime)(session.getAttribute("verifyCodeExpireTime"));

        ArrayList<GrantedAuthority> authorites = new ArrayList<>();
        authorites.add(new CaptchaAuthority());
        CaptchaAuthentication captchaAuthentication = new CaptchaAuthentication(authorites);

        // 判断验证码是否过期
        if(verifyCodeExpireTime.isBefore(LocalDateTime.now()) || !captcha.equalsIgnoreCase(verifyCode)) {
            captchaAuthentication.setAuthenticated(false);
        } else {
            captchaAuthentication.setAuthenticated(true);
        }

        return captchaAuthentication;
    }

}
