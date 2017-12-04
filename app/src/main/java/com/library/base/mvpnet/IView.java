package com.library.base.mvpnet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by ChenKang on 2017/11/21.
 */

public interface IView {

    void showMessage(@NonNull String message);

    void showMessage(@StringRes int messageId);

    void showLoading(String msg);

    void hideLoading();

    void showError(JesException e);

    /**
     * 获得Context
     */
    Context getContext();

}
