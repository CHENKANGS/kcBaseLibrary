package com.library.base.module.contract;

import android.app.Activity;

import com.library.base.entity.bean.NewsBean;
import com.library.base.mvp.IPresenter;
import com.library.base.mvp.IView;

import java.util.List;

/**
 * Created by ChenKang on 2017/11/21.
 */

public class TestContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    public interface View extends IView {
        //成功
        void success(List<NewsBean> result);

        //失败
        void failed();

        //验证失败提示
        void showVerifyError(String msg);

        void showDialog();
        void dismissDialog();
        Activity getActivity();
        //申请权限
        void getRxPermissions();
    }

    public interface Presenter<V extends IView> extends IPresenter<V> {
        void login(String userName, String password);
    }
}
