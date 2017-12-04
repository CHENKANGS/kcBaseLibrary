package com.library.base.mvpnet.nets;


import com.library.base.BuildConfig;
import com.library.base.global.GlobalConstant;
import com.library.base.net.APIService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 初始化Retrofit
 * Created by allen on 2017/4/12.
 */

public class InitRetrofit {

    private final Retrofit client;

    public InitRetrofit() {

        client = new Retrofit.Builder()
                .baseUrl(GlobalConstant.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .addConverterFactory(ResponseConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        newBuilder.interceptors().add(new AddCookiesInterceptor());
        //日志打印
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            newBuilder.addInterceptor(loggingInterceptor);
        }
        //设置缓存路径跟大小
        newBuilder.cache(new Cache(new File(GlobalConstant.NET_CATCH_DIR), GlobalConstant.NET_CATCH_SIZE_52428800));
        newBuilder.connectTimeout(GlobalConstant.NET_TIMEOUT_60, TimeUnit.SECONDS);
        newBuilder.readTimeout(GlobalConstant.NET_TIMEOUT_60, TimeUnit.SECONDS);
        newBuilder.writeTimeout(GlobalConstant.NET_TIMEOUT_120, TimeUnit.SECONDS);
        return newBuilder.build();
    }

    public APIService getChinaJesApi() {
        return client.create(APIService.class);
    }

}
