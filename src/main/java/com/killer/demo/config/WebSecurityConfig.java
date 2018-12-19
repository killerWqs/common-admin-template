package com.killer.demo.config;

import com.killer.demo.filter.UsernamePasswordAuthProvider;
import com.killer.demo.filter.UsernamePasswordFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-17 15:19
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UsernamePasswordAuthProvider usernamePasswordAuthProvider(){
        return new UsernamePasswordAuthProvider();
    }

    @Bean
    public ProviderManager providerManager() {
        ArrayList<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(usernamePasswordAuthProvider());

        ProviderManager providerManager = new ProviderManager(authenticationProviders);
        return providerManager;
    }

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
                .antMatchers("/static/views/index.html").authenticated()
                .and()
            .formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/admin/login").permitAll()
                .failureHandler(new UsernamePasswordFailureHandler());

        // .authenticated 给授权的用户权限 permitall给所有的用户权限
        http.authorizeRequests().antMatchers("/static/layui/**").permitAll();
//        http.addFilter(new MyUsernamePasswordFilter("/login")); 没有意义了
        http.csrf().disable();
    }
}
