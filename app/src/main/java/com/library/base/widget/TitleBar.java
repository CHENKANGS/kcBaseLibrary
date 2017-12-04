package com.library.base.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.base.R;


/**
 * 用于顶部展示的titlebar
 * 可根据项目需要自行更改
 * @author shishuyao
 */
public class TitleBar extends RelativeLayout {
	public TextView tvTitle;
	public TextView tvRight;
	public ImageView rightImg;
	public LinearLayout imgBack;
	public TranslateAnimation mShowAction, mHiddenAction;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.widget_title, this, true);
		setBackgroundColor(0xFFFFFF);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvRight = (TextView) findViewById(R.id.tvRight);
		imgBack = (LinearLayout) findViewById(R.id.imgBack);
		rightImg = (ImageView) findViewById(R.id.imgRight);

		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f);
		mHiddenAction.setDuration(200);
		mShowAction.setDuration(200);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 设置左边点击事件
	 *
	 * @param onLeftClickListener
	 */
	public void setBackBtn(final OnLeftClickLinstener onLeftClickListener) {
		imgBack.setVisibility(View.VISIBLE);
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View argprivate0) {
				onLeftClickListener.onclick();
			}
		});
	}
	
	/**
	 * 适合back按钮点击直接finish页面
	 * @param context
	 */
	public void setBackBtn2FinishPage(final Activity context) {
		imgBack.setVisibility(View.VISIBLE);
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				context.finish();
			}
		});
	
	}
	
	/**
	 * 设置右边文字
	 */
	public void setRightText(String str, final OnRightClickLinstener click) {
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText(str);
		tvRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click.onclick();
			}
		});
	}
	
	
	/**
	 *  设置右边文字
	 * @param str
	 */
	public void setRightText(String str) {
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText(str);
	}

	/**
	 * 若res<=0 或click为null，则右边栏目不展示
	 * 
	 * @param res
	 * @param click
	 */
	public void setRightImgRes(int res, final OnRightClickLinstener click) {
		if (res > 0) {
			rightImg.setImageResource(res);
			rightImg.setVisibility(View.VISIBLE);
		} else {
			rightImg.setVisibility(View.GONE);
		}
		if (click != null) {
			rightImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					click.onclick();
				}
			});

		}
	}

	/**
	 * 设置titlebar是否可见
	 * 
	 * @param isVisible
	 */
	public void setTitleBarVisible(boolean isVisible) {
		if (!isVisible && getVisibility()== View.VISIBLE) {
			startAnimation(mHiddenAction);
			setVisibility(View.GONE);
		} else if(isVisible && getVisibility() == View. GONE) {
			startAnimation(mShowAction);     
			setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置第几个不可见
	 * @param isVisible
	 * @param position
     */
	public void setTitleVisible(boolean isVisible,int position) {
		if (!isVisible && getVisibility()== View.VISIBLE&&position==1) {
			startAnimation(mHiddenAction);
			setVisibility(View.GONE);
		} else if(isVisible && getVisibility() == View. GONE) {
			startAnimation(mShowAction);
			setVisibility(View.VISIBLE);
		}
	}



	public interface OnLeftClickLinstener {

		void onclick();
	}

	public interface OnRightClickLinstener {
		void onclick();
	}

}
