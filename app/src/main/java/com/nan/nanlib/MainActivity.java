package com.nan.nanlib;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nan.nanlib.request.BaseObserver;
import com.nan.nanlib.request.BaseResponseEntity;
import com.nan.nanlib.request.RxSchedulers;
import com.nan.nanlib.request.ServiceConfig;
import com.nan.nanlib.request.ServiceGenerator;
import com.nan.nanlib.request.impl.ServiceFactory;
import com.nan.nanlib.request.retrofit.HeaderInterception;
import com.nan.nanlib.utils.LoggerUtil;

import butterknife.BindView;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceConfig.Builder builder = new ServiceConfig.Builder();
        builder.setBaseUrl("http://192.168.123.1") // 必须 baseUrl
                .setConnectTimeout(1000) // 连接超时时间
                .setReadTimeout(1000) // 读取超时时间
                .setWriteTimeout(1000) // 写入超时时间
                .addFactory(GsonConverterFactory.create()) // 添加Converter
                .addInterceptor(new HeaderInterception()); // 添加interceptor

        ServiceGenerator.init(builder.build());

        ServiceFactory.getRemoteService().getHospitalInfo("haha")
                .compose(RxSchedulers.<BaseResponseEntity<String>>compose())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        LoggerUtil.i(TAG, "hahah  "+s);
                    }
                });

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter();

    }
}
