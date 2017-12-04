package com.library.base.widget.image;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.library.base.R;
import com.library.base.entity.bean.PicBean;
import com.library.base.global.GlobalConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看器
 */
public class ImagePagerActivity extends FragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";

	public static final String TYPE_IMAGE_BD = "TYPE_IMAGE_BD";//加载本地
	public static final String TYPE_IMAGE_XC = "TYPE_IMAGE_XC";//相册图片
	public static final String TYPE_IMAGE_FXPG = "TYPE_IMAGE_FXPG";//风险评估
	public static final String TYPE_IMAGE_NET = "TYPE_IMAGE_NET";//加载网络

    private ViewPager mPager;
	private int pagerPosition;
	private TextView indicator;
	List<PicBean> image_List = new ArrayList<PicBean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_pager);

		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		if (null!=getIntent().getSerializableExtra(EXTRA_IMAGE_URLS)){
			image_List = (List<PicBean>) getIntent().getSerializableExtra(EXTRA_IMAGE_URLS);
		}

		mPager = (ViewPager) findViewById(R.id.pager);
		indicator = (TextView) findViewById(R.id.indicator);

		mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
		mPager.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return image_List.size();
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				PhotoView view = new PhotoView(ImagePagerActivity.this);
				view.enable();
				view.setScaleType(ImageView.ScaleType.FIT_CENTER);
//				view.setImageResource(imgsId[position]);
				if (TYPE_IMAGE_BD.equals(image_List.get(position).getType())){//加载本地
					int resourceId = R.mipmap.wzcx_image;
					Glide.with(ImagePagerActivity.this).load(resourceId).into(view);
				}else if (TYPE_IMAGE_XC.equals(image_List.get(position).getType())){//相册图片
					showImage(view, "file://" + image_List.get(position).getPath());
				}else if (TYPE_IMAGE_FXPG.equals(image_List.get(position).getType())){//风险评估
//					showImage(view, Constant.BASE_URL + "/" + image_List.get(position).getPath());
				}else {//加载网络
					showImage(view, GlobalConstant.API_HOST + "/" + image_List.get(position).getPath());

				}
				container.addView(view);
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}
		});

		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}

		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}


	private void showImage(PhotoView view , String path){
		Glide.with(ImagePagerActivity.this)
				.load(path)
				.placeholder(R.mipmap.gear_image_default)
				.error(R.mipmap.gear_image_default)
				.priority(Priority.HIGH)//优先级
//							.thumbnail(0.1f)
//                .crossFade()//渐显动画
//                .bitmapTransform(new RoundedCornersTransformation(context, 16, 0))//设置圆角
				.diskCacheStrategy(DiskCacheStrategy.ALL)  //让Glide既缓存全尺寸又缓存其他尺寸
				.into(view);
	}

}
