package com.killer.demo.utils;/**
 * @author killer
 * @date 2018/12/5 -  23:29
 **/

/**
 *@description 响应结构
 *@author killer
 *@date 2018/12/05 - 23:29
 */
public class Response<T> {
    private int code = 0;

    private String message;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
