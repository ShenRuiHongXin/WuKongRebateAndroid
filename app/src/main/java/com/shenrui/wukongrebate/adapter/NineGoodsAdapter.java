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
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */

//九块九商品适配器
public class NineGoodsAdapter extends RecyclerView.Adapter{
    Context context;
    List<TbkItem> list;
    View.OnClickListener listener;
    public NineGoodsAdapter(final Context context, List<TbkItem> list) {
        this.context = context;
        this.list = list;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = (String) v.getTag();
                //进入商品详情页
                Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                intent.putExtra("num_iid",cid);
                context.startActivity(intent);
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_nine_goods_item, null);
        return new NineGoodsHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TbkItem tbkItem = list.get(position);
        NineGoodsHolder goodsHolder = (NineGoodsHolder) holder;
        Glide.with(context).load(tbkItem.getPict_url()).into(goodsHolder.ivGoodsPic);
        goodsHolder.tvGoodsTitle.setText(tbkItem.getTitle());
        goodsHolder.tvGoodsPrice.setText(tbkItem.getZk_final_price());
        goodsHolder.tvGoodsVolume.setText(String.valueOf(tbkItem.getVolume()));
        goodsHolder.ivGoodsType.setImageResource(tbkItem.getUser_type() == 0 ? R.drawable.common_icon_tao : R.drawable.common_icon_mao);
        goodsHolder.layoutNineGoods.setTag(String.valueOf(tbkItem.getNum_iid()));
    }

    public void initData(List<TbkItem> goodsList){
        if (goodsList!=null){
            list.clear();
            list.addAll(goodsList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<TbkItem> goodsList){
        if (goodsList!=null){
            list.addAll(goodsList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class NineGoodsHolder extends RecyclerView.ViewHolder{
        ImageView ivGoodsPic,ivGoodsType;
        TextView tvGoodsTitle,tvGoodsVolume,tvGoodsPrice;
        LinearLayout layoutNineGoods;
        NineGoodsHolder(View itemView) {
            super(itemView);
            ivGoodsPic = (ImageView) itemView.findViewById(R.id.iv_nine_goods);
            ivGoodsType = (ImageView) itemView.findViewById(R.id.iv_nine_goods_type);
            tvGoodsTitle = (TextView) itemView.findViewById(R.id.tv_nine_goods_title);
            tvGoodsVolume = (TextView) itemView.findViewById(R.id.tv_nine_goods_volume);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_nine_goods_price);
            layoutNineGoods = (LinearLayout) itemView.findViewById(R.id.layout_nine_goods);
            layoutNineGoods.setOnClickListener(listener);
        }
    }
}
