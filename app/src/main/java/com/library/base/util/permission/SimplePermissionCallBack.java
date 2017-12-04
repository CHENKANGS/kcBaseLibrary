package com.library.base.util.permission;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by app on 2017/9/29.
 */

public abstract class SimplePermissionCallBack implements PermissionCallback {
    private final String mTips;
    private final Context mContext;

    public SimplePermissionCallBack(Context context, String tips) {
        mTips = tips;
        mContext = context;
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onDeny(String permission, int position) {
        if (! TextUtils.isEmpty(mTips))
            Toast.makeText(mContext, mTips, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {

    }


}