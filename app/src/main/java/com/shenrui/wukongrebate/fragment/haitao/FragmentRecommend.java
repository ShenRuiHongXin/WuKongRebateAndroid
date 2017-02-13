package com.shenrui.wukongrebate.fragment.haitao;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.fragment.BaseFragment;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.HaiTaoItemView;

import static com.shenrui.wukongrebate.R.id.tv;
import static com.shenrui.wukongrebate.R.id.tv_time_limit_title;


/**
 * 悟空推荐
 */
public class FragmentRecommend extends BaseFragment {
    Context context;
    RecyclerView rcv;
    int[] images_cycle,images_good_limit,images_good_presell;
    public FragmentRecommend() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        context = getContext();
        rcv = (RecyclerView) view.findViewById(R.id.rlv_recommend);
        initDatas();
        rcv.setAdapter(new MyRecommendAdapter());
        rcv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        return view;
    }

    private void initDatas() {
        //轮播图
        images_cycle = new int[]{R.drawable.oversea_banner,R.drawable.oversea_good_recommend_banner};
        //限时抢商品数据
        images_good_limit = new int[]{R.drawable.oversea_good_img1,R.drawable.oversea_good_img2,R.drawable.oversea_good_img3};
        //预售拼商品数据
        images_good_presell = new int[]{R.drawable.oversea_good_img4,R.drawable.oversea_good_img5};
    }

    //悟空推荐适配器
    class MyRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final int TYPE_ONE = 0;//轮播图加会员，新人专享,值得买，海淘热卖，预售拼模块
        private static final int TYPE_TWO = 1;//限时抢模块
        private static final int TYPE_THREE = 2;//预售模块

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout;
            switch (viewType){
                case TYPE_ONE:
                    layout = inflater.inflate(R.layout.layout_recommend_item_one, null);
                    holder = new OneViewHolder(layout);
                    break;
                case TYPE_TWO:
                    layout = inflater.inflate(R.layout.layout_recommend_item_two, null);
                    holder = new TwoViewHolder(layout);
                    break;
                case TYPE_THREE:
                    layout = inflater.inflate(R.layout.layout_recommend_item_two, null);
                    holder = new ThreeViewHolder(layout);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            switch (viewType){
                case TYPE_ONE:
                    OneViewHolder oneViewHolder = (OneViewHolder) holder;
                    oneViewHolder.cycleRotationView.setImages(images_cycle);
                    oneViewHolder.haitaoView.setGoodTitle("商品1","商品2","商品3");

                    break;
                case TYPE_TWO:
                    TwoViewHolder timeLimitViewHolder = (TwoViewHolder) holder;
                    timeLimitViewHolder.tvTitle.setText("限时抢");
                    timeLimitViewHolder.rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                    timeLimitViewHolder.rv.setAdapter(new TimeLimitAdapter(images_good_limit));
                    timeLimitViewHolder.rv.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            outRect.set(5,0,5,0);
                        }
                    });
                    break;
                case TYPE_THREE:
                    ThreeViewHolder presellViewHolder = (ThreeViewHolder) holder;
                    presellViewHolder.tvTitle.setText("预售拼");
                    presellViewHolder.rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                    presellViewHolder.rv.setAdapter(new PresellAdapter(images_good_presell));
                    presellViewHolder.rv.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            outRect.set(5,0,5,0);
                        }
                    });
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            if (position==0){
                return TYPE_ONE;
            }else if (position == 1){
                return TYPE_TWO;
            }else {
                return TYPE_THREE;
            }
        }

        class OneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            CycleRotationView cycleRotationView;
            RelativeLayout one,two;//会员专享，新人专享
            HaiTaoItemView haitaoView;
            public OneViewHolder(View itemView) {
                super(itemView);
                cycleRotationView = (CycleRotationView) itemView.findViewById(R.id.crv_recommend);
                one = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_one);
                two = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_two);
                haitaoView = (HaiTaoItemView) itemView.findViewById(R.id.haitaoView);
                one.setOnClickListener(this);
                two.setOnClickListener(this);
                haitaoView.setItemClick(new HaiTaoItemView.HaiTaoViewItemOnClick() {
                    @Override
                    public void itemClick(int itemId) {
                        switch (itemId){
                            case R.id.ll_left://值得买
                                Toast.makeText(context, "值得买", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.ll_right_top://海淘热卖
                                Toast.makeText(context, "海淘热卖", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.ll_right_bottom://预售拼
                                Toast.makeText(context, "预售拼", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.relativeLayout_one://会员专享
                        Toast.makeText(context, "会员专享", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.relativeLayout_two://新人专享
                        Toast.makeText(context, "新人专享", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }

        class TwoViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle;//活动标题
            RecyclerView rv;
            public TwoViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_time_limit_title);
                rv = (RecyclerView) itemView.findViewById(R.id.rv_time_limit);
                tvTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "进入限时抢界面", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        class ThreeViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle;//活动标题
            RecyclerView rv;
            public ThreeViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_time_limit_title);
                rv = (RecyclerView) itemView.findViewById(R.id.rv_time_limit);
                tvTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "进入预售拼界面", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //限时抢商品适配器
    class TimeLimitAdapter extends RecyclerView.Adapter<TimeLimitAdapter.LimitGoodViewHolder>{
        int[] images_good_limit;

        public TimeLimitAdapter(int[] images) {
            this.images_good_limit = images;
        }

        @Override
        public LimitGoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(context).inflate(R.layout.layout_have_goods_activity_theme_item, null);
            return new LimitGoodViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(TimeLimitAdapter.LimitGoodViewHolder holder, int position) {
            holder.ivTheme.setImageResource(images_good_limit[position]);
        }

        @Override
        public int getItemCount() {
            return images_good_limit.length;
        }

        class LimitGoodViewHolder extends RecyclerView.ViewHolder{
            ImageView ivTheme;
            TextView tvThemeName,tvThemeProf;
            public LimitGoodViewHolder(View itemView) {
                super(itemView);
                ivTheme = (ImageView) itemView.findViewById(R.id.iv_have_goods_activity_theme);
                tvThemeName = (TextView) itemView.findViewById(R.id.tv_have_goods_activity_name);
                tvThemeProf = (TextView) itemView.findViewById(R.id.tv_have_goods_activity_prof);
            }
        }
    }

    //预售拼商品适配器
    class PresellAdapter extends RecyclerView.Adapter<PresellAdapter.PresellGoodViewHolder>{
        int[] images_good_presell;

        public PresellAdapter(int[] images_good_presell) {
            this.images_good_presell = images_good_presell;
        }

        @Override
        public PresellGoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(context).inflate(R.layout.layout_have_goods_recommend_theme_item, null);
            return new PresellGoodViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(PresellGoodViewHolder holder, int position) {
            holder.ivTheme.setImageResource(images_good_presell[position]);
        }

        @Override
        public int getItemCount() {
            return images_good_presell.length;
        }

        class PresellGoodViewHolder extends RecyclerView.ViewHolder{
            ImageView ivTheme;
            TextView tvThemeName,tvEye,tvHeart;
            public PresellGoodViewHolder(View itemView) {
                super(itemView);
                ivTheme = (ImageView) itemView.findViewById(R.id.iv_recommend_theme);
                tvThemeName = (TextView) itemView.findViewById(R.id.tv_recommend_theme_name);
                tvEye = (TextView) itemView.findViewById(R.id.tv_recommend_theme_eye);
                tvHeart = (TextView) itemView.findViewById(R.id.tv_recommend_theme_heart);
            }
        }
    }
}
