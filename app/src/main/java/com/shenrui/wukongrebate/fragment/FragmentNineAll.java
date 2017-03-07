package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.NineScaleRankActivity_;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.Utils;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.ArrayList;
import java.util.List;


public class FragmentNineAll extends BaseFragment {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_DOWN = 1;
    private static final int ACTION_PULL_UP = 2;
    RecyclerView rv;
    SwipeRefreshLayout srl;
    TextView layoutRefresh;
    int[] cids;
    int pageNo = 1;
    Context context;
    List<TbkItem> goodsList;
    int[] images_cycle;
    NineAllAdapter adapter;
    GridLayoutManager layoutManager;
    public FragmentNineAll() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nine_all, container, false);
        context = getContext();
        cids = getArguments().getIntArray("cids");
        images_cycle = new int[]{R.drawable.index_99_banner1,R.drawable.index_99_banner2,R.drawable.index_99_banner1};
        initView(view);
        setListener();
        downloadGoods(ACTION_DOWNLOAD,1);
        return view;
    }

    private void setListener() {
        //上拉加载下一页
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastPosition >= adapter.getItemCount()-1){
                    pageNo = pageNo+1;
                    downloadGoods(ACTION_PULL_UP,pageNo);
                }
            }
        });
        //下拉刷新
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layoutRefresh.setVisibility(View.VISIBLE);
                downloadGoods(ACTION_PULL_DOWN,1);
            }
        });
    }

    private void downloadGoods(final int action, int pageNo) {
        if(Utils.isNetworkConnected(context)){
            NetDao.downloadNineGoods(context, cids, pageNo, new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String result) {
                    JSONObject jsonObject = (JSONObject) JSON.parse(result);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
                    JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
                    goodsList = JSON.parseArray(jsonArrayItems.toString(), TbkItem.class);
                    Log.e("DeDiWang",goodsList.toString());
                    updateUi(action);
                }

                @Override
                public void onError(String error) {

                }
            });
        }else{
            updateUi(action);
        }
    }

    private void initView(View view) {
        goodsList = new ArrayList<>();
        rv = (RecyclerView) view.findViewById(R.id.nine_all_rv);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.nine_all_srl);
        srl.setColorSchemeColors(getResources().getColor(R.color.green));
        layoutRefresh = (TextView) view.findViewById(R.id.layout_nine_refresh);
        adapter = new NineAllAdapter(goodsList);
        layoutManager = new GridLayoutManager(context,2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0){
                    return 2;
                }else{
                    return 1;
                }
            }
        });
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view)!=0) {
                    if (parent.getChildPosition(view)%2!=0){
                        outRect.set(16,8,8,8);
                    }else{
                        outRect.set(8,8,16,8);
                    }
                }
            }
        });
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    private void updateUi(int action) {
        switch (action){
            case ACTION_DOWNLOAD:
                adapter.initData(goodsList);
                break;
            case ACTION_PULL_DOWN:
                adapter.initData(goodsList);
                srl.setRefreshing(false);
                layoutRefresh.setVisibility(View.GONE);
                break;
            case ACTION_PULL_UP:
                adapter.addData(goodsList);
                break;
        }
    }

    class NineAllAdapter extends RecyclerView.Adapter{
        private static final int TYPE_ONE = 0;//轮播图，畅销榜单，9.9品牌团
        private static final int TYPE_TWO = 1;//九块九商品

        List<TbkItem> list;
        View.OnClickListener listener;
        public NineAllAdapter(List<TbkItem> goodsDatas) {
            list = new ArrayList<>();
            if(goodsDatas!=null){
                list.addAll(goodsDatas);
            }
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
                layout = inflater.inflate(R.layout.layout_nine_holder_one, null);
                holder = new OneHolder(layout);
            }else{
                layout = inflater.inflate(R.layout.layout_nine_goods_item,null);
                holder = new TwoHolder(layout);
            }
            return holder;
        }

        public void initData(List<TbkItem> goodsList){
            if(list!=null){
                this.list.clear();
            }
            if(goodsList!=null){
                list.addAll(goodsList);
                notifyDataSetChanged();
            }
        }
        public void addData(List<TbkItem> goodsList){
            if(goodsList!=null){
                list.addAll(goodsList);
                notifyDataSetChanged();
            }
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == TYPE_ONE){
                OneHolder oneHolder = (OneHolder) holder;
                //强制关闭复用
                oneHolder.setIsRecyclable(false);
                oneHolder.nineCrv.setImages(images_cycle);
            }else{
                TwoHolder twoHolder = (TwoHolder) holder;
                TbkItem tbkItem = list.get(position-1);
                Glide.with(context).load(tbkItem.getPict_url()).into(twoHolder.ivGoodsPic);
                twoHolder.tvGoodsTitle.setText(tbkItem.getTitle());
                twoHolder.tvGoodsPrice.setText(tbkItem.getZk_final_price());
                twoHolder.tvGoodsVolume.setText(String.valueOf(tbkItem.getVolume()));
                twoHolder.ivGoodsType.setImageResource(tbkItem.getUser_type() == 0 ? R.drawable.common_icon_tao : R.drawable.common_icon_mao);
                twoHolder.layoutNineGoods.setTag(String.valueOf(tbkItem.getNum_iid()));
            }

        }

        @Override
        public int getItemCount() {
            return list.size() == 0? 0:list.size()+1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0){
                return TYPE_ONE;
            }else{
                return TYPE_TWO;
            }
        }

        class OneHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            CycleRotationView nineCrv;
            RelativeLayout layoutScaleRank,layoutNineTeam;
            OneHolder(View itemView) {
                super(itemView);
                nineCrv = (CycleRotationView) itemView.findViewById(R.id.nine_cycleRotationView);
                layoutScaleRank = (RelativeLayout) itemView.findViewById(R.id.layout_scale_rank);
                //layoutNineTeam = (RelativeLayout) itemView.findViewById(R.id.layout_nine_team);
                //layoutNineTeam.setOnClickListener(this);
                layoutScaleRank.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.layout_scale_rank://热销榜单
                        MFGT.startActivity(context,NineScaleRankActivity_.class);
                        break;
                    case R.id.layout_nine_team://9.9品牌团

                        break;
                }
            }
        }

        class TwoHolder extends RecyclerView.ViewHolder{
            ImageView ivGoodsPic,ivGoodsType;
            TextView tvGoodsTitle,tvGoodsVolume,tvGoodsPrice;
            LinearLayout layoutNineGoods;
            TwoHolder(View itemView) {
                super(itemView);
                ivGoodsPic = (ImageView) itemView.findViewById(R.id.iv_nine_goods);
                ivGoodsType = (ImageView) itemView.findViewById(R.id.iv_nine_goods_type);
                tvGoodsTitle = (TextView) itemView.findViewById(R.id.tv_nine_goods_title);
                tvGoodsVolume = (TextView) itemView.findViewById(R.id.tv_nine_goods_volume);
                tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_nine_goods_price);
                layoutNineGoods = (LinearLayout) itemView.findViewById(R.id.layout_nine_goods);
                layoutNineGoods.setOnClickListener(listener);
            }
        }
    }
}
