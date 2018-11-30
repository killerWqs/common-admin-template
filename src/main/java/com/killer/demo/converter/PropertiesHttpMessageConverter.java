package com.killer.demo.converter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesHttpMessageConverter extends AbstractGenericHttpMessageConverter<Properties> {

    public PropertiesHttpMessageConverter() {
        super(new MediaType("text", "properties"));
    }

    @Override
    protected void writeInternal(Properties properties, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println("执行了writeInternal");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputMessage.getBody());
        outputStreamWriter.write(properties.get("username").toString());
        outputStreamWriter.flush();
    }

    @Override
    protected Properties readInternal(Class<? extends Properties> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public Properties read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        HttpHeaders headers = inputMessage.getHeaders();
        Charset charset = headers.getContentType().getCharset();
        InputStreamReader inputStreamReader = new InputStreamReader(inputMessage.getBody(), charset);
        Properties properties = new Properties();
        properties.load(inputStreamReader);
        return properties;
    }
}
