package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.SearchGoodsAdater;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.view.SearchView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/1/7.
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {
    private static final int ACTION_PULL_UP = 0;
    private static final int ACTION_PULL_DOWN = 1;
    private static final int ACTION_DOWNLOAD = 2;
    @ViewById(R.id.iv_back)
    ImageView ivBack;
    //搜索商品界面
    @ViewById(R.id.srl)
    SwipeRefreshLayout srl;
    @ViewById(R.id.recyclerView)
    RecyclerView rv;
    @ViewById(R.id.search_view)
    SearchView searchView;
    //搜索历史界面
    @ViewById(R.id.layout_search_history)
    LinearLayout layout_search_history;

    SearchGoodsAdater adater;
    GridLayoutManager gridLayoutManager;
    List<TenGoodsData> list;
    int pageNo = 1;
    String q = "";//关键词
    @AfterViews
    void init(){
        getUser();
        list = new ArrayList<>();
        initView();
        setListener();
    }

    @Background
    void download(int pageNo, int action) {
        List<TenGoodsData> goodsList = GetNetWorkDatas.getSearchGoods(q,pageNo);
        updateUi(goodsList,action);
    }
    @UiThread
    void updateUi(List<TenGoodsData> goodsList,int action) {
        switch (action){
            case ACTION_DOWNLOAD:
                adater.initData(goodsList);
                break;
            case ACTION_PULL_DOWN:
                srl.setRefreshing(false);
                adater.initData(goodsList);
                break;
            case ACTION_PULL_UP:
                adater.addData(goodsList);
                break;
        }

    }

    private void initView() {
        adater = new SearchGoodsAdater(this,list);
        rv.setAdapter(adater);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(gridLayoutManager);
    }

    private void setListener() {
        //开始搜索
        searchView.setFindOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchView.getEditText().isEmpty()){
                    //隐藏搜索历史界面
                    layout_search_history.setVisibility(View.GONE);
                    srl.setVisibility(View.VISIBLE);

                    q = searchView.getEditText();
                    download(pageNo,ACTION_DOWNLOAD);
                }else{
                    //没有搜索条件，显示搜索历史界面
                    srl.setVisibility(View.GONE);
                    layout_search_history.setVisibility(View.VISIBLE);
                }
            }
        });
        //下拉刷新
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                download(pageNo,ACTION_PULL_DOWN);
            }
        });
        //上拉加载
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = gridLayoutManager.findLastVisibleItemPosition();
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastPosition>=adater.getItemCount()-1){
                    pageNo = pageNo+1;
                    download(pageNo,ACTION_PULL_UP);
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUser(){
        Session session = AlibcLogin.getInstance().getSession();
        LogUtil.d(session.toString());
    }

    /*@Click(R.id.btn_taobao_out)
    void logout(){
        AlibcLogin.getInstance().logout(this, new LogoutCallback(){
            @Override
            public void onFailure(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }*/
}
