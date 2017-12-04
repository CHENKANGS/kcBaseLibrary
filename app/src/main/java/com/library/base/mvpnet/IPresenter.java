package com.library.base.mvpnet;

/**
 * Created by ChenKang on 2017/11/21.
 */

public interface IPresenter <V extends IView> {
    /**
     * 绑定View
     * @param view
     */
    void attachView(V view);

    /**
     * 解绑View
     */
    void detachView();

    void showErrorMessage(String error);
}

