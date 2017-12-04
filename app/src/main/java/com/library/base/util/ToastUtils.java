package com.library.base.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.library.base.base.BaseApplication;


/**
 * Toast工具类
 * Created by Allen on 2017/4/14.
 */

public class ToastUtils {

    private static Toast sToast;
    private static Context context;

    public static void showShortToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(@StringRes int msg) {
        showShortToast(context.getString(msg));
    }

    public static void showLongToast(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    public static void showLongToast(@StringRes int msg) {
        showShortToast(context.getString(msg));
    }

    private static void showToast(String msg, int duration) {
        if (sToast == null) {
            context = BaseApplication.getInstance();
            sToast = Toast.makeText(context, msg, duration);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }
}
