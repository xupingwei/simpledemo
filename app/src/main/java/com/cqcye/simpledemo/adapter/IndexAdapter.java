package com.cqcye.simpledemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqcye.simpledemo.R;
import com.cqcye.simpledemo.bean.DescBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.List;

/**
 * Created by xupingwei on 2016/12/10.
 */

public class IndexAdapter extends BaseAdapter {

    private Context mContext;
    private List<DescBean> mDescBeans;   //需要显示的具体数据集
    private LayoutInflater mInflater;

    public DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//            .showStubImage(R.mipmap.default_image)          // 设置图片下载期间显示的图片
//            .showImageForEmptyUri(R.mipmap.default_image)  // 设置图片Uri为空或是错误的时候显示的图片
//            .showImageOnFail(R.mipmap.default_image)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                       // 设置下载的图片是否缓存在内存中options
            .cacheOnDisc(true)
            .considerExifParams(true)                      // 设置下载的图片是否缓存在SD卡中
            .displayer(new SimpleBitmapDisplayer()) // 设置成圆角图片
            .build();

    public IndexAdapter(Context mContext, List<DescBean> mDescBeans) {
        this.mContext = mContext;
        this.mDescBeans = mDescBeans;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDescBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mDescBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_index, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            holder.ivThumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        DescBean bean = mDescBeans.get(i);
        holder.tvTitle.setText(bean.getTitle());

        if (null == bean.getDescription() || bean.getDescription().equals("")) {
            holder.tvDesc.setText("This is a lazy guy,these is nothing!");
            holder.tvDesc.setTextColor(Color.parseColor("#cccccc"));
        } else {
            holder.tvDesc.setText(bean.getDescription());
            holder.tvDesc.setTextColor(Color.parseColor("#000000"));
        }

        if (null == bean.getImageHref()) {
            holder.ivThumbnail.setImageBitmap(null);
        } else {
            ImageLoader.getInstance().displayImage(bean.getImageHref(), holder.ivThumbnail);
        }

        return view;
    }


    class ViewHolder {
        TextView tvTitle;
        TextView tvDesc;
        ImageView ivThumbnail;
    }
}
