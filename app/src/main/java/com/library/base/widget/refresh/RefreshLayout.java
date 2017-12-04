package com.library.base.widget.refresh;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.library.base.R;
import com.library.base.base.BaseKcActivity;
import com.library.base.widget.refresh.SuperSwipeRefreshLayout;

import pl.droidsonroids.gif.GifImageView;

/*

 * Description: 

 * File: Refresh.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/11/24 10:07

 *

 * Changes (from 2017/11/24)

 * -----------------------------------------------------------------

 * 2017/11/24 : Changes Refresh.java (k);

 * -----------------------------------------------------------------

 */
public class RefreshLayout extends SuperSwipeRefreshLayout {

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private GifImageView gifView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    OnRefreshListener mListener;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

//        setHeaderViewBackgroundColor(0xff888888);
        setHeaderView(createHeaderView(context));// add headerView
        setFooterView(createFooterView(context));
        setTargetScrollWithLayout(true);
        setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        //TODO 开始刷新
                        gifView.setBackgroundResource(R.drawable.ic_refresh_doing);
                        if (mListener != null) {
                            mListener.onRefreshUp();
                        }

//                        textView.setText("正在刷新");
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
//                        progressBar.setVisibility(View.GONE);
//                        new Handler().postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                swipeRefreshLayout.setRefreshing(false);
//                                progressBar.setVisibility(View.GONE);
//                            }
//                        }, 2000);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        //TODO 下拉距离
                        // pull distance
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        //TODO 下拉过程中，下拉的距离是否足够出发刷新
                        if (enable){
                            //释放刷新
                            gifView.setBackgroundResource(R.drawable.ic_refresh_release);
                        }else {
                            gifView.setBackgroundResource(R.drawable.ic_refresh_down);
                        }
//                        textView.setText(enable ? "松开刷新ss" : "下拉刷新zz");
//                        imageView.setVisibility(View.VISIBLE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
//                        imageView.setRotation(enable ? 180 : 0);
                    }
                });
        setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                footerImageView.setVisibility(View.VISIBLE);
                                footerProgressBar.setVisibility(View.GONE);
                                setLoadMore(false);
                            }
                        }, 5000);
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        //TODO 上拉过程中，上拉的距离是否足够出发刷新
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {
                        // TODO Auto-generated method stub
                        // TODO 上拉距离

                    }

                });
    }


    /**
     * 刷新底部布局
     * @return
     */
    public View createFooterView(Context context) {
        View footerView = LayoutInflater.from(context)
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.GONE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    /**
     * 刷新头部布局
     * @return
     */
    public View createHeaderView(Context context) {
        View headerView = LayoutInflater.from(context)
                .inflate(R.layout.layout_head, null);
        gifView = (GifImageView) headerView.findViewById(R.id.gifView);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        textView.setVisibility(View.GONE);
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.GONE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }


    /**
     * 监听事件
     */
    public interface OnRefreshListener {
        /**
         * 下拉刷新监听
         */
        void onRefreshUp() ;
        /**
         * 上拉加载监听
         */
        void onLoadMore();
    }

    /**
     * Set the listener to be notified when a refresh is triggered via the swipe
     * gesture.
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }
}
