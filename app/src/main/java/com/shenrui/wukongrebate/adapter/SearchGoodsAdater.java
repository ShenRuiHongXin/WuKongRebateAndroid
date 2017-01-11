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
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class SearchGoodsAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_GOODS = 0;
    private static int TYPE_FOOTER = 1;
    Context context;
    List<TenGoodsData> list;
    View.OnClickListener itemOnClickListener;
    public SearchGoodsAdater(final Context context, List<TenGoodsData> list) {
        this.context = context;
        this.list = list;
        itemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = (String) v.getTag();
                //进入商品详情页
                Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                intent.putExtra("cid",cid);
                context.startActivity(intent);
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == TYPE_FOOTER){
            View layout = inflater.inflate(R.layout.layout_footer, null);
            holder = new FooterViewHolder(layout);
        }else{
            View layout = inflater.inflate(R.layout.ten_oclick_new_goods_recy_item,null);
            holder = new GoodsViewHolder(layout);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_FOOTER){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText("上拉加载更多...");
        }else{
            final GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
            TenGoodsData tenGoodsDataTmp = list.get(position);
            Glide.with(context)
                    .load(tenGoodsDataTmp.getPict_url())
                    .into(goodsViewHolder.iv_goods);

            goodsViewHolder.tv_goods_price.setText("价格："+tenGoodsDataTmp.getZk_final_price()+"¥");
            goodsViewHolder.tv_goods_info.setText(tenGoodsDataTmp.getTitle());

            goodsViewHolder.layout_goods.setTag(tenGoodsDataTmp.getNum_iid());
        }
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
    public int getItemViewType(int position) {
        if(position == list.size()){
            return TYPE_FOOTER;
        }else{
            return TYPE_GOODS;
        }
    }

    @Override
    public int getItemCount() {
        return list==null ? 0:list.size()+1;
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_goods;
        TextView tv_goods_price;
        TextView tv_goods_info;
        LinearLayout layout_goods;
        public GoodsViewHolder(View view) {
            super(view);
            iv_goods = (ImageView) view.findViewById(R.id.iv_10_new_goods);
            ViewGroup.LayoutParams layoutParams = iv_goods.getLayoutParams();
            layoutParams.width = (ScreenUtils.getScreenWidth(context)-ScreenUtils.dip2px(context,10)*2)/2;
            layoutParams.height = layoutParams.width;
            iv_goods.setLayoutParams(layoutParams);
            tv_goods_price = (TextView) view.findViewById(R.id.tv_10_new_goods_price);
            tv_goods_info = (TextView) view.findViewById(R.id.tv_10_new_goods_info);
            layout_goods = (LinearLayout) view.findViewById(R.id.layout_goods);
            layout_goods.setOnClickListener(itemOnClickListener);
        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView tvFooter;
        public FooterViewHolder(View itemView) {
            super(itemView);
            tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
        }
    }
}
