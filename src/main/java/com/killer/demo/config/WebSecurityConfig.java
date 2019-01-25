package com.killer.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.filter.CaptchaFilter;
import com.killer.demo.filter.LoginFailureHandler;
import com.killer.demo.filter.UsernamePasswordAuthProvider;
import com.killer.demo.filter.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.annotation.PostConstruct;
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String cookieNamesToClear = "SESSION";

    @PostConstruct
    public void postConstruct() {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        stringRedisTemplate.setKeySerializer(stringSerializer);
        stringRedisTemplate.setValueSerializer(stringSerializer);
        stringRedisTemplate.setHashKeySerializer(stringSerializer);
        stringRedisTemplate.setHashValueSerializer(stringSerializer);
    }

    @Bean
    public UsernamePasswordAuthProvider usernamePasswordAuthProvider(){
        return new UsernamePasswordAuthProvider();
    }

    @Bean(autowireCandidate = true)
    public ProviderManager providerManager() {
        ArrayList<AuthenticationProvider>
                authenticationProviders = new ArrayList<>();
        authenticationProviders.add(usernamePasswordAuthProvider());

        ProviderManager providerManager = new ProviderManager(authenticationProviders);
        return providerManager;
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        授权所有url给通过了表单验证的用户
//        captchaFilter.setContinueChainBeforeSuccessfulAuthentication(true);

        http.authorizeRequests()
//                .anyRequest().authenticated() //会与下面的授权冲突
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/yiqihui/**").authenticated()
                .antMatchers("/static/views/index.html").authenticated()
                .antMatchers("/static/**").permitAll()
                .and()
            .formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/admin/login").permitAll()
//                如果直接访问登录页面，则授权成功后跳转到的页面
                .defaultSuccessUrl("/admin")
                // 为什么这个handler会在usernamepasswordauthenticationfilter调用一下
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .addLogoutHandler(new SecurityContextLogoutHandler())
                .deleteCookies(cookieNamesToClear);

        // .authenticated 给授权的用户权限 permitall给所有的用户权限

        http.csrf().disable();

        CaptchaFilter captchaFilter = new CaptchaFilter("/admin/login");
        http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
