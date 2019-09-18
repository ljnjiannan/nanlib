package com.nan.nanlib.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Converter;


/**
 * retrofit 配置类
 */
public class ServiceConfig {

    private String url;
    private int DEFAULT_CONNECT_TIMEOUT;
    private int DEFAULT_READ_TIMEOUT;
    private int DEFAULT_WRITE_TIMEOUT;
    private List<Converter.Factory> factoryList;
    private List<Interceptor> interceptorList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDEFAULT_CONNECT_TIMEOUT() {
        return DEFAULT_CONNECT_TIMEOUT;
    }


    public int getDEFAULT_READ_TIMEOUT() {
        return DEFAULT_READ_TIMEOUT;
    }


    public int getDEFAULT_WRITE_TIMEOUT() {
        return DEFAULT_WRITE_TIMEOUT;
    }


    public List<Converter.Factory> getFactoryList() {
        return factoryList;
    }


    public List<Interceptor> getInterceptorList() {
        return interceptorList;
    }


    public ServiceConfig(Builder builder) {
        if (builder.baseUrl.isEmpty()) throw new IllegalArgumentException("base url is empty");
        url = builder.baseUrl;
        DEFAULT_CONNECT_TIMEOUT = builder.DEFAULT_CONNECT_TIMEOUT;
        DEFAULT_READ_TIMEOUT = builder.DEFAULT_READ_TIMEOUT;
        DEFAULT_WRITE_TIMEOUT = builder.DEFAULT_WRITE_TIMEOUT;
        factoryList = builder.factoryList;
        interceptorList = builder.interceptorList;
    }

    public static final class Builder {
        private String baseUrl;
        private int DEFAULT_CONNECT_TIMEOUT;
        private int DEFAULT_READ_TIMEOUT;
        private int DEFAULT_WRITE_TIMEOUT;
        private List<Converter.Factory> factoryList;
        private List<Interceptor> interceptorList;

        public Builder() {
            baseUrl = "";
            DEFAULT_CONNECT_TIMEOUT = 60;
            DEFAULT_READ_TIMEOUT = 60;
            DEFAULT_WRITE_TIMEOUT = 60;
            factoryList = new ArrayList<>();
            interceptorList = new ArrayList<>();
        }

        public Builder setBaseUrl(String url) {
            baseUrl = url;
            return this;
        }

        public Builder addFactory(Converter.Factory... factories) {
            factoryList.addAll(Arrays.asList(factories));
            return this;
        }

        public Builder addInterceptor(Interceptor... interceptors) {
            interceptorList.addAll(Arrays.asList(interceptors));
            return this;
        }

        public Builder setConnectTimeout (int second) {
            DEFAULT_CONNECT_TIMEOUT = second;
            return this;
        }

        public Builder setReadTimeout (int second) {
            DEFAULT_READ_TIMEOUT = second;
            return this;
        }

        public Builder setWriteTimeout (int second) {
            DEFAULT_WRITE_TIMEOUT = second;
            return this;
        }

        public ServiceConfig build() {
            return new ServiceConfig(this);
        }

    }
}
