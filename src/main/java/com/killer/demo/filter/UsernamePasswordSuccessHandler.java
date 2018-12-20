package com.killer.demo.filter;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-20 9:52
 */
public class UsernamePasswordSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void setTargetUrlParameter(String targetUrlParameter) {
        super.setTargetUrlParameter(targetUrlParameter);
    }
}
