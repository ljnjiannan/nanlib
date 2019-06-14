# nanlib-android

[![](https://jitpack.io/v/site.nntec/nanlib.svg)](https://jitpack.io/#site.nntec/nanlib)

## 说明
该项目会封装一系列Android的快速开发工具。方便开发者在短时间内完成Android应用开发

## 1、网络请求request
使用retrofit+rxjava开发的一套网络请求工具，使用注解快速生成

### 下载
```
implementation 'site.nntec.nanlib:request:latest.release'
annotationProcessor 'site.nntec.nanlib:compile:latest.release'
```

### 使用

1、在Application或任意使用网络请求之前的位置使用`ServiceGenerator.init`初始化配置
```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceGenerator.init("http://192.168.30.185:8360");
    }
```

2、为所有的接口类加上`@RequestApi`注解

```
@RequestApi
public interface MyApi {

    @GET("/test")
    Observable<BaseResponseEntity<String>> test();
}

```

3、编译项目生成辅助类，使用`ServiceFactory`获取相应接口的实例，并调用接口方法实现网络请求

```
ServiceFactory.getMyApi().test()
    .compose(RxSchedulers.<BaseResponseEntity<String>>compose())
    .subscribe(new BaseObserver<String>() {
        @Override
        protected void onHandleSuccess(String s) {
            // 请求成功的代码
        }

        @Override
        protected void onHandleError(String msg) {
            super.onHandleError(msg);
            // 请求失败的代码
        }
    });
```

... to be continued

## 联系邮箱
ljnjiannan@163.com
