package com.nan.nanlib.request.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liujiannan
 * @date 2018/7/24
 */
public class HeaderInterception implements Interceptor {

    private static final String TAG = "HeaderInterception";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        return chain.proceed(builder.build());
    }

}
