package com.killer.demo.servlet.support;/**
 * @author killer
 * @date 2018/11/18 -  21:23
 **/

import com.killer.demo.config.WebMvcConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *@description
 *@author killer
 *@date 2018/11/18 - 21:23
 */
public class DefaultAnnotationConfigDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }
}
