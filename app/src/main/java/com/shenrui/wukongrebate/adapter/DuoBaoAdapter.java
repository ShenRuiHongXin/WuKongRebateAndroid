package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.DuobaoCatsActivity_;
import com.shenrui.wukongrebate.activity.DuobaoRecodeActivity_;
import com.shenrui.wukongrebate.entities.DuobaoGood;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.view.CycleRotationView;

import java.util.List;

/**
 * 夺宝商品的适配器
 */

public class DuoBaoAdapter extends RecyclerView.Adapter{
    public static final int HEADMENU = 1;
    public static final int CONTENT = 2;
    public static final int GOOD = 3;
    private Context context;
    private List<DuobaoData> listData;
    final String[] titles = {"人气","最新","最快","价格"};
    private boolean isLiner = false;


    public DuoBaoAdapter(Context context, List<DuobaoData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View view;
        switch (viewType){
            case HEADMENU :
                view = inflater.inflate(R.layout.duobao_head_menu,null);
                holder = new HeadMenuViewHolder(view);
                break;
            case GOOD:
                view = inflater.inflate(isLiner ? R.layout.duobao_good_item_liner : R.layout.duobao_good_item, parent,false);
                holder = new GoodViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case HEADMENU:
                HeadMenuViewHolder headMenuViewHolder = (HeadMenuViewHolder) holder;
                headMenuViewHolder.crv.setImages(listData.get(position).getImgs());
//                headMenuViewHolder.setIsRecyclable(false);
                headMenuViewHolder.setOnTvClick();
                break;
            case GOOD:
                DuobaoGood good = listData.get(position).getGood();
                GoodViewHolder goodViewHolder = (GoodViewHolder) holder;
                goodViewHolder.pb.setMax(good.getTotalTimes());
                goodViewHolder.pb.setProgress(good.getCurremtTimes());

                goodViewHolder.tvShowRate.setText(good.getCurremtTimes()*100/good.getTotalTimes()+"%");

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) goodViewHolder.tvShowRate.getLayoutParams();
                int screenWidth = ScreenUtils.getScreenWidth(context);
                int m = (screenWidth-10)/2 - (ScreenUtils.dp2px(context,10) * 2);
                int textW = ScreenUtils.getTextWidth(new Paint(),good.getCurremtTimes()*100/good.getTotalTimes()+"%");
                int curPbWid = m*good.getCurremtTimes()/good.getTotalTimes();
                int margi;
                if (curPbWid < textW){
                    margi = 0;
                }else if(curPbWid > m-textW){
                    margi = 0;
                    params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }else{
                    margi = curPbWid - textW/2;
                }
                params1.setMargins(margi,0,0,0);
                goodViewHolder.tvShowRate.setLayoutParams(params1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position).getType() ;
    }

    public void setLiner(boolean liner) {
        isLiner = liner;
    }

    private class HeadMenuViewHolder extends RecyclerView.ViewHolder{
        private CycleRotationView crv;
        private TextView tvCats;
        private TextView tvRecode;
        private TabLayout tabs;
        public HeadMenuViewHolder(View itemView) {
            super(itemView);
            crv = (CycleRotationView) itemView.findViewById(R.id.crv_duobao);
            tvCats = (TextView) itemView.findViewById(R.id.tv_db_cats);
            tvRecode = (TextView) itemView.findViewById(R.id.tv_db_recode);
            tabs = (TabLayout) itemView.findViewById(R.id.tabs_duobao);
            for(int i=0;i<titles.length;i++){
                tabs.addTab(tabs.newTab().setText(titles[i]));
            }
        }

        public void setOnTvClick(){
            tvCats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.startActivity(context, DuobaoCatsActivity_.class);
                }
            });
            tvRecode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.startActivity(context, DuobaoRecodeActivity_.class);
                }
            });
        }
    }

    private class GoodViewHolder extends RecyclerView.ViewHolder{
        ImageView ivBao,ivJoin;
        ProgressBar pb;
        TextView tvBao,tvSum,tvShowRate;
        LinearLayout layoutBao;
        GoodViewHolder(View itemView) {
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

    public static class DuobaoData{
        private int type;
        private int[] imgs;
        private DuobaoGood good;

        public DuobaoData(int type, int[] imgs, DuobaoGood good) {
            this.type = type;
            this.imgs = imgs;
            this.good = good;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int[] getImgs() {
            return imgs;
        }

        public void setImgs(int[] imgs) {
            this.imgs = imgs;
        }

        public DuobaoGood getGood() {
            return good;
        }

        public void setGood(DuobaoGood good) {
            this.good = good;
        }
    }
}
