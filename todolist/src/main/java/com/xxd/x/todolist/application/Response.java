package com.xxd.x.todolist.application;

public class Response<T> {

    private int code;
    private String message = "";

    private T data;

    public static Response<Void> ok() {
        return ok(HttpStatus.OK.message());
    }

    public static <T> Response<T> data(T t) {
        Response<T> response = new Response<>();
        response.data = t;
        return response;
    }

    public static <T> Response<T> ok(String message) {
        Response<T> Response = new Response<>();
        Response.message = message;
        return Response;
    }

    public static Response badRequest() {
        return badRequest(HttpStatus.BAD_REQUEST.message());
    }

    public static Response badRequest(String message) {
        Response Response = new Response();
        Response.code = HttpStatus.BAD_REQUEST.status();
        Response.message = message;
        return Response;
    }

    public static Response internalServerError() {
        return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR.message());
    }

    public static Response internalServerError(String message) {
        Response Response = new Response();
        Response.code = HttpStatus.INTERNAL_SERVER_ERROR.status();
        Response.message = message;
        return Response;
    }

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response message(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
