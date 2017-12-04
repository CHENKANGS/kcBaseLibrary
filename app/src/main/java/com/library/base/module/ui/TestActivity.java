package com.library.base.module.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.library.base.R;
import com.library.base.adapter.ZX_HomeItem;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.adapter.base_adapter.CommonRcvAdapter;
import com.library.base.adapter.base_adapter.rcv.RcvOnItemClickListener;
import com.library.base.base.BaseKcActivity;
import com.library.base.cache.AppCache;
import com.library.base.entity.bean.NewsBean;
import com.library.base.entity.bean.PicBean;
import com.library.base.entity.bean.UserBean;
import com.library.base.module.contract.TestContract;
import com.library.base.module.presenter.TestPresenter;
import com.library.base.util.permission.GuaranteeCallBack;
import com.library.base.widget.FullyLinearLayoutManager;
import com.library.base.widget.TitleBar;
import com.library.base.widget.dialog.QMUIBottomSheet;
import com.library.base.widget.dialog.QMUITipDialog;
import com.library.base.widget.image.ImagePagerActivity;
import com.library.base.widget.refresh.RefreshLayout;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ChenKang on 2017/11/21.
 */

public class TestActivity extends BaseKcActivity implements TestContract.View, RefreshLayout.OnRefreshListener {

    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    RefreshLayout refresh;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText22)
    EditText editText22;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.image)
    ImageView image;
    private TestPresenter mLoginPresenter;
    @BindView(R.id.text)
    TextView mEtUsername;
    private CommonRcvAdapter<NewsBean> adapter;

    private List<PicBean> imgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_base_main);
        hideToolBar();
//        String loginName = new SPFHelper(this, "").getString("loginName", "");
//        mEtUsername.setText(loginName);
        mLoginPresenter = new TestPresenter();
        mLoginPresenter.attachView(this);
//        mLoginPresenter.login("", "");

        mEtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList();
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetGrid();
            }
        });

        refresh.setOnRefreshListener(this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                mHandler.sendEmptyMessageDelayed(1, 500);
            }
        });

        getEditText();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, "请开启相机权限", new GuaranteeCallBack() {
                    @Override
                    public void onGuarantee() {
                        ImageSelectorActivity.start(TestActivity.this, 5, ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
                    }
                });
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (Serializable) imgList);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_IMAGE:
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                    for (int i = 0; i < images.size() ; i++) {
                        PicBean bean = new PicBean();
                        bean.setPath(images.get(i));
                        bean.setType(ImagePagerActivity.TYPE_IMAGE_XC);
                        imgList.add(bean);
                    }
                    Glide.with(TestActivity.this)
                            .load(new File(images.get(0)))
                            .into(image);
                    image.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


    private void getEditText() {
        String name = editText.getText().toString().trim();

        List<NewsBean> ks_injured_damages = AppCache.getInstance().getObjectDevice("list");
        if (null != ks_injured_damages && 0 < ks_injured_damages.size()) {
            editText22.setText(ks_injured_damages.get(2).getTitle());
        }

        UserBean user = AppCache.getInstance().getObjectDevice("User");
//        UserBean user =  SPHelper.getDeviceData(TestActivity.this , "User") ;
        if (null != user) {
            editText.setText(user.getName());
        }

        final UserBean userBean = new UserBean();

        final EditText[] ids = {editText, editText22};

        for (int i = 0; i < ids.length; i++) {
            final int finalI = i;
            ids[ i ].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                    } else {
                        // 此处为失去焦点时的处理内容
                        String edit = ids[ finalI ].getText().toString().trim();
                        showDialogMessage(edit);
                        switch (finalI) {
                            case 0:
                                userBean.setName(edit);
                                AppCache.getInstance().setObjectDevice("User", userBean);
//                                SPHelper.setDeviceData(TestActivity.this , "User" , userBean) ;
                                break;
                        }
                    }
                }
            });
        }

    }


    //资讯List
    private void recyclerList(List<NewsBean> result) {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new CommonRcvAdapter<NewsBean>(result = result == null ? new ArrayList<NewsBean>() : result) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new ZX_HomeItem(TestActivity.this);
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RcvOnItemClickListener(this, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final QMUITipDialog tipDialog;
                switch (position) {
                    case 0:
                        //正在加载
                        tipDialog = new QMUITipDialog.Builder(getContext())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("正在加载")
                                .create();
                        break;
                    case 1:
                        tipDialog = new QMUITipDialog.Builder(getContext())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                .setTipWord("发送成功")
                                .create();
                        break;
                    case 2:
                        tipDialog = new QMUITipDialog.Builder(getContext())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                                .setTipWord("发送失败")
                                .create();
                        break;
                    case 3:
                        tipDialog = new QMUITipDialog.Builder(getContext())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                                .setTipWord("请勿重复操作")
                                .create();
                        break;
                    case 4:
                        tipDialog = new QMUITipDialog.Builder(getContext())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                .create();
                        break;
                    case 5:
                        tipDialog = new QMUITipDialog.Builder(getContext())
                                .setTipWord("请勿重复操作")
                                .create();
                        break;
                    case 6:
                        tipDialog = new QMUITipDialog.CustomBuilder(getContext())
                                .setContent(R.layout.tipdialog_custom)
                                .create();
                        break;
                    default:
                        tipDialog = new QMUITipDialog.Builder(getContext())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("正在加载")
                                .create();
                }
                tipDialog.show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tipDialog.dismiss();
                    }
                }, 1000);
            }
        }));
    }


    @Override
    public void success(List<NewsBean> result) {
        mEtUsername.setText("成功");
        showToastMessage("成功");
//        showDialogMessage("成功");
        recyclerList(result);
        refresh.setRefreshing(false);
        refresh.setLoadMore(false);
    }

    @Override
    public void failed() {
        showToastMessage("失败");
    }

    /**
     *
     * @param msg
     */
    /**
     * @Description:
     * @Author:
     * @Time:
     */
    @Override
    public void showVerifyError(String msg) {
        mEtUsername.requestFocus();
        showMessage(msg);
    }

    @Override
    public void showDialog() {
//        showLoadDialog();
    }

    @Override
    public void dismissDialog() {
//        hideLoadDialog();
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public void getRxPermissions() {
        checkPermission(Manifest.permission.CAMERA, "请开启相机权限", new GuaranteeCallBack() {
            @Override
            public void onGuarantee() {
            }
        });
    }

    /**
     * @param view
     */
    public void login(View view) {
        hideKeyboard();
        String userName = mEtUsername.getText().toString();
        String password = mEtUsername.getText().toString();
        mLoginPresenter.login(userName, password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    public void onRefreshUp() {
        mHandler.sendEmptyMessageDelayed(1, 500);
    }

    @Override
    public void onLoadMore() {

    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    swipeRefreshLayout.setRefreshing(true);
//                    initDatas();
                    break;
                case 1:
//                    initDatas();
//                    if (mList.size() > 0) {
//                        mList.clear();
//                        mAdapter.setData(mList);
//                    }
//                    getHttp();
                    mLoginPresenter.login("", "");
                    break;
                case 2:
//					getHttp();
                    break;
            }
            return false;
        }
    });


    // ================================ 生成不同类型的BottomSheet
    private void showSimpleBottomSheetList() {
        List<String> item = new ArrayList<>();
        item.add("Item 1");
        item.add("Item 2");
        item.add("Item 3");
        new QMUIBottomSheet.BottomListSheetBuilder(this, true)
                .addItem(item).setTitle("事故责任方")
//                .addItem("Item 1")
//                .addItem("Item 2")
//                .addItem("Item 3")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        Toast.makeText(TestActivity.this, "Item " + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .show();
    }

    private void showSimpleBottomSheetGrid() {
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WECHAT_MOMENT = 1;
        final int TAG_SHARE_WEIBO = 2;
        final int TAG_SHARE_CHAT = 3;
        final int TAG_SHARE_LOCAL = 4;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this);
        builder.addItem(R.mipmap.ic_launcher, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher, "分享到私信", TAG_SHARE_CHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher, "保存到本地", TAG_SHARE_LOCAL, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView) {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_SHARE_WECHAT_FRIEND:
                                Toast.makeText(TestActivity.this, "分享到微信", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WECHAT_MOMENT:
                                Toast.makeText(TestActivity.this, "分享到朋友圈", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WEIBO:
                                Toast.makeText(TestActivity.this, "分享到微博", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_CHAT:
                                Toast.makeText(TestActivity.this, "分享到私信", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_LOCAL:
                                Toast.makeText(TestActivity.this, "保存到本地", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).build().show();


    }
}