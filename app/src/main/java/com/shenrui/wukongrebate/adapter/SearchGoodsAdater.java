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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.List;

public class SearchGoodsAdater extends RecyclerView.Adapter<SearchGoodsAdater.NewGoodsHolder> {
    Context context;
    private List<TbkItem> list;
    private View.OnClickListener listener;
    public SearchGoodsAdater(final Context context, List<TbkItem> list) {
        this.context = context;
        this.list = list;
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
    public NewGoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.layout_rebate_goods_item,null);
        return new NewGoodsHolder(layout);
    }

    @Override
    public void onBindViewHolder(SearchGoodsAdater.NewGoodsHolder holder, int position) {
        TbkItem item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText(item.getZk_final_price());
        holder.tvVolume.setText(String.valueOf(item.getVolume()));
        Glide.with(context).load(item.getPict_url()).into(holder.ivNewGoods);
        holder.layoutNewGoods.setTag(String.valueOf(item.getNum_iid()));
    }

    public void initData(List<TbkItem> goodsList){
        if(list.size()!=0){
            list.clear();
        }
        if(goodsList.size()!=0){
            list.addAll(goodsList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<TbkItem> goodsList){
        if(goodsList!=null){
            list.addAll(goodsList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NewGoodsHolder extends RecyclerView.ViewHolder{
        ImageView ivNewGoods;
        TextView tvTitle,tvPrice,tvRate,tvVolume;
        LinearLayout layoutNewGoods;
        NewGoodsHolder(View itemView) {
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
