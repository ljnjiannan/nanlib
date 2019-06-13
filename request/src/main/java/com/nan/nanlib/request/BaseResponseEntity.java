package com.nan.nanlib.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author liujiannan
 * @date 2018/7/24
 */
public class BaseResponseEntity<T> {
    @SerializedName("success")
    protected boolean success;
    @SerializedName("entity")
    protected T data;
    @SerializedName("message")
    protected String message;
    @SerializedName("token")
    protected String token;
    protected String errorCode;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
