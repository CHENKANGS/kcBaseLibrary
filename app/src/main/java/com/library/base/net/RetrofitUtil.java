package com.library.base.net;


import android.util.Log;


import com.library.base.global.GlobalConstant;
import com.library.base.global.GlobalVariable;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtil {
    项目采用MVP模式框架，OKHTTP，Retrofit，RXJAVA，DES加密，下拉刷新上拉加载封装及自定义，仿微信图片选择及拍照，及自定义dialog。
    git@github.com:CHENKANGS/kcBaseLibrary.git
    /**
     * 服务器地址
     */
    private static final String API_HOST = GlobalConstant.API_HOST;
    private static final int DEFAULT_TIMEOUT = 10;

    private APIService service;
    private Retrofit retrofit;


    public APIService getService() {
        if (service == null) {
            service = getRetrofit().create(APIService.class);
        }
        return service;
    }


    private Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    //设置超时
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                    .readTimeout( DEFAULT_TIMEOUT, TimeUnit.SECONDS )
                    //log请求参数
                    .addInterceptor(interceptor)
                    //统一加入token值
                    .addNetworkInterceptor(mTokenInterceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addConverterFactory(CustomConverterFactory.create())
                    //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    public String post( String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    private Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (GlobalVariable.token == null || alreadyHasAuthorizationHeader(originalRequest)) {
                return chain.proceed(originalRequest);
            }
            Log.e("==token==",GlobalVariable.token);
            Request authorised = originalRequest.newBuilder()
                    .header("token", GlobalVariable.token)
                    .build();
            return chain.proceed(authorised);
        }
    };

    private boolean alreadyHasAuthorizationHeader(Request originalRequest) {
        return null != originalRequest.headers();
    }

    protected RequestBody createJsonBody(String json) {
        RequestBody body = RequestBody.create(MediaType
                .parse("application/json;charset=utf-8"), json);
        return body;
    }

    protected String toJson( RequestParams params) {
        JSONObject jsonObject = new JSONObject(params.get());
        return jsonObject.toString();
    }

}
