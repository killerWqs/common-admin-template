package com.killer.demo.filter.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *@description
 *@author killer
 *@date 2019/01/19 - 15:10
 */
public class PasswordErrorException extends AuthenticationException {
    public PasswordErrorException(String explanation) {
        super(explanation);
    }
}
