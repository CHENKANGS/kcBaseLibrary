package com.library.base.mvpnet;



/**
 *
 * Created by ChenKang on 2017/11/21.
 */

public interface ILoadDataPresenter<V extends IView> extends IPresenter<V> {
    /**
     * 显示加载滚动条
     */
    void showLoading();

    /**
     * 隐藏加载滚动条
     */
    void hideLoading();

    /**
     * 显示加载重试
     */
    void showRetry();

    /**
     * 隐藏加载重试
     */
    void hideRetry();

    /**
     * 中途停止任务
     */
    void halfwayStop();
}
