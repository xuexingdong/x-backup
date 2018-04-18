package com.xuexingdong.x.common.http;

public class XMonoResp<T> extends XResp {

    private T data;

    public static <T> XMonoResp<T> data(T data) {
        XMonoResp<T> monoResp = new XMonoResp<>();
        monoResp.message = HttpStatus.OK.message();
        monoResp.data = data;
        return monoResp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
