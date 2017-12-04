package com.library.base.view;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.library.base.R;
import com.library.base.adapter.ZX_HomeItem;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.adapter.base_adapter.CommonRcvAdapter;
import com.library.base.adapter.base_adapter.rcv.RcvOnItemClickListener;
import com.library.base.base.BaseKcActivity;
import com.library.base.cache.AppCache;
import com.library.base.entity.basemodel.ResponseModel;
import com.library.base.entity.bean.NewsBean;
import com.library.base.global.GlobalVariable;
import com.library.base.module.ui.TestActivity;
import com.library.base.net.HttpRequest;
import com.library.base.net.ResponseCallback;
import com.library.base.util.QMUIStatusBarHelper;
import com.library.base.util.permission.GuaranteeCallBack;
import com.library.base.util.permission.HiPermission;
import com.library.base.util.permission.PermissionCallback;
import com.library.base.util.permission.PermissionItem;
import com.library.base.widget.FullyLinearLayoutManager;
import com.library.base.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class BaseMainActivity extends BaseKcActivity {
    private final static String ARG_CHANGE_TRANSLUCENT = "ARG_CHANGE_TRANSLUCENT";
    private TitleBar titleBar;

    private RecyclerView recyclerView;
    private CommonRcvAdapter<NewsBean> adapter;
    private List<NewsBean> ks_injured_damages;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        Intent intent = getIntent();
//        if (intent != null) {
//            boolean isTranslucent = intent.getBooleanExtra(ARG_CHANGE_TRANSLUCENT, true);
//            if (isTranslucent) {
//                QMUIStatusBarHelper.translucent(this); // 沉浸式状态栏
//            }
//        }

        setContentView( R.layout.activity_base_main );

        setPermisson();
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.setTitle("详情");
        titleBar.setBackBtn2FinishPage(this);

        recyclerView = findViewById(R.id.recyclerView);
        httpGetZX(GlobalVariable.appkey , GlobalVariable .appsecret) ;
        recyclerList();
//        setSwipeRefreshLayout();
    }
    //资讯List
    private void recyclerList() {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        manager.setSmoothScrollbarEnabled(true);
        adapter = new CommonRcvAdapter<NewsBean>(ks_injured_damages = ks_injured_damages == null ? new ArrayList<NewsBean>() : ks_injured_damages) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new ZX_HomeItem(BaseMainActivity.this);
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RcvOnItemClickListener(this, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(BaseMainActivity.this , "dianlewo " , Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putString("id_detail", ks_injured_damages.get(position).getId());
//                bundle.putString("url", ks_injured_damages.get(position).getUrl());
//                bundle.putString("pic",ks_injured_damages.get(position).getPic());
//                bundle.putString("title",ks_injured_damages.get(position).getTitle());
//                bundle.putString("excerpt",ks_injured_damages.get(position).getExcerpt());
//                ((BaseActivity) getActivity()).jumpActivity(bundle, ZX_HomeDetailActivity.class);
//                checkPermission( Manifest.permission.CAMERA, "请开启相机权限", new GuaranteeCallBack() {
//                    @Override
//                    public void onGuarantee() {
                        Intent intent = new Intent(BaseMainActivity.this , TestActivity.class);
                        startActivity(intent);
//                    }
//                });
            }
        }));
    }
    /**
     * 获取首页资讯列表
     *
     * @param appkey
     * @param appsecret
     */
    private void httpGetZX(String appkey, String appsecret) {
        HttpRequest.getInstance(this).getHomeZX(appkey, appsecret, new ResponseCallback<List<NewsBean>>(this) {
            @Override
            public void onSuccess(final List<NewsBean> resultData) {
                ks_injured_damages.clear();
//                human_address_tv.setText(resultData.getQuickcase_injured_id()+"");
                if (resultData.size() > 0) {
                    if (resultData.size() > 10) {
                        for (int i = 0; i < 10; i++) {
                            ks_injured_damages.add(resultData.get(i));
                        }
                    } else {
                        for (int i = 0; i < resultData.size(); i++) {
                            ks_injured_damages.add(resultData.get(i));
                        }
                    }
                }
                Toast.makeText(BaseMainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                adapter.setData(ks_injured_damages);
                AppCache.getInstance().setObjectDevice("list" , ks_injured_damages);
            }

            @Override
            public void onFailure(Call<ResponseModel<List<NewsBean>>> call, Throwable t) {
                super.onFailure(call, t);
						Toast.makeText(BaseMainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPermisson(){
        //申请权限
        List<PermissionItem> items=new ArrayList<>();
        items.add(new PermissionItem(android.Manifest.permission.ACCESS_FINE_LOCATION,"定位"));
        items.add(new PermissionItem(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,"存储"));
        items.add(new PermissionItem(android.Manifest.permission.READ_PHONE_STATE,"设备信息"));
        HiPermission.create(this).permissions(items)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        finish();
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(BaseMainActivity.this , "wancheng" , Toast.LENGTH_SHORT).show();
                        Log.e("==PermissionItem==" , "11");
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                },false);

    }
}
