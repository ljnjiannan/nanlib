package com.nan.nanlib.request;

import com.nan.nanlib.request.retrofit.HeaderInterception;
import com.nan.nanlib.request.retrofit.ParamsInterception;
import com.nan.nanlib.request.retrofit.RetryTokenInterception;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author liujiannan
 * @date 2018/7/24
 */
public class ServiceGenerator {

    private static String baseUrl = "http://127.0.0.1";
    private static final int DEFAULT_CONNECT_TIMEOUT = 60;
    private static final int DEFAULT_READ_TIMEOUT = 60;
    private static final int DEFAULT_WRITE_TIMEOUT = 60;

    public static void init(String url) {
        baseUrl = url;
    }

    public static <T> T createNormalService(Class<T> serviceClass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.client(getNormalOkHttpClient()).build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient getNormalOkHttpClient(){
        return new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 增加 header
                .addInterceptor(new HeaderInterception())
                .addInterceptor(new ParamsInterception())
                .addInterceptor(new RetryTokenInterception())
                .build();
    }

}
