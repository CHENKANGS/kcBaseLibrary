package com.library.base.net.gson;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * Created by fullteem on 2016/5/3.
 */
final class CustomResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String body = value.string();

            JSONObject json = new JSONObject(body);

            int code = json.optInt("resultCode");
            String msg = json.optString("resultMessage", "");

            if (code == 0) {
                if (json.has("resultData")) {
                    Object data = json.get("resultData");

                    body = data.toString();

                    return adapter.fromJson(body);
                } else {
                    return null;
                }
            } else {
                throw new RuntimeException(msg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}