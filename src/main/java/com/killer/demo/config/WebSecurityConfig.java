package com.killer.demo.config;

import com.killer.demo.filter.MyUsernamePasswordFilter;
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
//                .anyRequest().authenticated() //会与下面的授权冲突
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/yiqihui/**").authenticated()
                .and()
            .formLogin().loginPage("/static/views/login.html").permitAll()
                .loginProcessingUrl("/login");

        // .authenticated 给授权的用户权限 permitall给所有的用户权限
        http.authorizeRequests().antMatchers("/static/layui/**").permitAll();

        http.addFilter(new MyUsernamePasswordFilter("/login"));
    }
}
