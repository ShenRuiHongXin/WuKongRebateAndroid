package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.List;

/**
 * Created by heikki on 2017/3/17.
 */

public class BaseGoodsAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TbkItem> goodsData;
    private View.OnClickListener listener;

    public BaseGoodsAdapter(final Context context, List<TbkItem> goodsData) {
        this.context = context;
        this.goodsData = goodsData;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = (String) v.getTag();
                Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                intent.putExtra("num_iid",cid);
                MFGT.startActivity(context,intent);
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout;
        RecyclerView.ViewHolder holder = null;

        layout = inflater.inflate(R.layout.layout_rebate_goods_item,null);
        holder = new GoodsHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsHolder newGoodsHolder = (GoodsHolder) holder;
        TbkItem item = goodsData.get(position);
        newGoodsHolder.tvTitle.setText(item.getTitle());
        newGoodsHolder.tvPrice.setText(item.getZk_final_price());
        newGoodsHolder.tvVolume.setText(String.valueOf(item.getVolume()));
        newGoodsHolder.tvRate.setText("返利");
        Glide.with(context).load(item.getPict_url()).into(newGoodsHolder.ivNewGoods);
        newGoodsHolder.layoutNewGoods.setTag(String.valueOf(item.getNum_iid()));
    }

    @Override
    public int getItemCount() {
        return goodsData.size();
    }


    private class GoodsHolder extends RecyclerView.ViewHolder{
        ImageView ivNewGoods;
        TextView tvTitle,tvPrice,tvRate,tvVolume;
        LinearLayout layoutNewGoods;
        GoodsHolder(View itemView) {
            super(itemView);
            ivNewGoods = (ImageView) itemView.findViewById(R.id.iv_new_goods);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_new_goods_title);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_new_goods_price);
            tvRate = (TextView) itemView.findViewById(R.id.tv_new_goods_rate);
            tvVolume = (TextView) itemView.findViewById(R.id.tv_new_goods_volume);
            layoutNewGoods = (LinearLayout) itemView.findViewById(R.id.layout_new_goods);
            layoutNewGoods.setOnClickListener(listener);
        }
    }
}
