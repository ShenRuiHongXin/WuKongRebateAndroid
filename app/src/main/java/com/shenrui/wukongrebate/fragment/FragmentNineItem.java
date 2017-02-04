package com.shenrui.wukongrebate.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.NineContentAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.AiTaoBaoItem;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FragmentNineItem extends BaseFragment {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_DOWN = 1;
    private static final int ACTION_PULL_UP = 2;
    SwipeRefreshLayout srl;
    LinearLayout layout_refresh;
    ProgressBar pb;
    RecyclerView rv;
    int[] cids;
    int page_no = 1;
    Context context;
    List<TenGoodsData> goodsList;
    NineContentAdapter adapter;
    GridLayoutManager layoutManager;
    public FragmentNineItem() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nine_item, container, false);
        context = getContext();
        cids = getArguments().getIntArray("cids");
        goodsList = new ArrayList<>();
        initView(view);
        setListener();
        downloadGoods(ACTION_DOWNLOAD,1);
        return view;
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
                layout_refresh.setVisibility(View.VISIBLE);
                downloadGoods(ACTION_PULL_DOWN,1);
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastPosition>=adapter.getItemCount()-1){
                    page_no = page_no + 1;
                    downloadGoods(ACTION_PULL_UP,page_no);
                }
            }
        });
    }

    private void initView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.nine_item_rv);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.nine_item_srl);
        layout_refresh = (LinearLayout) view.findViewById(R.id.layout_refresh);
        pb = (ProgressBar) view.findViewById(R.id.nine_item_pb);

        adapter = new NineContentAdapter(context, goodsList);
        layoutManager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new RecyclerViewItemDecoration(10));
    }

    private void downloadGoods(final int action, final int page_no){
        NetDao.downloadNineGoods(context, cids, page_no, new OkHttpUtils.OnCompleteListener<String>() {
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

    private void updateUi(int action) {
        switch (action){
            case ACTION_DOWNLOAD:
                adapter.initData(goodsList);
                break;
            case ACTION_PULL_DOWN:
                adapter.initData(goodsList);
                layout_refresh.setVisibility(View.GONE);
                break;
            case ACTION_PULL_UP:
                adapter.addData(goodsList);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.release();
    }
}
