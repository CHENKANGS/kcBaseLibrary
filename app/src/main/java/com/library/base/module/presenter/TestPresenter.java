package com.library.base.module.presenter;

import android.text.TextUtils;

import com.library.base.cache.AppBaseCache;
import com.library.base.cache.RetrofitCache;
import com.library.base.cache.SPFHelper;
import com.library.base.entity.bean.NewsBean;
import com.library.base.module.contract.TestContract;
import com.library.base.module.model.TestModel;
import com.library.base.mvp.BasePresenter;
import com.library.base.mvp.JesException;
import com.library.base.mvp.nets.JesSubscribe;
import com.socks.library.KLog;

import java.util.List;

import rx.Subscription;

/**
 * Created by ChenKang on 2017/11/21.
 */

public class TestPresenter extends BasePresenter<TestContract.View> implements TestContract.Presenter<TestContract.View> {

    private TestModel mModel;

    public TestPresenter() {
        mModel = TestModel.getInstance();
    }

    @Override
    public void login(final String userName, final String password) {
//        if (!verifyAccount(userName, password)) {
//            return;
//        }
//        mView.showDialog();
        Subscription mSubscribe = mModel.login(userName, password)
                .subscribe(new JesSubscribe<List<NewsBean>>(mView , true ) {
                    @Override
                    public void _onSuccess(List<NewsBean> result) {
//                        AppBaseCache.getInstance().setUser(result);
                        SPFHelper spfHelper = new SPFHelper(mView.getContext(), "");
                        spfHelper.putString("loginName", userName);
//                        mView.dismissDialog();
                        mView.success(result);
                    }

                    @Override
                    public void _onError(JesException e) {
                        mView.showMessage(e.getMessage());
                    }
                });
        mSubscriptions.add(mSubscribe);
    }

    /**
     * 简单验证下账户
     *
     * @param userName
     * @param password
     */
    private boolean verifyAccount(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            mView.showVerifyError("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mView.showVerifyError("密码不能为空");
            return false;
        }
        return true;
    }

    private void testCache() {
        mSubscriptions.add(RetrofitCache.load("test", mModel.login("aaaa", "bbbb"), true, true)
                .subscribe(new JesSubscribe<List<NewsBean>>(mView ) {
                    @Override
                    public void _onSuccess(List<NewsBean> response) {
                        KLog.d(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }

}
