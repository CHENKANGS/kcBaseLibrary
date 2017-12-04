package com.library.base.module.model;

import android.util.Log;

import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.NewsBean;
import com.library.base.mvpnet.nets.HttpResultFunc;
import com.library.base.util.encrypt.Aes;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ChenKang on 2017/11/21.
 */

public class TestModel {

    private static TestModel INSTANCE;

    private TestModel() {
    }

    public static synchronized TestModel getInstance() {
        if (INSTANCE == null) {
            synchronized (TestModel.class) {
                INSTANCE = new TestModel();
            }
        }
        return INSTANCE;
    }

    public Observable<List<NewsBean>> login(String userName, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("appkey", "lvX0jaeZBIdwtHyz");
        params.put("appsecret", "e125579c07bad67d2bde9b3a79cdb6c2");
//        String encStr = Des.EncryptAsDoNet("e125579c07bad67d2bde9b3a79cdb6c2", BaseApplication.getInstance().getDESKey());
        String encryStr = Aes.encrypt(BaseApplication.getInstance().getDESKey(), "e125579c07bad67d2bde9b3a79cdb6c2");
        Log.e("==encryStr==" , encryStr+"");
//        String decStr = Des.DecryptDoNet(encStr, BaseApplication.getInstance().getDESKey());
        String decryStr = Aes.decrypt(BaseApplication.getInstance().getDESKey(), encryStr);
        Log.e("==decryStr==" , decryStr+"");

//        return BaseApplication.getRetrofitClient().getChinaJesApi().getZXss("lvX0jaeZBIdwtHyz" , "e125579c07bad67d2bde9b3a79cdb6c2")
        return BaseApplication.getRetrofitClient().getChinaJesApi().getZXh(params)
                .map(new HttpResultFunc<List<NewsBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
