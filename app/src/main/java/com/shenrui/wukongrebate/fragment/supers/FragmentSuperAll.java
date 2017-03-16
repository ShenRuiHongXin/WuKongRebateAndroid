package com.shenrui.wukongrebate.fragment.supers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.TbkFavorite;

import com.shenrui.wukongrebate.entities.UatmTbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.Utils;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.taobao.api.AliSdkWebViewProxyActivity_;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EFragment(R.layout.fragment_super_all)
public class FragmentSuperAll extends Fragment {
    Context context;
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_UP = 1;
    private static final int ACTION_PULL_DOWN = 2;
    private static final int ACTION_NO_NET = 3;

    @ViewById(R.id.srl_super_new)
    SwipeRefreshLayout srl;
    @ViewById(R.id.rv_super_new)
    RecyclerView rv;
    @ViewById(R.id.fab_super_new)
    FloatingActionButton fab;

    List<UatmTbkItem> goodsList;
    SuperNewAdapter adpter;
    GridLayoutManager layoutManager;
    int pageNo = 1;

    @AfterViews
    void init(){
        context = getContext();
        initView();
        srl.setRefreshing(true);
        getFavorites(ACTION_DOWNLOAD,1);
        setListener();
    }

    @Click(R.id.fab_super_new)
    void clickEvent(){
        rv.scrollToPosition(0);
        fab.setVisibility(View.GONE);
    }

    @Background
    void getFavorites(int action,int pageNo) {
        if (Utils.isNetworkConnected(context)){
            Map<String, Object> map = GetNetWorkDatas.getFavoritesGoods(3305391, pageNo ,Constants.PAGE_SIZE);
            List<UatmTbkItem> goods = (List<UatmTbkItem>) map.get(Constants.GOODS);
            updateUi(action,goods);
        }else{
            updateUi(ACTION_NO_NET,null);
        }
    }

    @UiThread
    void updateUi(int action,List<UatmTbkItem> list){
        switch (action){
            case ACTION_DOWNLOAD:
            case ACTION_PULL_DOWN:
                adpter.initData(list);
                if (srl.isRefreshing()){
                    srl.setRefreshing(false);
                }
                break;
            case ACTION_PULL_UP:
                adpter.addData(list);
                break;
            case ACTION_NO_NET:
                Toast.makeText(context, getString(R.string.word_no_net), Toast.LENGTH_SHORT).show();
                srl.setRefreshing(false);
                break;
        }
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getFavorites(ACTION_PULL_DOWN,pageNo);
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = layoutManager.findLastVisibleItemPosition();
                if (position>5){
                    fab.setVisibility(View.VISIBLE);
                }else{
                    fab.setVisibility(View.GONE);
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&position>=adpter.getItemCount()-1){
                    pageNo = pageNo+1;
                    getFavorites(ACTION_PULL_UP,pageNo);
                }
            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        goodsList = new ArrayList<>();
        adpter = new SuperNewAdapter(goodsList);
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
        private static final int TYPE_TWO = 1;
        List<UatmTbkItem> list;
        View.OnClickListener listener;
        SuperNewAdapter(List<UatmTbkItem> list) {
            this.list = list;
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cid = (String) v.getTag();
                    //进入商品详情页
                    Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                    intent.putExtra("num_iid",cid);
                    MFGT.startActivity(context,intent);
                }
            };
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
                UatmTbkItem uatmTbkItem = list.get(position-1);
                twoHolder.tvTitle.setText(uatmTbkItem.getTitle());
                twoHolder.tvPrice.setText(uatmTbkItem.getZk_final_price());
                twoHolder.tvVolume.setText(String.valueOf(uatmTbkItem.getVolume()));
                Glide.with(context).load(uatmTbkItem.getPict_url()).into(twoHolder.ivNewGoods);
                twoHolder.tvRate.setText(uatmTbkItem.getTk_rate());
                twoHolder.layoutNewGoods.setTag(String.valueOf(uatmTbkItem.getNum_iid()));
            }
        }
        public void initData(List<UatmTbkItem> goodsList){
            if (list.size()!=0){
                list.clear();
            }
            if (goodsList!=null){
                list.addAll(goodsList);
                notifyDataSetChanged();
            }
        }
        public void addData(List<UatmTbkItem> goodsList){
            if (goodsList!=null){
                list.addAll(goodsList);
                notifyDataSetChanged();
            }
        }
        @Override
        public int getItemCount() {
            return list.size()==0?0:1+list.size();
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
