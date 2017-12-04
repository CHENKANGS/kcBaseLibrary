package com.library.base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.library.base.R;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.entity.bean.NewsBean;
import com.library.base.global.GlobalConstant;

/**
 * @Class
 * @Author 邮箱
 * @Description (实现的主要功能)
 * @Date Administratoron 2017/4/1 08:56
 * @Upate 修改者:;修改日期:;修改内容:.
 */
public class ZX_HomeItem implements AdapterItem<NewsBean> {

    private Context context;
    private ImageView iv_url;
    private TextView zx_tv_title,zx_tv_excerpt,zx_tv_hot;

    public ZX_HomeItem(Context context ) {
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_zx_list_new;
    }

    @Override
    public void bindViews(View view) {
        zx_tv_title = (TextView) view.findViewById(R.id.zx_tv_title);
        zx_tv_excerpt = (TextView) view.findViewById(R.id.zx_tv_excerpt);
        zx_tv_hot = (TextView) view.findViewById(R.id.zx_tv_hot);
        iv_url = (ImageView) view.findViewById(R.id.iv_url);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(final NewsBean zx_homeItemModel, int position) {
        zx_tv_title.setText(zx_homeItemModel.getTitle());
//        zx_tv_excerpt.setText(zx_insideItemModel.getExcerpt());
        zx_tv_hot.setText(zx_homeItemModel.getCreated_at());
        Glide.with(context)
                .load(GlobalConstant.API_HOST + "/" +zx_homeItemModel.getPic())
                .placeholder(R.mipmap.zx_icon)
                .error(R.mipmap.zx_icon)
//                .bitmapTransform(new RoundedCornersTransformation(context, 16, 0))//设置圆角
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)  //让Glide既缓存全尺寸又缓存其他尺寸
                .into(iv_url);
    }

}
