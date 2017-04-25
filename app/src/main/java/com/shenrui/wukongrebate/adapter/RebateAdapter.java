package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.DuoBaoActivity_;
import com.shenrui.wukongrebate.activity.NineBlockNineActivity_;
import com.shenrui.wukongrebate.activity.SignActivity_;
import com.shenrui.wukongrebate.activity.SuperActivity_;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.RebateMenuData;
import com.shenrui.wukongrebate.entities.UatmTbkItem;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.RebateItemView;
import com.sixth.adwoad.AdwoAdView;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.text.SimpleDateFormat;
import java.util.Date;
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
                //广告banner
                rebateMenuHolder.cyclerotationview.setUrls(menuData.getCycleList());
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rebateMenuHolder.cyclerotationview.getLayoutParams();

                LogUtil.d("广告尺寸:" + params.width + "-" + params.height);
                int width = ScreenUtils.getScreenWidth(context);
                int height = width * 3 / 7;
                LogUtil.d("新尺寸:" + width + "-" + height);
                params.width = width;
                params.height = height;
                rebateMenuHolder.cyclerotationview.setLayoutParams(params);
                rebateMenuHolder.cyclerotationview.setVisibility(View.GONE);

                /************** 百度SSP **************/
//                rebateMenuHolder.rlBaiduAd.setLayoutParams(params);
//
//                //人群属性
//                AdSettings.setKey(new String[]{"baidu","中国"});
//                //创建广告view
//                String adPlaceId = "3888712";// 重要：请填上你的 代码位ID, 否则 无法请求到广告
//                AdView adView = new AdView(context,adPlaceId);
//                //设置监听器
//                // 设置监听器
//                adView.setListener(new AdViewListener() {
//                    public void onAdSwitch() {
//                        Log.w("", "onAdSwitch");
//                    }
//
//                    public void onAdShow(JSONObject info) {
//                        // 广告已经渲染出来
//                        Log.w("", "onAdShow " + info.toString());
//                    }
//
//                    public void onAdReady(AdView adView) {
//                        // 资源已经缓存完毕，还没有渲染出来
//                        Log.w("", "onAdReady " + adView);
//                    }
//
//                    public void onAdFailed(String reason) {
//                        Log.w("", "onAdFailed " + reason);
//                    }
//
//                    public void onAdClick(JSONObject info) {
//                        // Log.w("", "onAdClick " + info.toString());
//
//                    }
//
//                    @Override
//                    public void onAdClose(JSONObject arg0) {
//                        Log.w("", "onAdClose");
//                    }
//                });
//                // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
//                RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
//                rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                rebateMenuHolder.rlBaiduAd.addView(adView,rllp);

                /************** 安沃 **************/
                AdwoAdView adView = new AdwoAdView(context, "1a2cc79c68e2480faa0b487cf67c4d18",true, 20);
                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width, height);
                //当不设置广告条充满屏幕宽时建议放置在父容器中间
                params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                //设置广告条充满屏幕宽
                adView.setBannerMatchScreenWidth(true);
                // 设置广告监听回调
//                adView.setListener(context);
                rebateMenuHolder.rlBaiduAd.addView(adView,params1);

                //获取当前时间
                SimpleDateFormat formatHour = new SimpleDateFormat("HH");
                SimpleDateFormat formatMins = new SimpleDateFormat("mm");
                SimpleDateFormat formatSeco = new SimpleDateFormat("ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String hour = formatHour.format(curDate);
                String mins = formatMins.format(curDate);
                String seco = formatSeco.format(curDate);
                int curHour = Integer.parseInt(hour);
                int curMin = Integer.parseInt(mins);
                int curSec = Integer.parseInt(seco);

                int secLeft = 0;
                int minLeft = 0;
                int hourLeft = 0;
                if (curSec != 0 && curMin != 0){
                    secLeft = 60 - curSec;
                    minLeft = 59 - curMin;
                    hourLeft = 23 - curHour;
                }else if(curSec == 0 && curMin != 0){
                    minLeft = 60 - curMin;
                    hourLeft = 23 - curHour;
                }else if(curSec != 0 && curMin == 0){
                    secLeft = 60 - curSec;
                    minLeft = 59;
                    hourLeft = 23 - curHour;
                }else{
                    hourLeft = 24 - curHour;
                }
                rebateMenuHolder.layoutThreeItem.setCountTime(hourLeft,minLeft,secLeft);
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
        RelativeLayout rlBaiduAd;

        RebateMenuHolder(View view) {
            super(view);
            cyclerotationview = (CycleRotationView) view.findViewById(R.id.cyclerotationview);
            layoutThreeItem = (RebateItemView) view.findViewById(R.id.layout_three_item);
            tvSuper = (TextView) view.findViewById(R.id.tv_super);
            tvNine = (TextView) view.findViewById(R.id.tv_nine);
            tvGroup = (TextView) view.findViewById(R.id.tv_group);
            tvDuo = (TextView) view.findViewById(R.id.tv_duo);
            tvSign = (TextView) view.findViewById(R.id.tv_sign);
            rlBaiduAd = (RelativeLayout) view.findViewById(R.id.rl_baidu_ssp_ad);
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
