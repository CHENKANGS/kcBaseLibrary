package com.library.base.mvpnet;

/**
 * 加载数据的View层
 * Created by ChenKang on 2017/11/21.
 */
public interface ILoadDataView extends IView {
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
     * 中途停止
     */
    void halfwayStop();


}
