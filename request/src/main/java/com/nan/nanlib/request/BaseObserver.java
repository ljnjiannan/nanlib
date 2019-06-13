package com.nan.nanlib.request;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author liujiannan
 * @date 2018/7/24
 */
public abstract class BaseObserver<T> implements Observer<BaseResponseEntity<T>> {

    private static final String TAG = "BaseObserver";

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponseEntity<T> value) {
        if (value.isSuccess()) {
            if (value.getToken()!=null && !value.getToken().isEmpty()) {
            }
            T t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
    }

    @Override
    public void onComplete() {
    }


    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        Log.d(TAG, msg);
    }
}
