package com.library.base.adapter.base_adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.library.base.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T> extends BaseAdapter implements IAdapter<T> {

    private List<T> mDataList;

    private int mViewTypeCount = 1;

    /**
     * 每个item的类型，会根据每一条数据进行产生
     */
    private Object mType;

    private LayoutInflater mInflater;

    private ItemTypeUtil util;


    protected CommonAdapter(@Nullable List<T> data, int viewTypeCount) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mDataList = data;
        mViewTypeCount = viewTypeCount;
        util = new ItemTypeUtil();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 通过数据得到obj的类型的type
     * 然后，通过{@link ItemTypeUtil}来转换位int类型的type
     *
     * instead by{@link #getItemType(Object)}
     */
    @Override
    @Deprecated
    public int getItemViewType(int position) {
        mType = getItemType(mDataList.get(position));
        // 如果不写这个方法，会让listView更换dataList后无法刷新数据
        return util.getIntType(mType);
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        final AdapterItem item;
        if (convertView == null) {
            item = createItem(mType);
            convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
            convertView.setTag(R.id.tag_item, item); // get item

            item.bindViews(convertView);
            item.setViews();
        } else {
            item = (AdapterItem) convertView.getTag(R.id.tag_item); // save item
        }
        item.handleData(getConvertedData(mDataList.get(position), mType), position);
        return convertView;
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

}
