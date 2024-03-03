package com.xxd.x.common.http;

public class XFluxResp<T> extends XResp {
    private Page page;
    private Iterable<T> data;

    public static <T> XFluxResp<T> ok(Iterable<T> data) {
        XFluxResp<T> fluxResp = new XFluxResp<>();
        fluxResp.message = HttpStatus.OK.message();
        fluxResp.data = data;
        return fluxResp;
    }

    public static <T> XFluxResp<T> ok(Iterable<T> data, Page page) {
        XFluxResp<T> fluxResp = new XFluxResp<>();
        fluxResp.message = HttpStatus.OK.message();
        fluxResp.page = page;
        fluxResp.data = data;
        return fluxResp;
    }
}
