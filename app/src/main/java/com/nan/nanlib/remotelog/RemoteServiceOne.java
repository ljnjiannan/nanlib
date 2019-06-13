package com.nan.nanlib.remotelog;


import com.nan.nanlib.request.BaseResponseEntity;
import com.nan.nanlib.request.annotation.RequestApi;
import com.nan.nanlib.request.annotation.RequestGet;

import io.reactivex.Observable;
import retrofit2.http.GET;

@RequestApi
public interface RemoteServiceOne {

    @GET("")
    Observable<BaseResponseEntity<String>> getHospitalInfo();

    @GET("")
    Observable<BaseResponseEntity<String>> getHospitalInfo2();

    @GET("")
    Observable<BaseResponseEntity<String>> getHospitalInfo3();

}
