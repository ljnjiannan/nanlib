package com.nan.nanlib.remotelog;


import com.nan.nanlib.request.BaseResponseEntity;
import com.nan.nanlib.request.annotation.RequestApi;
import com.nan.nanlib.request.annotation.RequestGet;

import io.reactivex.Observable;
import retrofit2.http.GET;

@RequestApi
public interface RemoteService {

    @RequestGet
    @GET("/test")
    Observable<BaseResponseEntity<String>> getHospitalInfo();

}
