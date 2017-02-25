package com.shenrui.wukongrebate.fragment.supers;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.taobao.api.AliSdkWebViewProxyActivity_;


public class FragmentSuperAll extends Fragment {
    Context context;
    SwipeRefreshLayout srl;
    RecyclerView rv;
    SuperNewAdapter adpter;
    GridLayoutManager layoutManager;

    public FragmentSuperAll() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_super_all, container, false);
        context = getContext();
        initView(layout);
        setListener();
        return layout;
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
            }
        });
    }

    private void initView(View layout) {
        srl = (SwipeRefreshLayout) layout.findViewById(R.id.srl_super_new);
        rv = (RecyclerView) layout.findViewById(R.id.rv_super_new);
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        adpter = new SuperNewAdapter();
        rv.setAdapter(adpter);
        layoutManager = new GridLayoutManager(context,2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==0){
                    return 2;
                }else {
                    return 1;
                }
            }
        });
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childPosition = parent.getChildPosition(view);
                if (childPosition!=0){
                    if (childPosition/2!=0){
                        outRect.set(6,6,4,0);
                    }else{
                        outRect.set(4,6,6,0);
                    }
                }
            }
        });
    }


    class SuperNewAdapter extends RecyclerView.Adapter{
        private static final int TYPE_ONE = 0;//轮播图，爱逛街，美妆不可少，创意神器，精品小吃
        private static final int TYPE_TWO = 1;//九块九商品

        View.OnClickListener listener;
        SuperNewAdapter() {

            /*listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cid = (String) v.getTag();
                    //进入商品详情页
                    Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                    intent.putExtra("num_iid",cid);
                    MFGT.startActivity(context,intent);
                }
            };*/
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder;
            View layout;
            LayoutInflater inflater = LayoutInflater.from(context);
            if (viewType == TYPE_ONE){
                layout = inflater.inflate(R.layout.layout_super_new_holder_one, null);
                holder = new OneHolder(layout);
            }else{
                layout = inflater.inflate(R.layout.layout_rebate_goods_item,null);
                holder = new TwoHolder(layout);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == TYPE_ONE){
                OneHolder oneHolder = (OneHolder) holder;
                //强制关闭复用
                oneHolder.setIsRecyclable(false);
                oneHolder.superNewCrv.setImages(new int[]{R.drawable.home_banner,R.drawable.home_banner,R.drawable.home_banner});
            }else{
                TwoHolder twoHolder = (TwoHolder) holder;
                if (position%2==0){
                    twoHolder.ivNewGoods.setImageResource(R.drawable.img_right_clothes);
                    twoHolder.tvTitle.setText("稻草人时尚单肩小挎包");
                    twoHolder.tvPrice.setText("165.8");
                    twoHolder.tvVolume.setText("355");
                }else{
                    twoHolder.ivNewGoods.setImageResource(R.drawable.img_left_bag);
                    twoHolder.tvTitle.setText("稻草人时尚单肩小挎包");
                    twoHolder.tvPrice.setText("95.8");
                    twoHolder.tvVolume.setText("251");
                }
            }

        }

        @Override
        public int getItemCount() {
            return 11;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0){
                return TYPE_ONE;
            }else{
                return TYPE_TWO;
            }
        }

        class OneHolder extends RecyclerView.ViewHolder{
            CycleRotationView superNewCrv;

            OneHolder(View itemView) {
                super(itemView);
                superNewCrv = (CycleRotationView) itemView.findViewById(R.id.cycle_super_new);

            }
        }

        class TwoHolder extends RecyclerView.ViewHolder{
            ImageView ivNewGoods;
            TextView tvTitle,tvPrice,tvRate,tvVolume;
            LinearLayout layoutNewGoods;
            TwoHolder(View itemView) {
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
}
