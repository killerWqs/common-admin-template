package com.killer.demo.filter;

import org.springframework.security.core.GrantedAuthority;

/**
 * TODO...
 *
 * @author wqs
 * @date 2019-01-02 10:25
 */
public class CaptchaAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "captcha verifies successfully";
    }
}
