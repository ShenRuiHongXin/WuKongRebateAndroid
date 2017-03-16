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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.DuoBaoActivity_;
import com.shenrui.wukongrebate.activity.NineBlockNineActivity_;
import com.shenrui.wukongrebate.activity.SignActivity_;
import com.shenrui.wukongrebate.activity.SuperActivity_;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.RebateMenuData;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.entities.UatmTbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.RebateItemView;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class RebateAdapter extends RecyclerView.Adapter {
    private static final int TYPE_MENU = 0;
    private static final int TYPE_NEW_GOODS = 1;
    Context context;
    private RebateMenuData menuData;
    private List<UatmTbkItem> goodsData;
    private View.OnClickListener listener;
    private RebateMenuHolder menuHolder = null;//用来保存第一个holder的状态

    public RebateAdapter(final Context context, RebateMenuData rebateData,List<UatmTbkItem> goodsList) {
        this.context = context;
        menuData = rebateData;
        goodsData = goodsList;
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
        switch (viewType){
            case TYPE_MENU:
                layout = inflater.inflate(R.layout.layout_rebate_menu_item,null);
                if (menuHolder == null){
                    menuHolder = new RebateMenuHolder(layout);
                    holder = menuHolder;
                }else{
                    holder = menuHolder;
                }
                break;
            case TYPE_NEW_GOODS:
                layout = inflater.inflate(R.layout.layout_rebate_goods_item,null);
                holder = new NewGoodsHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case TYPE_MENU:
                RebateMenuHolder rebateMenuHolder = (RebateMenuHolder) holder;
                rebateMenuHolder.setIsRecyclable(false);
                rebateMenuHolder.cyclerotationview.setUrls(menuData.getCycleList());
                rebateMenuHolder.layoutThreeItem.setCountTime(12,30,30);
                break;
            case TYPE_NEW_GOODS:
                NewGoodsHolder newGoodsHolder = (NewGoodsHolder) holder;
                UatmTbkItem item = goodsData.get(position-1);
                newGoodsHolder.tvTitle.setText(item.getTitle());
                newGoodsHolder.tvPrice.setText(item.getZk_final_price());
                newGoodsHolder.tvVolume.setText(String.valueOf(item.getVolume()));
                newGoodsHolder.tvRate.setText(item.getTk_rate());
                Glide.with(context).load(item.getPict_url()).into(newGoodsHolder.ivNewGoods);
                newGoodsHolder.layoutNewGoods.setTag(String.valueOf(item.getNum_iid()));
                break;
        }
    }

    public void initData(RebateMenuData rebateData,List<UatmTbkItem> goodsList){
        if (rebateData!=null && goodsList!=null){
            menuData = rebateData;
            goodsData.addAll(goodsList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<UatmTbkItem> goodsList){
        if (goodsList!=null){
            goodsData.addAll(goodsList);
            notifyDataSetChanged();
        }
    }

    private boolean isMore;

    public void setMore(boolean isMore){
        this.isMore = isMore;
    }

    public boolean isMore(){
        return this.isMore;
    }

    @Override
    public int getItemCount() {
        return goodsData.size()==0?0:1+goodsData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_MENU;
        }else {
            return TYPE_NEW_GOODS;
        }
    }

    private class RebateMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CycleRotationView cyclerotationview;
        RebateItemView layoutThreeItem;
        TextView tvSuper,tvNine,tvGroup,tvDuo,tvSign;

        RebateMenuHolder(View view) {
            super(view);
            cyclerotationview = (CycleRotationView) view.findViewById(R.id.cyclerotationview);
            layoutThreeItem = (RebateItemView) view.findViewById(R.id.layout_three_item);
            tvSuper = (TextView) view.findViewById(R.id.tv_super);
            tvNine = (TextView) view.findViewById(R.id.tv_nine);
            tvGroup = (TextView) view.findViewById(R.id.tv_group);
            tvDuo = (TextView) view.findViewById(R.id.tv_duo);
            tvSign = (TextView) view.findViewById(R.id.tv_sign);
            tvDuo.setOnClickListener(this);
            tvNine.setOnClickListener(this);
            tvSign.setOnClickListener(this);
            tvSuper.setOnClickListener(this);
            tvGroup.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_super:
                    MFGT.startActivity(context,SuperActivity_.class);
                    break;
                case R.id.tv_nine:
                    MFGT.startActivity(context,NineBlockNineActivity_.class);
                    break;
                case R.id.tv_group:
                    Intent intent = new Intent().setAction(Constants.GOTOZHI);
                    context.sendBroadcast(intent);
                    break;
                case R.id.tv_duo:
                    MFGT.startActivity(context, DuoBaoActivity_.class);
                    break;
                case R.id.tv_sign:
                    MFGT.startActivity(context,SignActivity_.class);
                    break;
            }
        }
    }

    private class NewGoodsHolder extends RecyclerView.ViewHolder{
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
