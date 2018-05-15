package com.wh.domain.response;

/**
 * Created by wh on 2017/12/9.
 */
public class Response<T> {

    private Integer code;
    private T data;
    private String message;

    public static <T> Response SUCCESS(T data){
        return new Response(200, data, null);
    }

    public static <T> Response SUCCESS(T data, String message){
        return new Response(200, data, message);
    }

    public static Response ERROR(String message){
        return new Response(500, null, message);
    }

    public Response(){}
    public Response(Integer code, T data, String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
