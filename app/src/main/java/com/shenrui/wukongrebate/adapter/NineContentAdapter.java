package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.AiTaoBaoItem;
import com.shenrui.wukongrebate.entities.RecyItemIndexData;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.ArrayList;
import java.util.List;

import static com.shenrui.wukongrebate.R.id.layout_nine_item;
import static com.shenrui.wukongrebate.R.id.tv_nine_price;

/**
 * Created by Administrator on 2017/1/12.
 */

public class NineContentAdapter extends RecyclerView.Adapter<NineContentAdapter.MyHolder> {
    Context context;
    List<TenGoodsData> list;
    View.OnClickListener listener;
    public NineContentAdapter(final Context context, List<TenGoodsData> goodsList) {
        this.context = context;
        list = new ArrayList<>();
        if(goodsList!=null){
            list.addAll(goodsList);
        }
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

    public void initData(List<TenGoodsData> goodsList){
        if(list!=null){
            list.clear();
        }
        if(goodsList!=null){
            list.addAll(goodsList);
        }
        notifyDataSetChanged();
    }
    public void addData(List<TenGoodsData> goodsList){
        if(goodsList!=null){
            list.addAll(goodsList);
            notifyDataSetChanged();
        }
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_nine, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        TenGoodsData item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText("￥："+item.getZk_final_price());
        holder.ivPicture.setTag(R.id.iv_nine_picture,item.getPict_url());
        if(item.getPict_url().equals(holder.ivPicture.getTag(R.id.iv_nine_picture))){
            Glide.with(context).load(item.getPict_url()).into(holder.ivPicture);
        }
        holder.layoutNineItem.setTag(item.getNum_iid());
        //holder.tvRate.setText("返"+(Integer.getInteger(item.getCommission_rate())/100)+"%");
        //holder.tvVolume.setText(String.valueOf(item.getVolume()));
        //holder.ivShopType.setImageResource(item.getShop_type().equals("B")?R.drawable.common_icon_mao:R.drawable.common_icon_tao);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView ivPicture;
        TextView tvTitle;
        TextView tvPrice;
        TextView tvRate;
        TextView tvVolume;
        ImageView ivShopType;
        LinearLayout layoutNineItem;
        public MyHolder(View itemView) {
            super(itemView);
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_nine_picture);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_nine_title);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_nine_price);
            tvRate = (TextView) itemView.findViewById(R.id.tv_nine_rate);
            tvVolume = (TextView) itemView.findViewById(R.id.tv_nine_volume);
            ivShopType = (ImageView) itemView.findViewById(R.id.iv_nine_shop_type);
            layoutNineItem = (LinearLayout) itemView.findViewById(R.id.layout_nine_item);
            layoutNineItem.setOnClickListener(listener);
        }
    }
}
