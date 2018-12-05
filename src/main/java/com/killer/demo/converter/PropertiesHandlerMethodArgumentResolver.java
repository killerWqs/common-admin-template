package com.killer.demo.converter;/**
 * @author killer
 * @date 2018/11/27 -  22:56
 **/

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 *@description 加requestbody与不加requestbody有什么区别 handlerargumentresolver与httpmessageconverter有什么区别
 *@author killer
 *@date 2018/11/27 - 22:56
 */
public class PropertiesHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        AnnotatedElement annotatedElement = methodParameter.getAnnotatedElement();
        if(annotatedElement.isAnnotationPresent(RequestBody.class)) {
//            如果存在requestbody
        }

        Parameter parameter = methodParameter.getParameter();
        if(parameter.getType() == Properties.class) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        PropertiesHttpMessageConverter propertiesHttpMessageConverter = new PropertiesHttpMessageConverter();

        ServletWebRequest servletWebRequest = (ServletWebRequest)nativeWebRequest;
        HttpServletRequest request = servletWebRequest.getRequest();

        HttpInputMessage httpInputMessage = new ServletServerHttpRequest(request);

        Properties properties = propertiesHttpMessageConverter.read(null, null, httpInputMessage);

//        String contentType = request.getContentType();
//        MediaType mediaType = MediaType.parseMediaType(contentType);
//        Charset charset = mediaType.getCharset();
//        if(charset == null) { charset = Charset.forName("utf-8"); }
//
//        Properties properties = new Properties();
//        InputStream inputStream = request.getInputStream();
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
//        // 加载字符流
//        properties.load(inputStreamReader);

        return properties;
    }
}
