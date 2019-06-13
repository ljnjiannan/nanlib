package com.nan.nanlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.nan.nanlib.remotelog.LogService;
import com.nan.nanlib.remotelog.LoggerUtil;
import com.nan.nanlib.remotelog.RemoteService;
import com.nan.nanlib.request.BaseObserver;
import com.nan.nanlib.request.BaseResponseEntity;
import com.nan.nanlib.request.RxSchedulers;
import com.nan.nanlib.request.ServiceGenerator;
import com.nan.nanlib.request.impl.RemoteServiceImpl;
import com.nan.nanlib.request.impl.ServiceFactory;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceGenerator.init("http://192.168.30.185:8360");

        ServiceFactory.getRemoteService().getHospitalInfo()
                .compose(RxSchedulers.<BaseResponseEntity<String>>compose())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        LoggerUtil.i(TAG, "hahah  "+s);
                    }
                });

    }
}
