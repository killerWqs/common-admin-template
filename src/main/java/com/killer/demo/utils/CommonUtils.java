package com.killer.demo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 *@description
 *@author killer
 *@date 2019/02/13 - 11:36
 */
public class CommonUtils {
    public static String getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        UserDetails details = (UserDetails)authentication.getDetails();
        // username我存的user.id
        String username = details.getUsername();
        return username;
    }

    public static String getRoleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        UserDetails details = (UserDetails)authentication.getDetails();

        Object[] authorities = details.getAuthorities().toArray();

        return authorities[0].toString();
    }
}
