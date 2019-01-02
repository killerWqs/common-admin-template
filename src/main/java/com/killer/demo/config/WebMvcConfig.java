package com.killer.demo.config;/**
 * @author killer
 * @date 2018/11/18 -  16:53
 **/

import com.killer.demo.converter.PropertiesHandlerMethodArgumentResolver;
import com.killer.demo.converter.PropertiesHandlerMethodReturnValueHandler;
import com.killer.demo.converter.PropertiesHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *@description spring mvc config file
 *@author killer
 *@date 2018/11/18 - 16:53
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }

    @PostConstruct
    public void init() {
        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> myArgumentResolvers = new ArrayList<>(argumentResolvers.size() + 1);
        myArgumentResolvers.add(new PropertiesHandlerMethodArgumentResolver());
        myArgumentResolvers.addAll(argumentResolvers);
        requestMappingHandlerAdapter.setArgumentResolvers(myArgumentResolvers);

        // TODO 自定义 return value handlers
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> myReturnValueHandlers = new ArrayList<>(returnValueHandlers.size() + 1);
        myReturnValueHandlers.add(new PropertiesHandlerMethodReturnValueHandler());
        myReturnValueHandlers.addAll(returnValueHandlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(myReturnValueHandlers);
    }

    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    public WebMvcConfig(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        // TODO 自定义 argument resolvers
       this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/static/views");
        internalResourceViewResolver.setSuffix(".html");
        registry.viewResolver(internalResourceViewResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                logger.info("这个拦截器执行了");
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        });
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        PropertiesHttpMessageConverter propertiesHttpMessageConverter = new PropertiesHttpMessageConverter();
        converters.add(0, propertiesHttpMessageConverter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.set(0, new PropertiesHandlerMethodArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/admin/cors/resources").allowedOrigins("*");
    }

//    @Bean spring session 自动配置已经将sessionRepositoryFilter 注册到容器了
//    public SessionRepositoryFilter sessionRepositoryFilter(RedisOperationsSessionRepository redisOperationsSessionRepository) {
//        return new SessionRepositoryFilter(redisOperationsSessionRepository);
//    }


}
