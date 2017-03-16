package com.shenrui.wukongrebate.fragment.zhi;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.SearchGoodsAdater;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.Utils;
import com.shenrui.wukongrebate.view.CycleRotationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EFragment(R.layout.fragment_jx)
public class JXFragment extends Fragment {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_UP = 1;
    private static final int ACTION_REFRESH = 2;
    private static final int ACTION_NO_NET = 3;
    @ViewById(R.id.jx_srl)
    SwipeRefreshLayout srl;
    @ViewById(R.id.jx_fab)
    FloatingActionButton fab;
    @ViewById(R.id.jx_rv)
    RecyclerView rv;

    Context context;
    SearchGoodsAdater adapter;
    List<TbkItem> list;
    LinearLayoutManager layoutManager;
    int pageNo = 1;

    @AfterViews
    void init(){
        context = getContext();
        initRv();
        initData(ACTION_DOWNLOAD,pageNo);
        setListener();
    }

    @Click(R.id.jx_fab)
    void clickEvent(){
        rv.scrollToPosition(0);
        fab.setVisibility(View.GONE);
    }

    private void setListener() {
        //下拉刷新
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                initData(ACTION_REFRESH,pageNo);
            }
        });
        //上拉加载
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                //置顶按钮是否隐藏
                if (lastPosition>3){
                    fab.setVisibility(View.VISIBLE);
                }else{
                    fab.setVisibility(View.GONE);
                }
                if (newState==RecyclerView.SCROLL_STATE_IDLE && lastPosition>=adapter.getItemCount()-1){
                    pageNo = pageNo +1;
                    initData(ACTION_PULL_UP,pageNo);
                }
            }
        });
    }

    @Background
    void initData(int action,int pageNo){
        if (Utils.isNetworkConnected(context)){
            Map<String, Object> result = GetNetWorkDatas.getSearchGoods("女装", pageNo);
            List<TbkItem> searchGoods = (List<TbkItem>) result.get("goodsList");
            updateUi(action,searchGoods);
        }else{
            updateUi(ACTION_NO_NET,null);
        }
    }

    @UiThread
    void updateUi(int action,List<TbkItem> searchGoods) {
        switch (action){
            case ACTION_DOWNLOAD:
            case ACTION_REFRESH:
                adapter.initData(searchGoods);
                srl.setRefreshing(false);
                break;
            case ACTION_PULL_UP:
                adapter.addData(searchGoods);
                break;
            case ACTION_NO_NET:
                Toast.makeText(context, getResources().getString(R.string.word_no_net), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initRv() {
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        srl.setRefreshing(true);
        list = new ArrayList<>();
        adapter = new SearchGoodsAdater(context,list);
        rv.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
    }

}
