package com.killer.demo.converter;/**
 * @author killer
 * @date 2018/11/29 -  21:39
 **/

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 *@description 不需要使用@Reponsebody 需要添加这个类, 使用responsebody都会转为json格式返回
 * 接口是解耦的实现方式，门面模式的使用目的是使用 接口来实现接口
 *@author killer
 *@date 2018/11/29 - 21:39
 */
public class PropertiesHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return Properties.class.equals(methodParameter.getMethod().getReturnType());
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        Properties pro = (Properties) o;

        ServletWebRequest servletWebRequest = (ServletWebRequest)nativeWebRequest;
        HttpServletRequest request = servletWebRequest.getRequest();

        HttpServletResponse response = servletWebRequest.getResponse();

        HttpOutputMessage httpOutputMessage= new ServletServerHttpResponse(response);
        String contentType = request.getContentType();
        MediaType mediaType = MediaType.parseMediaType(contentType);

        PropertiesHttpMessageConverter propertiesHttpMessageConverter = new PropertiesHttpMessageConverter();
        propertiesHttpMessageConverter.write(pro, mediaType, httpOutputMessage);

        // TODO 告诉spring mvc处理已经完毕了
        modelAndViewContainer.setRequestHandled(true);
    }
}
