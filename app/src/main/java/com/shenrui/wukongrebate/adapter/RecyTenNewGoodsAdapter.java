package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.ScreenUtils;

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        TenGoodsData tenGoodsDataTmp = (TenGoodsData)listData.get(position);
        Glide.with(context)
                .load(tenGoodsDataTmp.getPict_url())
                .into(myViewHolder.iv_goods);

        myViewHolder.tv_goods_price.setText("价格："+tenGoodsDataTmp.getZk_final_price()+"¥");
        myViewHolder.tv_goods_info.setText(tenGoodsDataTmp.getTitle());

        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = myViewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_goods;
        TextView tv_goods_price;
        TextView tv_goods_info;

        public MyViewHolder(View view)
        {
            super(view);


            iv_goods = (ImageView) view.findViewById(R.id.iv_10_new_goods);
            ViewGroup.LayoutParams layoutParams = iv_goods.getLayoutParams();
            layoutParams.width = (ScreenUtils.getScreenWidth(context)-ScreenUtils.dip2px(context,10)*2)/2;
//            layoutParams.height = (int) (layoutParams.width/1.75);
            layoutParams.height = layoutParams.width;
            iv_goods.setLayoutParams(layoutParams);
            tv_goods_price = (TextView) view.findViewById(R.id.tv_10_new_goods_price);
            tv_goods_info = (TextView) view.findViewById(R.id.tv_10_new_goods_info);
        }
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
//        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
