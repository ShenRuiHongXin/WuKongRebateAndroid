package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.ZhiJxItem;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.view.CycleRotationView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ZhiAdapter extends RecyclerView.Adapter {
    public static final int TYPE_Banner = 0;
    public static final int TYPE_JX_GOODS = 1;
    public static final int TYPE_BK_GOODS = 2;
    Context context;
    private int[] imgs;
    private List<ZhiJxItem> list = new ArrayList<>();
    private View.OnClickListener listener;
    private int itemType = -1;

    private boolean hasCrv = false;
    public void setHasCrv(boolean hasCrv){
        this.hasCrv = hasCrv;
    }

    public ZhiAdapter(final Context context, List<ZhiJxItem> goodsList) {
        this.context = context;
        this.list = goodsList;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String cid = (String) v.getTag();
//                Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
//                intent.putExtra("num_iid",cid);
//                MFGT.startActivity(context,intent);
            }
        };
    }

    public ZhiAdapter(final Context context, List<ZhiJxItem> goodsList,int[] imgs) {
        this(context,goodsList);
        this.imgs = imgs;
    }
    public ZhiAdapter(final Context context, List<ZhiJxItem> goodsList,int itemType) {
        this(context,goodsList);
        this.itemType = itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_Banner:
                layout = inflater.inflate(R.layout.cycle_rotation_view,null);
                holder = new BannerHolder(layout);
                break;
            case TYPE_JX_GOODS:
            case TYPE_BK_GOODS:
                if(viewType == TYPE_JX_GOODS) {
                    layout = inflater.inflate(R.layout.zhi_jx_item,null);
                }else{
                    layout = inflater.inflate(R.layout.zhi_bk_item,null);
                }
                holder = new GoodsHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case TYPE_Banner:
                BannerHolder bannerHolder = (BannerHolder) holder;
                bannerHolder.setIsRecyclable(false);
                bannerHolder.crv.setImages(imgs);
                break;
            case TYPE_BK_GOODS:
            case TYPE_JX_GOODS:
                GoodsHolder goodsHolder = (GoodsHolder) holder;
                ZhiJxItem item = list.get(hasCrv ? (position-1) : position);
                goodsHolder.ivGoods.setImageResource(item.getImg());
                goodsHolder.tvGoodsTitle.setText(item.getTitle());
                String prefix = viewType == TYPE_JX_GOODS ? "\u3000\"" :"";
                if (viewType == TYPE_JX_GOODS){
                    goodsHolder.ivIsOverseas.setVisibility(item.isOverseas() ? View.VISIBLE : View.INVISIBLE);
                }
                goodsHolder.tvGoodsInfo.setText(prefix+item.getInfo());
                break;
        }
    }
    @Override
    public int getItemCount() {
        if(hasCrv){
            return list.size()+1;
        }
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(hasCrv) {
            if (position == 0) {
                return TYPE_Banner;
            } else {
                return TYPE_JX_GOODS;
            }
        }
        if(itemType == TYPE_BK_GOODS){
            return TYPE_BK_GOODS;
        }
        return TYPE_JX_GOODS;
    }

    public void initData(List<ZhiJxItem> zhiJxItems){
        LogUtil.d("before|list size:" + list.size()+"zhiJxItems size:" + zhiJxItems.size());
            list.clear();
            list.addAll(zhiJxItems);
        LogUtil.d("after|list size:" + list.size()+"zhiJxItems size:" + zhiJxItems.size());
            notifyDataSetChanged();
    }

    public void addData(List<ZhiJxItem> zhiJxItems){
        if(zhiJxItems!=null){
            list.addAll(zhiJxItems);
            notifyDataSetChanged();
        }
    }

    private class BannerHolder extends RecyclerView.ViewHolder{
        CycleRotationView crv;
        public BannerHolder(View itemView) {
            super(itemView);
            crv = (CycleRotationView) itemView.findViewById(R.id.comment_crv);
        }
    }

    private class GoodsHolder extends RecyclerView.ViewHolder{
        ImageView ivGoods;
        TextView tvGoodsTitle,tvGoodsInfo;
        ViewGroup layoutGoods;
        ImageView ivIsOverseas;
        GoodsHolder(View itemView) {
            super(itemView);
            ivGoods = (ImageView) itemView.findViewById(R.id.zhi_jx_item_good_img);
            tvGoodsTitle = (TextView) itemView.findViewById(R.id.zhi_jx_item_good_title);
            tvGoodsInfo = (TextView) itemView.findViewById(R.id.zhi_jx_item_good_info);
            layoutGoods = (ViewGroup) itemView.findViewById(R.id.zhi_jx_item);
            layoutGoods.setOnClickListener(listener);
            if(itemView instanceof RelativeLayout){
                ivIsOverseas = (ImageView) itemView.findViewById(R.id.zhi_jx_item_good_overseas);
            }
        }
    }
}
