package com.futures.knowledge.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.futures.knowledge.bean.BannerBean;

import java.util.List;

/**
 * @ClassName: BannerAdapter
 * @Description:
 * @Author: dongxie
 * @CreateDate: 2020/6/30 10:21
 */
public class BannerAdapter extends com.youth.banner.adapter.BannerAdapter<BannerBean,
        BannerAdapter.BannerViewHolder> {


    public BannerAdapter(List<BannerBean> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerBean data, int position, int size) {
        holder.imageView.setImageResource(data.getImageRes());
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
