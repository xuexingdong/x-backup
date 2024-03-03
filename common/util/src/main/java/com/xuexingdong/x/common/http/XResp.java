package com.xxd.x.common.http;

public class XResp {

    protected int code;
    protected String message;

    public static XResp ok() {
        return ok(HttpStatus.OK.message());
    }

    public static XResp ok(String message) {
        XResp Resp = new XResp();
        Resp.message = message;
        return Resp;
    }

    public static XResp badRequest() {
        return badRequest(HttpStatus.BAD_REQUEST.message());
    }

    public static XResp badRequest(String message) {
        XResp Resp = new XResp();
        Resp.code = HttpStatus.BAD_REQUEST.status();
        Resp.message = message;
        return Resp;
    }

    public static XResp internalServerError() {
        return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR.message());
    }

    public static XResp internalServerError(String message) {
        XResp Resp = new XResp();
        Resp.code = HttpStatus.INTERNAL_SERVER_ERROR.status();
        Resp.message = message;
        return Resp;
    }

    public int getCode() {
        return code;
    }

    public XResp setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public XResp message(String message) {
        this.message = message;
        return this;
    }
}
