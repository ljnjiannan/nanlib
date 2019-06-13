package com.nan.nanlib.request;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author liujiannan
 * @date 2018/7/25
 */
public class RequestBodyBuilder {

    private JSONObject param = new JSONObject();

    public RequestBodyBuilder() {
    }

    public RequestBodyBuilder addParams(String key, String value) {
        try {
            param.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public RequestBodyBuilder addParams(String key, int value) {
        try {
            param.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public RequestBody build(){
        return RequestBody.create(MediaType.parse("application/json"),param.toString());
    }
}
