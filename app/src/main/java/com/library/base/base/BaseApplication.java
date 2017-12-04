package com.library.base.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.library.base.R;
import com.library.base.global.GlobalConstant;
import com.library.base.mvpnet.nets.InitRetrofit;

/**
 * Created by ChenKang on 2017/11/14.
 */

public class BaseApplication extends Application {

    static BaseApplication instance;
    public final boolean isDebug = false;// 是否为调试模式

    // IWXAPI 是第三方app和微信通信的openapi接口
//    public static IWXAPI api;

    private static InitRetrofit sInitRetrofit;

    public BaseApplication() {
        instance = this;
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }
    public static Context get() {
        return instance.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {

//        Bugtags.start("37be3262ff13df8bd6df7b9b818011d6", this, Bugtags.BTGInvocationEventNone);

//        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        api = WXAPIFactory.createWXAPI(this, GlobalConstant.APP_ID, false);
//        // 将该app注册到微信
//        api.registerApp(GlobalConstant.APP_ID);

//        PushManager.getInstance().initialize(this);
//
////        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), PushIntentService.class);
//        //		在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(this);


    }

    public synchronized static InitRetrofit getRetrofitClient() {
        if (sInitRetrofit == null) {
            sInitRetrofit = new InitRetrofit();
        }
        return sInitRetrofit;
    }

    /**
     * 重建Retrofit
     */
    public synchronized static void resetRetrofitClient() {
        sInitRetrofit = new InitRetrofit();
    }

    /**
     * 跳转登录页
     */
    public void jumpLoginActivity() {
        Intent intent = new Intent(GlobalConstant.LOGON_ACTION);
        sendBroadcast(intent);
    }

    /**
     * 加密秘钥
     * @return
     */
    public String getDESKey() {
        return getString(R.string.DES_KEY);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


}
