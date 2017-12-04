package com.library.base.base;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.library.base.BuildConfig;
import com.library.base.R;
import com.library.base.cache.AppBaseCache;
import com.library.base.global.GlobalConstant;
import com.library.base.mvpnet.IView;
import com.library.base.mvpnet.JesException;
import com.library.base.util.ToastUtils;
import com.library.base.util.UiUtils;
import com.library.base.util.permission.GuaranteeCallBack;
import com.library.base.util.permission.HiPermission;
import com.library.base.util.permission.SimplePermissionCallBack;
import com.library.base.view.BaseMainActivity;
import com.library.base.widget.dialog.LoadDialog;
import com.library.base.widget.dialog.QMUITipDialog;

import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ChenKang on 2017/11/21.
 */

public class BaseKcActivity extends AppCompatActivity implements IView , TextView.OnEditorActionListener{
    protected Toolbar mToolBar;
    protected TabLayout mTab;
    private TextView mBarTitle;
    private AppBarLayout appBar;
    private LinearLayout ll_search;
    private ImageButton ib_search;
    private ImageButton ib_add;

    protected Context mContext;
    private QMUITipDialog tipDialog ;

//    private SuperSwipeRefreshLayout swipeRefreshLayout;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private GifImageView gifView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    //注销广播
    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AppBaseCache.getInstance().resetAppBaseCache();
            Intent loginIntent = new Intent(mContext, BaseMainActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//清空堆栈
            startActivity(loginIntent);
            finish();
        }
    };
    private LoadDialog mLoadDialog;
    private ActionBar mActionBar;
    private int menuRes = -1;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        registerReceiver(mLoginReceiver, new IntentFilter(GlobalConstant.LOGON_ACTION));
    }

    protected void createView(@LayoutRes int layoutId) {
        setContentView(R.layout.activity_base);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        FrameLayout mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mTab = (TabLayout) findViewById(R.id.tab);
        mBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        ib_search = (ImageButton) findViewById(R.id.ib_search);
        ib_add = (ImageButton) findViewById(R.id.ib_add);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(this);
        setSupportActionBar(mToolBar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        View view = View.inflate(this, layoutId, null);
        mFlContent.addView(view);
        ButterKnife.bind(this, view);
        UiUtils.getRootView(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
    }

    public void hideToolBar() {
        appBar.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarNoShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setElevation(0);
        }
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarHasShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setElevation(10);
        }
    }

    /**
     * 绑定TabLayout
     *
     * @param viewPager
     */
    public void setTabWithViewPager(ViewPager viewPager) {
        mTab.setVisibility(View.VISIBLE);
        mTab.setupWithViewPager(viewPager);
    }

    /**
     * 隐藏TabLayout
     */
    public void hideTabLayout() {
        mTab.setVisibility(View.GONE);
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag) {
        addFragment(containerViewId, fragment, fragmentTag, false);
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment, fragmentTag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    /**
     * 初始化标题栏
     *
     * @param isBack
     * @param title
     */
    public void initTitleBar(boolean isBack, String title) {
        mBarTitle.setText(title);
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(isBack);
        }
    }

    public void initTitleBar(boolean isBack, @StringRes int title) {
        initTitleBar(isBack, getString(title));
    }

    /**
     * 带菜单
     *
     * @param isBack
     * @param title
     * @param menuRes
     */
    public void initTitleBar(boolean isBack, String title, @MenuRes int menuRes, OnMenuItemClickListener onMenuItemClickListener) {
        initTitleBar(isBack, title);
        this.menuRes = menuRes;
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void initTitleBar(boolean isBack, @StringRes int title, @MenuRes int menuRes, OnMenuItemClickListener onMenuItemClickListener) {
        initTitleBar(isBack, getString(title), menuRes, onMenuItemClickListener);
    }

    /**
     * 设置Toolbar滑动Flag
     *
     * @param flag
     */
    public void setToolBarScrollFlag(int flag) {
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) ((View) (mToolBar.getParent())).getLayoutParams();
        params.setScrollFlags(flag);
    }

    /**
     * 搜索框
     *
     * @param hintText
     * @param searchListener
     */
    public void showSearchView(String hintText, OnSearchListener searchListener) {
        showSearchView(hintText, searchListener, null);
    }

    public void showSearchView(String hintText, final OnSearchListener searchListener, View.OnClickListener addListener) {
        if (addListener == null) {
            ib_add.setVisibility(View.GONE);
        } else {
            ib_add.setVisibility(View.VISIBLE);
        }
        onSearchListener = searchListener;
        ll_search.setVisibility(View.VISIBLE);
        et_search.setHint(hintText);
        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchListener.onSearch(et_search.getText().toString());
            }
        });
        ib_add.setOnClickListener(addListener);
    }

    private OnSearchListener onSearchListener;

    public interface OnSearchListener {
        void onSearch(String str);
    }

    public void hideSearchView() {
        ll_search.setVisibility(View.GONE);
    }

    public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
        et_search.clearFocus();
        hideKeyboard();
        if (onSearchListener != null) {
            onSearchListener.onSearch(et_search.getText().toString());
        }
        return true;
    }


    /**
     * 定义菜单栏
     *
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onItemClick(item);
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnMenuItemClickListener {
        void onItemClick(MenuItem item);
    }

    private OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    /**
     * 修改标题
     *
     * @param title
     */
    public void changeTitle(String title) {
        mBarTitle.setText(title);
    }

    public void changeTitle(@StringRes int title) {
        changeTitle(getString(title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuRes != -1) {
            getMenuInflater().inflate(menuRes, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected void startActivityNoValue(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
    /**
     * 显示加载框
     */
    @Override
    public void showLoading(String msg) {
//        if (mLoadDialog == null) {
//            mLoadDialog = new LoadDialog(this);
//        }
//        mLoadDialog.show();

        if (tipDialog == null){
                tipDialog = new QMUITipDialog.Builder(getContext())
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord(msg)
                        .create();
        }
        tipDialog.show();
    }
    /**
     * 隐藏加载框
     */
    @Override
    public void hideLoading() {
//        if (mLoadDialog != null) {
//            mLoadDialog.cancel();
//        }
        if (null != tipDialog){
            tipDialog.dismiss();
        }
    }

    @Override
    public void showError(JesException e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
        }else {
            showMessage("出现异常");
        }
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    protected boolean getNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        return false;
    }

    /**
     * 纯文字提示
     * @param message
     */
    protected void showDialogMessage(String message) {
            tipDialog = new QMUITipDialog.Builder(getContext())
                    .setTipWord(message)
                    .create();
        tipDialog.show();
        new Handler().postDelayed(new Runnable(){
            public void run() {
                tipDialog.dismiss();
            }
        }, 1000);
    }

    /**
     * 显示加载框
     */
    protected void showLoadDialog() {
        if (tipDialog == null){
            tipDialog = new QMUITipDialog.Builder(getContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }
        tipDialog.show();
    }
    /**
     * 隐藏加载框
     */
    protected void hideLoadDialog() {
        if (null != tipDialog)
        tipDialog.dismiss();
    }



    protected void showToastMessage(String message) {
        if (! TextUtils.isEmpty(message)) {
            ToastUtils.showShortToast(message);
        }
    }

    protected void showToastMessage(@StringRes int messageId) {
        if (messageId != 0) {
            String message = mContext.getResources().getString(messageId);
            showToastMessage(message);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        showToastMessage(message);
    }

    @Override
    public void showMessage(@StringRes int StringRes) {
        showToastMessage(StringRes);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    protected void onDestroy() {
        //反注册广播
        unregisterReceiver(mLoginReceiver);
        super.onDestroy();
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 请求权限
     * @param permission
     * @param tips
     * @param callBack
     */
    protected void checkPermission(String permission, String tips, final GuaranteeCallBack callBack) {
        HiPermission.create(this).checkSinglePermission(permission, new SimplePermissionCallBack(this, tips) {
            @Override
            public void onGuarantee(String permission, int position) {
                callBack.onGuarantee();
            }
        });
    }

    /**
     * 短信权限
     * @param callBack
     */
    protected void checkSMSPermission(final GuaranteeCallBack callBack) {
        checkPermission( Manifest.permission.READ_SMS, "请开启短信权限", callBack);
    }

}
