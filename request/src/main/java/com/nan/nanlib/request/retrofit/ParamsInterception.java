package com.nan.nanlib.request.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author liujiannan
 * @date 2018/7/25
 */
public class ParamsInterception implements Interceptor{

    private static final String TAG = "ParamsInterception";
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody value = request.body();

        return chain.proceed(request);
    }

    private String bodyToString(final RequestBody request){
        try {
            final Buffer buffer = new Buffer();
            if(request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }
}
