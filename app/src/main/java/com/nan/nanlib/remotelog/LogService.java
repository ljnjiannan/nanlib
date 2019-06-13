package com.nan.nanlib.remotelog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LogService extends Service {

    private static final String TAG = "LogService";
    private List<String> logList = new ArrayList<>();
    private String[] logArray = new String[]{};


    @Override
    public void onCreate() {
        super.onCreate();
        startLogThread();
        startUploadThread();
    }

    private void startLogThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    RemoteLoggerManager.addLog(TAG,"add log");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void startUploadThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<String> logs = RemoteLoggerManager.getLogList(100);
                    LoggerUtil.i(TAG,logs.size() + "  size");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
