package com.killer.demo.filter;

import com.killer.demo.modules.main.dao.UserMapper;
import com.killer.demo.modules.main.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-18 17:09
 */
@Component
public class UsernamePasswordAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    // 用来检索（retrieve）user
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        User user = userMapper.selectUserByUserName(username);
        // 权限的表现形式可以为字符串 role 的角色名
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRoleName());
        authorities.add(simpleGrantedAuthority);

        org.springframework.security.core.userdetails.User user1 =
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return user1;
    }
}
