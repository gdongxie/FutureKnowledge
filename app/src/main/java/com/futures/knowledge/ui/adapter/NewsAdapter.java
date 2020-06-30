package com.futures.knowledge.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.futures.knowledge.R;
import com.futures.knowledge.bean.DataBean;
import com.futures.knowledge.ui.activitys.InfoDetailsActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @ClassName: NewsAdapter
 * @Description:
 * @Author: dongxie
 * @CreateDate: 2020/6/27 15:10
 */
public class NewsAdapter extends CommonAdapter<DataBean> {
    public NewsAdapter(Context context, int layoutId, List<DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final DataBean dataBean, int position) {
        holder.setText(R.id.tv_title, dataBean.getTitle());
        holder.setText(R.id.tv_desc, dataBean.getDesc());
        holder.setText(R.id.tv_time, dataBean.getTime());
        if (dataBean.getIcon()!=null){
            holder.getView(R.id.iv_icon).setVisibility(View.VISIBLE);
            Glide.with(mContext).load(dataBean.getIcon()).into((ImageView) holder.getView(R.id.iv_icon));
        }else {
            holder.getView(R.id.iv_icon).setVisibility(View.GONE);
        }
        holder.setOnClickListener(R.id.layout_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoDetailsActivity.class);
                intent.putExtra("id", dataBean.getId());
                mContext.startActivity(intent);
            }
        });
    }
}
