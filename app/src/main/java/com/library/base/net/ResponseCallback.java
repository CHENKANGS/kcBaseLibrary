package com.library.base.net;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.library.base.base.BaseApplication;
import com.library.base.entity.basemodel.ResponseModel;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by fullteem on 2016/4/18.
 */
public abstract class ResponseCallback<T> implements Callback<ResponseModel<T>> {

    private Context mContext;

    public ResponseCallback(Context mContext){
        this.mContext=mContext;
    }

    public abstract void onSuccess(T resultData);

    @Override
    public void onResponse(Call<ResponseModel<T>> call, retrofit2.Response<ResponseModel<T>> response) {
        if (response.body() != null) {
            if (response.body().isSuccess()) {
                onSuccess((T) response.body().result);
                //((BaseActivity)mContext).dismissProgressDialog();
            } else {
                onFailure(call, new Throwable(response.body().msg));
            }
        }
    }


    @Override
    public void onFailure(Call<ResponseModel<T>> call, Throwable t) {
       //((BaseActivity)mContext).dismissProgressDialog();
        if (t instanceof SocketTimeoutException ) {
            Toast.makeText(BaseApplication.get(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (t instanceof ConnectException ) {
            Toast.makeText(BaseApplication.get(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
        } else if (t instanceof ConnectTimeoutException ) {
            Toast.makeText(BaseApplication.get(), "服务器异常，请求超时", Toast.LENGTH_SHORT).show();
        }else if (t instanceof UnknownHostException ) {
            Toast.makeText(BaseApplication.get(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (t instanceof HttpException ) {
            HttpException httpException = (HttpException) t;
           String msg = convertStatusCode(httpException);
            Toast.makeText(BaseApplication.get(), msg, Toast.LENGTH_SHORT).show();

        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException ) {
            String msg = "数据解析错误";
            Toast.makeText(BaseApplication.get(), msg, Toast.LENGTH_SHORT).show();
        }else {
            Log.e("==getMessage==",t.getMessage());
//            Toast.makeText(App.get(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private String convertStatusCode( HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}