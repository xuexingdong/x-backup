package com.xxd.xpay.strategy.wechat;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.http.*;

@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface WechatPayService {
    @POST(Constants.TRANSACTION_PREFIX + "/jsapi")
    Call<JsapiResponse> jsapiPay(@Body PayRequest payRequest);

    @POST(Constants.TRANSACTION_PREFIX + "/app")
    Call<AppResponse> appPay(@Body PayRequest payRequest);

    @POST(Constants.TRANSACTION_PREFIX + "/h5")
    Call<H5Response> h5Pay(@Body PayRequest payRequest);

    @POST(Constants.TRANSACTION_PREFIX + "/native")
    Call<NativeResponse> nativePay(@Body PayRequest payRequest);

    @GET(Constants.TRANSACTION_PREFIX + "/id/{transaction_id}")
    Call<PayResponse> queryPayResultByTransactionId(@Path("transaction_id") String transactionId, @Query("mchid") String mchid);

    @GET(Constants.TRANSACTION_PREFIX + "/out-trade-no/{out_trade_no}")
    Call<PayResponse> queryPayResultByOutTradeNo(@Path("transaction_id") String outTradeNo, @Query("mchid") String mchid);

    @POST(Constants.PREFIX + "/out-trade-no/{out_trade_no}/close")
    Call<Void> close(@Path("transaction_id") String outTradeNo, @Query("mchid") String mchid);

    @POST(Constants.REFUND_PREFIX)
    Call<RefundResponse> refund(@Body RefundRequest refundRequest);

    @POST(Constants.PREFIX + "/{out_refund_no}")
    Call<RefundResponse> queryRefundResult(@NotNull @Path("out_refund_no") String outRefundNo);

    @GET(Constants.PREFIX + "/bill/tradebill")
    Call<String> getBillDownloadUrl(@NotNull @Query("bill_date") String billDate, @Query("bill_type") String billType, @Query("tarType") String tarType);

    @Streaming
    @GET
    Call<ResponseBody> downloadBill(@Url String downloadUrl);
}
