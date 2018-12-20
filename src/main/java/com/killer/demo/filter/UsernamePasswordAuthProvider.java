package com.killer.demo.filter;

import com.killer.demo.modules.main.dao.UserMapper;
import com.killer.demo.modules.main.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-18 17:09
 */
public class UsernamePasswordAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // athentication 是用户发送过来的身份信息 userDetails是获取到的用户信息
        String credentials = authentication.getCredentials().toString();
        String cryptoed = DigestUtils.md5DigestAsHex(credentials.getBytes());

        authentication.setAuthenticated(cryptoed.equals(userDetails.getPassword()));
    }

    // 用来检索（retrieve）user 获取用户信息
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws UsernameNotFoundException {
        logger.info("要验证的用户名：" + username);
        User user = userMapper.selectUserByUserName(username);
        // 权限的表现形式可以为字符串 role 的角色名
        if(user == null) {
            throw new UsernameNotFoundException("该用户不存在：" + username);
        }
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRoleName());
        authorities.add(simpleGrantedAuthority);

        org.springframework.security.core.userdetails.User user1 =
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return user1;
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }
}
