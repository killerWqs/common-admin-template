package com.killer.demo.utils;

/**
 *@description layui专用响应体
 *@author killer
 *@date 2018/12/05 - 23:29
 */
public class Response<T> {
    private int code = 0;

    private String message;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
