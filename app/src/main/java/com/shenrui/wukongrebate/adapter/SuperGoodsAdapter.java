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
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class SuperGoodsAdapter extends RecyclerView.Adapter {
    Context context;
    List<TbkItem> list;
    int sortBy;//排序规则
    View.OnClickListener listener;
    DecimalFormat df;
    public SuperGoodsAdapter(final Context context, List<TbkItem> list) {
        this.context = context;
        this.list = list;
        df = new DecimalFormat("#####0.00");
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

    public void setSortBy(int sortBy){
        this.sortBy = sortBy;
        sortBy();
        notifyDataSetChanged();
    }

    private void sortBy() {
        Collections.sort(list, new Comparator<TbkItem>() {
            @Override
            public int compare(TbkItem o1, TbkItem o2) {
                int result = 0;
                switch (sortBy){
                    case Constants.SORT_PRICE_ASC://价格升序排列
                        result = (int) (Double.parseDouble(o1.getZk_final_price())-Double.parseDouble(o2.getZk_final_price()));
                        break;
                    case Constants.SORT_PRICE_DESC:
                        result = (int) (Double.parseDouble(o2.getZk_final_price())-Double.parseDouble(o1.getZk_final_price()));
                        break;
                    case Constants.SORT_VOLUME_ASC://销量升序排列
                        result = (int) (o1.getVolume()-o2.getVolume());
                        break;
                    case Constants.SORT_VOLUME_DESC:
                        result = (int) (o2.getVolume()-o1.getVolume());
                        break;
                }
                return result;
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_super_goods_item, null);
        return new SuperGoodsHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TbkItem tbkItem = list.get(position);
        SuperGoodsHolder goodsHolder = (SuperGoodsHolder) holder;
        Glide.with(context).load(tbkItem.getPict_url()).into(goodsHolder.ivGoodsPic);
        goodsHolder.tvGoodsTitle.setText(tbkItem.getTitle());
        goodsHolder.tvGoodsPrice.setText(tbkItem.getZk_final_price());
        goodsHolder.tvGoodsFan.setText(df.format(Double.parseDouble(tbkItem.getZk_final_price()) * Constants.RATE_FAN));
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

    private class SuperGoodsHolder extends RecyclerView.ViewHolder{
        ImageView ivGoodsPic,ivGoodsType;
        TextView tvGoodsTitle,tvGoodsFan,tvGoodsPrice;
        LinearLayout layoutNineGoods;
        SuperGoodsHolder(View itemView) {
            super(itemView);
            ivGoodsPic = (ImageView) itemView.findViewById(R.id.iv_super_goods);
            ivGoodsType = (ImageView) itemView.findViewById(R.id.iv_super_goods_type);
            tvGoodsTitle = (TextView) itemView.findViewById(R.id.tv_super_goods_title);
            tvGoodsFan = (TextView) itemView.findViewById(R.id.tv_super_goods_fan);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_super_goods_price);
            layoutNineGoods = (LinearLayout) itemView.findViewById(R.id.layout_super_goods);
            layoutNineGoods.setOnClickListener(listener);
        }
    }
}
