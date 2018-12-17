package com.killer.demo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-17 15:19
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        授权所有url给通过了表单验证的用户
        http.authorizeRequests()
                .anyRequest().authenticated().and()
            .formLogin().loginPage("/static/views/login.html").permitAll();

        http.authorizeRequests().antMatchers("/static/layui/**").permitAll();
    }
}
