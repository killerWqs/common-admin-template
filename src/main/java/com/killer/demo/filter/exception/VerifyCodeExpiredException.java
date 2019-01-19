package com.killer.demo.filter.exception;/**
 * @author killer
 * @date 2019/1/19 -  14:45
 **/


import org.springframework.security.core.AuthenticationException;

/**
 *@description
 *@author killer
 *@date 2019/01/19 - 14:45
 */
public class VerifyCodeExpiredException extends AuthenticationException {
    public VerifyCodeExpiredException(String explanation) {
        super(explanation);
    }
}
