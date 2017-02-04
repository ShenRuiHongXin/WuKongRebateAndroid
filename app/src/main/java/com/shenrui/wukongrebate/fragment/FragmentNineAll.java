package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.NineContentAdapter;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.RecyclerViewItemDecoration;
import com.shenrui.wukongrebate.view.CycleRotationView;

import java.util.ArrayList;
import java.util.List;


public class FragmentNineAll extends BaseFragment {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_DOWN = 1;
    private static final int ACTION_PULL_UP = 2;
    RecyclerView rv;
    SwipeRefreshLayout srl;
    LinearLayout layoutRefresh;
    int[] cids;
    int pageNo = 1;
    Context context;
    List<TenGoodsData> goodsList;
    NineAllAdapter adapter;
    LinearLayoutManager layoutManager;
    public FragmentNineAll() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nine_all, container, false);
        context = getContext();
        cids = getArguments().getIntArray("cids");
        initView(view);
        setListener();
        downloadGoods(ACTION_DOWNLOAD,1);
        return view;
    }

    private void setListener() {

    }

    private void downloadGoods(final int action, int pageNo) {
        NetDao.downloadNineGoods(context, cids, pageNo, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = (JSONObject) JSON.parse(result);
                JSONObject jsonObject1 = jsonObject.getJSONObject("tbk_item_get_response");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("results");
                JSONArray jsonArrayItems = jsonObject2.getJSONArray("n_tbk_item");
                goodsList = JSON.parseArray(jsonArrayItems.toString(), TenGoodsData.class);
                Log.e("DeDiWang",goodsList.toString());
                updateUi(action);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView(View view) {
        goodsList = new ArrayList<>();
        rv = (RecyclerView) view.findViewById(R.id.nine_all_rv);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.nine_all_srl);
        layoutRefresh = (LinearLayout) view.findViewById(R.id.layout_nine_refresh);
        adapter = new NineAllAdapter(goodsList);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
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
                layoutRefresh.setVisibility(View.GONE);
                break;
            case ACTION_PULL_UP:
                adapter.addData(goodsList);
                break;
        }
    }


    class NineAllAdapter extends RecyclerView.Adapter<NineAllAdapter.MyHolder>{
        NineContentAdapter contentAdapter;
        GridLayoutManager gridLayoutManager;
        List<TenGoodsData> list;
        public NineAllAdapter(List<TenGoodsData> goodsDatas) {
            list = new ArrayList<>();
            if(goodsDatas!=null){
                list.addAll(goodsDatas);
            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_nine_holder, null);
            return new MyHolder(view);
        }

        public void initData(List<TenGoodsData> goodsList){
            if(list!=null){
                this.list.clear();
            }
            if(goodsList!=null){
                contentAdapter.initData(goodsList);
            }
        }
        public void addData(List<TenGoodsData> goodsList){
            if(goodsList!=null){
                contentAdapter.addData(goodsList);
            }
        }
        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.nineCrv.setImages(new int[]{R.drawable.index_99_banner1,R.drawable.index_99_banner2});
            contentAdapter = new NineContentAdapter(context, list);
            layoutManager = new GridLayoutManager(context, 2);
            holder.nineContentRv.setLayoutManager(layoutManager);
            holder.nineContentRv.setAdapter(contentAdapter);
            holder.nineContentRv.addItemDecoration(new RecyclerViewItemDecoration(10));
        }

        @Override
        public int getItemCount() {
            return 1;
        }

        class MyHolder extends RecyclerView.ViewHolder{
            CycleRotationView nineCrv;
            LinearLayout nineLinearLayout;
            RecyclerView nineContentRv;
            public MyHolder(View itemView) {
                super(itemView);
                nineCrv = (CycleRotationView) itemView.findViewById(R.id.nine_cycleRotationView);
                nineLinearLayout = (LinearLayout) itemView.findViewById(R.id.nine_linearLayout);
                nineContentRv = (RecyclerView) itemView.findViewById(R.id.nine_rvContent);

                nineContentRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        int lastPosition = gridLayoutManager.findLastVisibleItemPosition();
                        if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                                lastPosition>=contentAdapter.getItemCount()-1){
                            pageNo = pageNo+1;
                            downloadGoods(ACTION_PULL_UP,pageNo);
                        }
                    }
                });
            }
        }
    }
}
