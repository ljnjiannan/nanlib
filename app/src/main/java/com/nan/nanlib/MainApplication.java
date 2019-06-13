package com.nan.nanlib;

import android.app.Application;

import com.nan.nanlib.remotelog.LoggerUtil;
import com.nan.nanlib.request.annotation.RequestApi;

import java.lang.reflect.Field;

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
