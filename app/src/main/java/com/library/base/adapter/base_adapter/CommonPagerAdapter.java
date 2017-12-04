package com.library.base.adapter.base_adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.library.base.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jack Tony
 * @date 2015/11/29
 */
public abstract class CommonPagerAdapter<T> extends BasePagerAdapter<View> implements IAdapter<T> {

    private List<T> mDataList;

    private LayoutInflater mInflater;

    private boolean mIsLazy = false;

    public CommonPagerAdapter(@Nullable List<T> data) {
        this(data, false);
    }

    public CommonPagerAdapter(@Nullable List<T> data, boolean isLazy) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mDataList = data;
        mIsLazy = isLazy;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    protected View getViewFromItem(View item, int pos) {
        return item;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = super.instantiateItem(container, position);
        if (!mIsLazy) {
            initItem(position, view);
        }
        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, @NonNull Object object) {
        if (mIsLazy && object != currentItem) {
            initItem(position, ((View) object));
        }
        super.setPrimaryItem(container, position, object);
    }

    private void initItem(int position, View view) {
        final AdapterItem item = (AdapterItem) view.getTag(R.id.tag_item);
        item.handleData(getConvertedData(mDataList.get(position), getItemType(position)), position);
    }

    @Override
    protected View createItem(ViewGroup viewPager, int position) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewPager.getContext());
        }
        AdapterItem item = createItem(getItemType(position));
        View view = mInflater.inflate(item.getLayoutResId(), null);
        view.setTag(R.id.tag_item, item);
        item.bindViews(view);
        item.setViews();
        return view;
    }

    public void setIsLazy(boolean isLazy) {
        mIsLazy = isLazy;
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    /**
     * 强烈建议返回string,int,bool类似的基础对象做type
     */
    public Object getItemType(int position) {
        if (position < mDataList.size()) {
            return getItemType(mDataList.get(position));
        } else {
            return null;
        }
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }
}
