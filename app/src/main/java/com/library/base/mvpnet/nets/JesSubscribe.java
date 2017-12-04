package com.library.base.mvpnet.nets;



import com.library.base.base.BaseApplication;
import com.library.base.global.GlobalConstant;
import com.library.base.mvpnet.IView;
import com.library.base.mvpnet.JesException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 通用Subscribe
 * 统一处理异常
 * Created by Allen on 2017/4/14.
 */

public abstract class JesSubscribe<T> extends Subscriber<T> {

    private IView mView;
    private boolean showLoading = false;//显示loading框
    private String Loading_msg = "" ; //显示load信息提示
    public JesSubscribe(IView view ) {
        this.mView = view;
    }

    public JesSubscribe(IView view , boolean showLoading ,String Loading_msg) {
        this.mView = view;
        this.showLoading = showLoading;
        this.Loading_msg = Loading_msg;
    }
    public JesSubscribe(IView view , boolean showLoading) {
        this.mView = view;
        this.showLoading = showLoading;
    }

    public JesSubscribe setShowLoading(boolean showLoading) {
        this.showLoading = showLoading;
        return this;
    }

    @Override
    public void onStart() {
        if (showLoading) {
            mView.showLoading(Loading_msg);
        }
    }

    @Override
    public void onCompleted() {
        if (showLoading) {
            mView.hideLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        mView.hideLoading();
        JesException exception;
        if (e instanceof SocketTimeoutException) {
            exception = new JesException("连接超时", 100020);
        } else if (e instanceof ConnectException) {
            exception = new JesException("网络中断，请检查您的网络状态", 100021);
        } else if (e instanceof JesException) {
            exception = (JesException) e;
            handleJesException(exception);
            _onError((JesException) e);
        } else {
            exception = new JesException(e.getMessage(), 100050);
        }
        mView.showError(exception);
    }

    private void handleJesException(JesException exception) {
        switch (exception.getCode()){
            case GlobalConstant.NET_CODE_RE_LOGIN:
                mView.showMessage("登录过期，请重新登录");
                BaseApplication.getInstance().jumpLoginActivity();
                break;
        }
    }

    @Override
    public void onNext(T result) {
        _onSuccess(result);
    }

    public abstract void _onSuccess(T t);

    public void _onError(JesException e) {

    }

}
