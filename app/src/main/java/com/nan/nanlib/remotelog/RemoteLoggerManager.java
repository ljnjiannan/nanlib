package com.nan.nanlib.remotelog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoteLoggerManager {
    private static final String TAG = "RemoteLoggerManager";
    private static List<String> logList = new ArrayList<>();
    private static final Object LOCK = new Object();

    public static void addLog(String tag,String message) {
        synchronized (LOCK) {
            String logString = String.format("%s %s %s",DateUtil.getCurrentTime(),tag,message);
            logList.add(logString);
        }
    }

    public static void addLog(String type,String tag,String message) {

    }

    public static List<String> getLogList(int index) {
        synchronized (LOCK) {
            if (logList.size() == 0) {
                return new ArrayList<>();
            }
            LoggerUtil.i(TAG,"all size:  "+ logList.size());
            List<String> findList = logList.subList(0, logList.size() <index? logList.size() :index);
            List<String> result = new ArrayList<>(findList);
            LoggerUtil.i(TAG,"result size:  "+ result.size());
//        Collections.copy(result, findList);
            logList.removeAll(findList);
            LoggerUtil.i(TAG,"other size:  "+ logList.size());

            return result;
        }
    }

}
