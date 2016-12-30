package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import java.util.List;

/**
 * Created by heikki on 2016/12/30.
 */

public class RecyTenNewGoodsAdapter extends RecyclerView.Adapter {
    Context context;
    List listData;

    public RecyTenNewGoodsAdapter(Context context, List listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.ten_oclick_new_goods_recy_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_goods;
        TextView tv_goods_name;
        TextView tv_goods_info;

        public MyViewHolder(View view)
        {
            super(view);
            iv_goods = (ImageView) view.findViewById(R.id.iv_10_new_goods);
            tv_goods_name = (TextView) view.findViewById(R.id.tv_10_new_goods_name);
            tv_goods_info = (TextView) view.findViewById(R.id.tv_10_new_goods_info);
        }
    }
}
