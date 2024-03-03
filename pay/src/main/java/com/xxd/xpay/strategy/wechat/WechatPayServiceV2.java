package com.xxd.xpay.strategy.wechat;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.Call;
import retrofit2.http.*;

@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface WechatPayServiceV2 {
    @POST(Constants.V2_PREFIX + "/micropay")
    Call<JsapiResponse> microPay(@Body PayRequest payRequest);

    @GET(Constants.PREFIX + "/id/{transaction_id}")
    Call<JsapiResponse> queryByTransactionId(@Path("transaction_id") String transactionId, @Query("mch_id") String mchid);

    @GET(Constants.PREFIX + "out-trade-no/{out_trade_no}")
    Call<JsapiResponse> queryByOutTradeNo(@Path("transaction_id") String outTradeNo, @Query("mch_id") String mchid);
}
