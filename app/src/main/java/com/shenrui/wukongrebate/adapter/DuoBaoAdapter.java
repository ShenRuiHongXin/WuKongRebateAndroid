package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

/**
 * 夺宝商品的适配器
 */

public class DuoBaoAdapter extends RecyclerView.Adapter{
    Context context;

    public DuoBaoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.duobao_good_item, null);
        return new BaoHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaoHolder baoHolder = (BaoHolder) holder;
        baoHolder.pb.setMax(189);
        baoHolder.pb.setProgress(95);
        baoHolder.tvShowRate.setText(95*100/189+"%");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class BaoHolder extends RecyclerView.ViewHolder{
        ImageView ivBao,ivJoin;
        ProgressBar pb;
        TextView tvBao,tvSum,tvShowRate;
        LinearLayout layoutBao;
        BaoHolder(View itemView) {
            super(itemView);
            ivBao = (ImageView) itemView.findViewById(R.id.iv_bao);
            ivJoin = (ImageView) itemView.findViewById(R.id.iv_bao_join);
            pb = (ProgressBar) itemView.findViewById(R.id.pb_bao);
            tvBao = (TextView) itemView.findViewById(R.id.tv_bao_title);
            tvSum = (TextView) itemView.findViewById(R.id.tv_people_sum);
            tvShowRate = (TextView) itemView.findViewById(R.id.tv_show_rate);
            layoutBao = (LinearLayout) itemView.findViewById(R.id.layout_bao);
        }
    }
}
