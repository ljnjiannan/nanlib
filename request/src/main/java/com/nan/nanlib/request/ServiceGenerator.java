package com.nan.nanlib.request;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author liujiannan
 * @date 2018/7/24
 */
public class ServiceGenerator {

    private static ServiceConfig serviceConfig;

    public static void init(ServiceConfig config) {
        serviceConfig = config;
    }

    public static <T> T createNormalService(Class<T> serviceClass) {
        if (serviceConfig == null) throw new NullPointerException("please init config");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(serviceConfig.getUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        if (serviceConfig.getFactoryList().size() > 0 ) {
            for (Converter.Factory factory : serviceConfig.getFactoryList()) {
                builder.addConverterFactory(factory);
            }
        }else {
            builder.addConverterFactory(GsonConverterFactory.create());
        }

        Retrofit retrofit = builder.client(getNormalOkHttpClient()).build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient getNormalOkHttpClient(){
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(serviceConfig.getDEFAULT_CONNECT_TIMEOUT(), TimeUnit.SECONDS)
                .readTimeout(serviceConfig.getDEFAULT_READ_TIMEOUT(), TimeUnit.SECONDS)
                .writeTimeout(serviceConfig.getDEFAULT_WRITE_TIMEOUT(), TimeUnit.SECONDS);

        for (Interceptor interceptor : serviceConfig.getInterceptorList()) {
            client.addInterceptor(interceptor);
        }

        return client.build();
    }

}
