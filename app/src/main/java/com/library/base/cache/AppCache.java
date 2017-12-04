package com.library.base.cache;

import android.content.Context;
import android.text.TextUtils;

import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.UserBean;
import com.library.base.global.GlobalConstant;
import com.library.base.util.SPHelper;

/*

 * Description: 

 * File: AppCache.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/11/28 16:40

 *

 * Changes (from 2017/11/28)

 * -----------------------------------------------------------------

 * 2017/11/28 : Changes AppCache.java (k);

 * -----------------------------------------------------------------

 */
public class AppCache {
    private static AppCache appCache;
    private Context context ;

    private final String USER_MODEL = "USER_MODEL";

    private String Str;
    private UserBean userBean;

    private AppCache(Context context) {
        this.context = context ;
    }

    public static synchronized AppCache getInstance() {
        if (appCache == null) {
            appCache = new AppCache(BaseApplication.getInstance());
        }
        return appCache;
    }

    /**
     * 获取String
     * @return
     */
    public String getStr() {
        if (TextUtils.isEmpty(Str)) {
            return SPHelper.getStringSF(context, USER_MODEL);
        }
        return Str;
    }

    /**
     * 设置String
     * @param Str
     */
    public void setStr(String Str) {
        SPHelper.setStringSF(context , USER_MODEL , Str);
            this.Str = Str;
    }

    /**
     * 设置对象
     * @param key
     * @param model
     * @param <T>
     * @return
     */
    public <T> boolean setObjectDevice(String key, T model) {
        return SPHelper.setDeviceData(context , key , model);
    }

    /**
     * 获取对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getObjectDevice(String key) {
        return SPHelper.getDeviceData(context, key);
    }

}
