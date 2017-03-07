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
import com.shenrui.wukongrebate.entities.UatmTbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.List;

/**
 * 品牌页的适配器
 */

public class BrandAdapter extends RecyclerView.Adapter {
    List<UatmTbkItem> list;
    Context context;
    private View.OnClickListener listener;

    public BrandAdapter(List<UatmTbkItem> list, final Context context) {
        this.list = list;
        this.context = context;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = (String) v.getTag();
                //进入商品详情页
                Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                intent.putExtra("num_iid",cid);
                MFGT.startActivity(context,intent);
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.layout_rebate_goods_item,null);
        return new GoodsHolder(layout);
    }

    public void initData(List<UatmTbkItem> goodsList){
        if(list.size()!=0){
            list.clear();
        }
        if(goodsList.size()!=0){
            list.addAll(goodsList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<UatmTbkItem> goodsList){
        if(goodsList!=null){
            list.addAll(goodsList);
            notifyDataSetChanged();
        }
    }
    private boolean isMore = true;
    public void setMore(boolean isMore){
        this.isMore = isMore;
    }
    public boolean isMore(){
        return this.isMore;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsHolder goodsHolder = (GoodsHolder) holder;
        UatmTbkItem item = list.get(position);
        goodsHolder.tvTitle.setText(item.getTitle());
        goodsHolder.tvPrice.setText(item.getZk_final_price());
        goodsHolder.tvRate.setText(item.getTk_rate());
        goodsHolder.tvVolume.setText(String.valueOf(item.getVolume()));
        Glide.with(context).load(item.getPict_url()).into(goodsHolder.ivNewGoods);
        goodsHolder.layoutNewGoods.setTag(String.valueOf(item.getNum_iid()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GoodsHolder extends RecyclerView.ViewHolder{
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
