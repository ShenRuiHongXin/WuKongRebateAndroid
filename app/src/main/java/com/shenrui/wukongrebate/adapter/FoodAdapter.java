package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.ShopDetailActivity_;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.entities.FoodFragmentBtnItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.utils.Utils;
import com.shenrui.wukongrebate.view.CycleRotationView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/3/20.
 */

public class FoodAdapter extends RecyclerView.Adapter {
    public static final int TYPE_HEADER     = 0;
    public static final int TYPE_COOKBOOK   = 1;
    public static final int TYPE_VIDEO      = 2;
    public static final int TYPE_SHOP       = 3;
    public static final int TYPE_SHOP_GRID  = 4;
    public static final int TYPE_SHOP_JP    = 5;

    Context context;
    List foodContentDatas;
    int[] bannerDatas;
    List btnDatas;
    boolean hasHeader = false;

    public FoodAdapter(Context context, List foodContentDatas) {
        this.context = context;
        this.foodContentDatas = foodContentDatas;
    }

    public FoodAdapter(Context context, List foodContentDatas, int[] bannerDatas, List btnDatas) {
        this.context = context;
        this.foodContentDatas = foodContentDatas;
        this.bannerDatas = bannerDatas;
        this.btnDatas = btnDatas;
    }

    public void setHasHeader(boolean hasHeader){
        this.hasHeader = hasHeader;
    }

    /**
     * 设置轮播图
     * @param bannerDatas
     */
    public void setHeadDatas(int[] bannerDatas) {
        this.bannerDatas = bannerDatas;
    }

    /**
     * 设置按钮跳转事件
     * @param btnDatas
     */
    public void setBtnDatas(List btnDatas) {
        this.btnDatas = btnDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_HEADER:
                view = inflater.inflate(R.layout.food_fragment_header,null);
                holder = new HeaderHolder(view);
                break;
            case TYPE_COOKBOOK:
                view = inflater.inflate(R.layout.food_content_item_cookbook,null);
                holder = new CookbookHolder(view);
                break;
            case TYPE_VIDEO:
                view = inflater.inflate(R.layout.food_content_item_video,parent,false);
                holder = new VideoHolder(view);
                break;
            case TYPE_SHOP:
            case TYPE_SHOP_GRID:
            case TYPE_SHOP_JP:
                if(viewType == TYPE_SHOP_GRID){
                    view = inflater.inflate(R.layout.food_content_item_shop_grid,parent,false);
                }else if(viewType == TYPE_SHOP){
                    view = inflater.inflate(R.layout.food_content_item_shop,parent,false);
                }else{
                    view = inflater.inflate(R.layout.food_content_item_shop_jp,parent,false);
                }
                holder = new ShopHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case TYPE_HEADER:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.setIsRecyclable(false);
                headerHolder.crv.setImages(bannerDatas);
                for(int i = 0; i<3; i++){
                    TextView btn = (TextView) headerHolder.btnList.get(i);
                    FoodFragmentBtnItem btnItem = (FoodFragmentBtnItem) btnDatas.get(i);
                    headerHolder.setBtn(btn,btnItem);
                }
                break;
            case TYPE_COOKBOOK:
                int tmpPositon = hasHeader ? position - 1 : position;
                CookbookHolder cookbookHolder = (CookbookHolder) holder;
                cookbookHolder.ivCookbookIcon.setImageResource(((FoodContentItem)foodContentDatas.get(tmpPositon)).getImg());
                cookbookHolder.tvCookbookTitle.setText(((FoodContentItem)foodContentDatas.get(tmpPositon)).getTitle());
                break;
            case TYPE_VIDEO:
                int tmpPositon2 = hasHeader ? position - 1 : position;
                VideoHolder videoHolder = (VideoHolder) holder;
                videoHolder.ivVideoIcon.setImageResource(((FoodContentItem)foodContentDatas.get(tmpPositon2)).getImg());
                videoHolder.tvVideoTitle.setText(((FoodContentItem)foodContentDatas.get(tmpPositon2)).getTitle());

                int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                videoHolder.ivVideoIcon.measure(w, h);
                int height =videoHolder.ivVideoIcon.getMeasuredHeight();
                RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) videoHolder.rlPlay.getLayoutParams();
                layoutParams.height = height;
                videoHolder.rlPlay.setLayoutParams(layoutParams);
                break;
            case TYPE_SHOP:
            case TYPE_SHOP_GRID:
            case TYPE_SHOP_JP:
                int tmpPositon3 = hasHeader ? position - 1 : position;
                FoodContentItem item = ((FoodContentItem)foodContentDatas.get(tmpPositon3));
                ShopHolder shopHolder = (ShopHolder) holder;

                shopHolder.ivShopImg.setImageResource(item.getImg());
                shopHolder.tvShopName.setText(item.getShopName());
                shopHolder.tvShopLocation.setText(item.getShopLocation());
                shopHolder.tvShopPrice.setText((item.getShopPrice() == 0) ? "":("人均:" + Utils.transformDouble(item.getShopPrice()) + "元"));
                shopHolder.setOnItemClickListener();

                if(viewType == TYPE_SHOP){
                    shopHolder.setItemView();
                    shopHolder.tvShopInfo.setText(item.getTitle());
                    int w2 = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int h2 = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    shopHolder.ivShopImg.measure(w2, h2);
                    int height2 =shopHolder.ivShopImg.getMeasuredHeight();
                    RelativeLayout.LayoutParams layoutParams2= (RelativeLayout.LayoutParams) shopHolder.rlIcon.getLayoutParams();
                    layoutParams2.height = height2;
                    shopHolder.rlIcon.setLayoutParams(layoutParams2);
                }else if(viewType == TYPE_SHOP_JP){
                    shopHolder.setJpItemView();
                    shopHolder.tvShopPromotion.setVisibility(item.isShopPromotion() ? View.VISIBLE : View.GONE);
                    shopHolder.tvShopCombo.setVisibility(item.isShopCombo() ? View.VISIBLE : View.GONE);
                    shopHolder.tvShopVip.setVisibility(item.isShopVip() ? View.VISIBLE : View.GONE);
                    shopHolder.ivShopVip.setVisibility(item.isShopVip() ? View.VISIBLE : View.GONE);

                    RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(0,(int)ScreenUtils.getDefaultFontSize(item.getShopName())/2,ScreenUtils.dp2px(context,5),0);//4个参数按顺序分别是左上右下
                    shopHolder.ivShopVip.setLayoutParams(layoutParams2);
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return hasHeader ? foodContentDatas.size()+1 : foodContentDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && hasHeader){
            return TYPE_HEADER;
        }
        return ((FoodContentItem)foodContentDatas.get(hasHeader ? position-1 : position)).getType();
    }

    /**************************************** ViewHolder 专区 ********************************************/
    private class CookbookHolder extends RecyclerView.ViewHolder{
        private ImageView ivCookbookIcon;
        private TextView tvCookbookTitle;

        public CookbookHolder(View itemView) {
            super(itemView);
            ivCookbookIcon = (ImageView) itemView.findViewById(R.id.iv_food_cookbook_icon);
            tvCookbookTitle = (TextView) itemView.findViewById(R.id.tv_food_cookbook_title);
        }
    }

    private class VideoHolder extends RecyclerView.ViewHolder{
        private ImageView ivVideoIcon;
        private TextView tvVideoTitle;
        private RelativeLayout rlPlay;

        public VideoHolder(View itemView) {
            super(itemView);
            ivVideoIcon = (ImageView) itemView.findViewById(R.id.iv_food_video_icon);
            tvVideoTitle = (TextView) itemView.findViewById(R.id.tv_food_video_title);
            rlPlay = (RelativeLayout) itemView.findViewById(R.id.rl_play);
        }
    }

    private class ShopHolder extends RecyclerView.ViewHolder{
        private ImageView ivShopImg;
        private TextView tvShopName;
        private TextView tvShopInfo;
        private TextView tvShopLocation;
        private TextView tvShopPrice;
        //
        private RelativeLayout rlIcon;
        //
        private TextView tvShopPromotion;
        private TextView tvShopCombo;
        private TextView tvShopVip;
        private ImageView ivShopVip;

        public void setItemView() {
            tvShopInfo = (TextView) itemView.findViewById(R.id.tv_food_j_shop_info);
            rlIcon = (RelativeLayout) itemView.findViewById(R.id.rl_icon);
        }

        public void setJpItemView(){
            tvShopPromotion = (TextView) itemView.findViewById(R.id.tv_food_j_shop_promotion);
            tvShopCombo = (TextView) itemView.findViewById(R.id.tv_food_j_shop_combo);
            tvShopVip = (TextView) itemView.findViewById(R.id.tv_food_j_shop_vip);
            ivShopVip = (ImageView) itemView.findViewById(R.id.iv_shop_vip);
        }

        public ShopHolder(View itemView) {
            super(itemView);
            ivShopImg = (ImageView) itemView.findViewById(R.id.iv_food_j_shop_img);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_food_j_shop_name);
            tvShopLocation = (TextView) itemView.findViewById(R.id.tv_food_j_shop_location);
            tvShopPrice = (TextView) itemView.findViewById(R.id.tv_food_j_shop_price);
        }

        public void setOnItemClickListener(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.startActivity(context, ShopDetailActivity_.class);
                }
            });
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder{
        private CycleRotationView crv;
        private TextView tvBtn1;
        private TextView tvBtn2;
        private TextView tvBtn3;
        List btnList = new ArrayList();
        public HeaderHolder(View itemView) {
            super(itemView);
            crv = (CycleRotationView) itemView.findViewById(R.id.crv_food_fragment);
            tvBtn1 = (TextView) itemView.findViewById(R.id.tv_btn_1);
            tvBtn2 = (TextView) itemView.findViewById(R.id.tv_btn_2);
            tvBtn3 = (TextView) itemView.findViewById(R.id.tv_btn_3);
            btnList.add(tvBtn1);
            btnList.add(tvBtn2);
            btnList.add(tvBtn3);
        }
        public void setBtn(TextView btn, final FoodFragmentBtnItem btnItem){
            btn.setText(btnItem.getName());
            Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), btnItem.getIcon(), null);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btn.setCompoundDrawables(null,drawable,null,null);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnItem.getJumpClass() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dataType",btnItem.getName());
                        MFGT.startActivity(context, btnItem.getJumpClass(),bundle);
                    }else{
                        Toast.makeText(context,btnItem.getName(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
