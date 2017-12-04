package com.library.base.mvp.nets;


import com.google.gson.Gson;
import com.library.base.entity.basemodel.ResponseModel;
import com.library.base.global.GlobalConstant;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Gson解析器 根据后台返回结果是否为10000
 * Created by Allen on 2017/6/9.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
        //处理不正常返回结果的情况
        ResponseModel httpResult = gson.fromJson(response, ResponseModel.class);
        if (httpResult.getRet() .equals(GlobalConstant.REQUEST_SUCCESS) ) {
            return gson.fromJson(response, type);
        } else {
            //抛一个自定义ResultException 传入失败时候的状态码，和信息
            ResponseModel jesResponse = new ResponseModel();
            jesResponse.setMsg(httpResult.getMsg());
            jesResponse.setRet(httpResult.getRet());
            return (T) jesResponse;
        }
    }
}
