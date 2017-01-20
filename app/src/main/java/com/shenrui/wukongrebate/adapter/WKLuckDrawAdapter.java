package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.view.CycleRotationView;

/**
 * Created by Administrator on 2017/1/13.
 */

public class WKLuckDrawAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CYCLE = 0;
    private static final int TYPE_ITEM = 1;
    Context context;
    String[] urls = {"http://p1.so.qhmsg.com/t01514641c357a98c81.jpg", "http://p4.so.qhmsg.com/t01244e62a3f44edf24.jpg", "http://p4.so.qhmsg.com/t01f017b2c06cc1124e.jpg"};

    public WKLuckDrawAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == TYPE_CYCLE){
            View layout = inflater.inflate(R.layout.layout_nine_cyclerotationview, parent, false);
            holder = new CycleHolder(layout);
        }else{
            View layout = inflater.inflate(R.layout.layout_item_wkluckdraw, parent, false);
            holder = new ItemHolder(layout);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_CYCLE:
                CycleHolder cycleHolder = (CycleHolder) holder;
                cycleHolder.cycleRotationView.setUrls(urls);
                break;
            case TYPE_ITEM:
                ItemHolder itemHolder = (ItemHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_CYCLE;
        }else{
            return TYPE_ITEM;
        }
    }
    class CycleHolder extends RecyclerView.ViewHolder{
        CycleRotationView cycleRotationView;
        public CycleHolder(View itemView) {
            super(itemView);
            cycleRotationView = (CycleRotationView) itemView.findViewById(R.id.nine_crv);
        }
    }
    class ItemHolder extends RecyclerView.ViewHolder{
        ImageView ivPrizeImage;
        TextView tvPrizeInfo;
        TextView tvPrizePrice;
        TextView tvStartDraw;
        TextView tvDrewNumber;
        public ItemHolder(View itemView) {
            super(itemView);
            ivPrizeImage = (ImageView) itemView.findViewById(R.id.iv_prize_image);
            tvPrizePrice = (TextView) itemView.findViewById(R.id.tv_prize_price);
            tvPrizeInfo = (TextView) itemView.findViewById(R.id.tv_prize_info);
            tvStartDraw = (TextView) itemView.findViewById(R.id.tv_start_draw);
            tvDrewNumber = (TextView) itemView.findViewById(R.id.tv_drew_number);
        }
    }
}
