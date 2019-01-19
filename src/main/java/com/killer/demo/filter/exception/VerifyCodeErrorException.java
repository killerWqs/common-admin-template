package com.killer.demo.filter.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *@description
 *@author killer
 *@date 2019/01/19 - 19:02
 */
public class VerifyCodeErrorException extends AuthenticationException {
    public VerifyCodeErrorException(String explanation) {
        super(explanation);
    }
}
