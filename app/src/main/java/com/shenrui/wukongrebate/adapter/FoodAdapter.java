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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    public static final int TYPE_HEADER         = 0;    //头部，轮播、按钮
    public static final int TYPE_COOKBOOK       = 1;    //菜谱，美食攻略首页
    public static final int TYPE_VIDEO          = 2;    //视频
    public static final int TYPE_SHOP           = 3;    //店铺，美食记首页
    public static final int TYPE_SHOP_GRID      = 4;    //店铺，美食记聚会、刁角美食
    public static final int TYPE_SHOP_JP        = 5;    //店铺，美食记精品美食
    public static final int TYPE_FOOTER         = 6;    //底部，点击加载
    public static final int TYPE_COOKBOOK_MENU  = 7;    //菜谱，美食攻略菜谱
    public static final int TYPE_COOKBOOK_CATS  = 8;    //类别，美食攻略菜谱类别

    public static final int LOAD_STATE_DEFAULT  = 10;   //默认情况
    public static final int LOAD_STATE_LOADING  = 11;     //正在加载
    public static final int LOAD_STATE_FAILURE  = 12;     //加载失败
    public static final int LOAD_STATE_SUCCESS  = 13;     //加载成功
    public static final int LOAD_STATE_FINISH   = 14;      //没有更多数据了

    Context context;
    List foodContentDatas;
    int[] bannerDatas;
    List btnDatas;
    boolean hasHeader = false;
    boolean hasFooter = true;
    private int loadState = LOAD_STATE_DEFAULT;
    private FooterHolder footerHolder = null;
    private View.OnClickListener loadMoreListener;

    public FoodAdapter(Context context, List foodContentDatas) {
        this.context = context;
        this.foodContentDatas = foodContentDatas;
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
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.footer_layout,null);
                holder = new FooterHolder(view);
                break;
            case TYPE_COOKBOOK:
                view = inflater.inflate(R.layout.food_content_item_cookbook,null);
                holder = new CookbookHolder(view);
                break;
            case TYPE_COOKBOOK_MENU:
                view = inflater.inflate(R.layout.food_content_item_cookbook_menu,null);
                holder = new CookbookMenuHolder(view);
                break;
            case TYPE_COOKBOOK_CATS:
                view = inflater.inflate(R.layout.food_content_item_cookbook_menu_cats,null);
                holder = new CookbookMenuCatsHolder(view);
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
                if (btnDatas != null){
                    headerHolder.llBtnGroup.setVisibility(View.VISIBLE);
                    for(int i = 0; i<3; i++){
                        TextView btn = (TextView) headerHolder.btnList.get(i);
                        FoodFragmentBtnItem btnItem = (FoodFragmentBtnItem) btnDatas.get(i);
                        headerHolder.setBtn(btn,btnItem);
                    }
                }
                break;
            case TYPE_FOOTER:
                    FooterHolder footerHolder = (FooterHolder) holder;
                if (hasFooter){
                    this.footerHolder = footerHolder;
                    footerHolder.setLoadMoreOnclickListener(this.loadMoreListener);
                    footerHolder.mProgressBar.setVisibility(View.GONE);
                    footerHolder.tvFooter.setText("点击加载更多");
                }else{
                    footerHolder.itemView.setVisibility(View.GONE);
                }
                break;
            case TYPE_COOKBOOK:
                int tmpPositon = hasHeader ? position - 1 : position;
                CookbookHolder cookbookHolder = (CookbookHolder) holder;
                cookbookHolder.ivCookbookIcon.setImageResource(((FoodContentItem)foodContentDatas.get(tmpPositon)).getImg());
                cookbookHolder.tvCookbookTitle.setText(((FoodContentItem)foodContentDatas.get(tmpPositon)).getTitle());
                break;
            case TYPE_COOKBOOK_MENU:
                int tmpPositon1 = hasHeader ? position - 1 : position;
                CookbookMenuHolder cookbookMenuHolder = (CookbookMenuHolder) holder;
//                cookbookMenuHolder.ivCookbookImg.setImageResource(((FoodContentItem)foodContentDatas.get(tmpPositon1)).getImg());
                Glide.with(context)
                        .load("http://api.jisuapi.com/recipe/upload/20160720/113938_92715.jpg")
                        .into(cookbookMenuHolder.ivCookbookImg);
                cookbookMenuHolder.tvCookbookTitle.setText(((FoodContentItem)foodContentDatas.get(tmpPositon1)).getTitle());
                break;
            case TYPE_COOKBOOK_CATS:
                int tmpPositon4 = hasHeader ? position - 1 : position;
                CookbookMenuCatsHolder cookbookMenuCatsHolder = (CookbookMenuCatsHolder) holder;
                cookbookMenuCatsHolder.tvCookbookCats.setText(((FoodContentItem)foodContentDatas.get(tmpPositon4)).getTitle());
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
        return hasHeader ? foodContentDatas.size()+2 : foodContentDatas.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && hasHeader){
            return TYPE_HEADER;
        }
        if((position == foodContentDatas.size()+1 && hasHeader) || (position == foodContentDatas.size() && !hasHeader)){
            return TYPE_FOOTER;
        }
        return ((FoodContentItem)foodContentDatas.get(hasHeader ? position-1 : position)).getType();
    }

    /**************************************** 自定义方法专区 ********************************************/
    /**
     * 设置是否有头部
     * @param hasHeader
     */
    public void setHasHeader(boolean hasHeader){
        this.hasHeader = hasHeader;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    /**
     * 设置轮播图
     * @param bannerDatas
     */
    public void setHeadDatas(int[] bannerDatas) {
        hasHeader = true;
        this.bannerDatas = bannerDatas;
    }

    /**
     * 设置按钮跳转事件
     * @param btnDatas
     */
    public void setBtnDatas(List btnDatas) {
        this.btnDatas = btnDatas;
    }

    public int getLoadState() {
        return loadState;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
    }

    public void setLoadMoreListener(View.OnClickListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setLoadDefaultUI(){
        footerHolder.setProgressBarVisiable(View.GONE);
        footerHolder.setProgressBarInfoText("点击加载更多");
    }
    public void setloadingUI(){
        footerHolder.setProgressBarVisiable(View.VISIBLE);
        footerHolder.setProgressBarInfoText("加载中。。。");
    }
    public void setLoadFailUI(){
        footerHolder.setProgressBarVisiable(View.GONE);
        footerHolder.setProgressBarInfoText("加载失败，点击重试");
    }
    public void setLoadFinishUI(){
        footerHolder.setProgressBarVisiable(View.GONE);
        footerHolder.setProgressBarInfoText("没有更多数据了o(╯□╰)o");
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
        private LinearLayout llBtnGroup;
        List btnList = new ArrayList();
        public HeaderHolder(View itemView) {
            super(itemView);
            crv = (CycleRotationView) itemView.findViewById(R.id.crv_food_fragment);
            tvBtn1 = (TextView) itemView.findViewById(R.id.tv_btn_1);
            tvBtn2 = (TextView) itemView.findViewById(R.id.tv_btn_2);
            tvBtn3 = (TextView) itemView.findViewById(R.id.tv_btn_3);
            llBtnGroup = (LinearLayout) itemView.findViewById(R.id.ll_header_btn_group);
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

    private class FooterHolder extends RecyclerView.ViewHolder{
        private ProgressBar mProgressBar;
        private TextView tvFooter;

        public FooterHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_footer);
            tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
        }
        private void setProgressBarVisiable(int visiable){
            mProgressBar.setVisibility(visiable);
        }
        private void setProgressBarInfoText(String info){
            tvFooter.setText(info);
        }
        public void setLoadMoreOnclickListener(View.OnClickListener onclickListener){
            itemView.setOnClickListener(onclickListener);
        }
    }

    private class CookbookMenuHolder extends RecyclerView.ViewHolder{
        private ImageView ivCookbookImg;
        private TextView tvCookbookTitle;

        public CookbookMenuHolder(View itemView) {
            super(itemView);
            ivCookbookImg = (ImageView) itemView.findViewById(R.id.iv_cookbook_menu_img);
            tvCookbookTitle = (TextView) itemView.findViewById(R.id.tv_cookbook_menu_title);
        }
    }

    private class CookbookMenuCatsHolder extends RecyclerView.ViewHolder{
        private TextView tvCookbookCats;
        public CookbookMenuCatsHolder(View itemView) {
            super(itemView);
            tvCookbookCats = (TextView) itemView.findViewById(R.id.tv_cookbook_menu_cats);
        }
    }
}
