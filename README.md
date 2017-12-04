# kcBaseLibrary
Android项目采用MVP模式框架，OKHTTP，Retrofit，RXJAVA，DES加密，下拉刷新上拉加载封装及自定义，仿微信图片选择及拍照，及自定义dialog。


主要流程如下：
新建一个TestActivity继承BaseKcActivity实现TestContract.View，RefreshLayout.OnRefreshListener（下拉刷新）
public class TestActivity extends BaseKcActivity implements TestContract.View, RefreshLayout.OnRefreshListener {
}
然后新建TestContract，里面会提供View，Presenter接口，及自定义的方法
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
新建TestPresenter类，继承BasePresenter，实现TestContract.Presenter
public class TestPresenter extends BasePresenter<TestContract.View> implements TestContract.Presenter<TestContract.View> {
通知model去请求数据，通知view去刷新界面
}
新建TestModel
public class TestModel {
发送请求，返回数据
}

自己摸索，有什么不对还请多多指教。
