package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.SearchGoodsAdater;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;
import com.shenrui.wukongrebate.view.SearchView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
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
    @ViewById(R.id.rv_all_search)
    RecyclerView rvAllSearch;
    @ViewById(R.id.rv_history_search)
    RecyclerView rvHistorySearch;
    @ViewById(R.id.btn_clear_search_history)
    Button btnClearAll;
    @ViewById(R.id.layout_history)
    LinearLayout layoutHistory;

    Context context;
    SearchGoodsAdater adater;
    GridLayoutManager gridLayoutManager;
    List<TenGoodsData> list;
    int pageNo = 1;
    String q = "";//关键词
    @AfterViews
    void init(){
        getUser();
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
    List<String> searthHistory;
    SearthHistoryAdapter historyAdapter;
    private void initView() {
        list = new ArrayList<>();
        context = this;
        adater = new SearchGoodsAdater(this,list);
        rv.setAdapter(adater);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(gridLayoutManager);

        //获取历史搜索数据
        searthHistory = SharedPreferenceUtils.getInstance(this).getSearthHistory();
        if(searthHistory!=null){
            layoutHistory.setVisibility(View.VISIBLE);
            historyAdapter = new SearthHistoryAdapter(this);
            rvHistorySearch.setAdapter(historyAdapter);
            rvHistorySearch.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            rvHistorySearch.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(0,5,0,5);
                }
            });
        }else{
            layoutHistory.setVisibility(View.GONE);
        }
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

                    q = searchView.getEditText().trim();
                    download(pageNo,ACTION_DOWNLOAD);
                    //将搜索词放入首选项
                    SharedPreferenceUtils.getInstance(context).putSearthHistory(q);
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
                if (Build.VERSION.SDK_INT >= 21){
                    finishAfterTransition();
                }else{
                    MFGT.finish(SearchActivity.this);
                }
            }
        });
        //清空全部搜索记录
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtils.getInstance(context).clearAllHistory();
                searthHistory = null;
                historyAdapter.notifyDataSetChanged();
                layoutHistory.setVisibility(View.GONE);
            }
        });
    }

    //搜索历史适配器
    class SearthHistoryAdapter extends RecyclerView.Adapter<SearthHistoryAdapter.MyHolder>{
        Context context;
        View.OnClickListener clickListener;
        public SearthHistoryAdapter(final Context context) {
            this.context = context;
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    switch (v.getId()){
                        case R.id.iv_delete_history:
                            //删除单条搜索记录
                            SharedPreferenceUtils.getInstance(context).clearOneHistory(searthHistory.get(position));
                            searthHistory.remove(position);
                            notifyDataSetChanged();
                            if(SharedPreferenceUtils.getInstance(context).getSearthHistory()==null){
                                layoutHistory.setVisibility(View.GONE);
                            }
                            break;
                        case R.id.tv_show_history:
                            q = searthHistory.get(position);
                            download(pageNo,ACTION_DOWNLOAD);
                            layout_search_history.setVisibility(View.GONE);
                            srl.setVisibility(View.VISIBLE);
                            break;
                    }

                }
            };
        }
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_searth_history, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tvShowHistory.setText(searthHistory.get(position));
            holder.ivDeleteHistory.setTag(position);
            holder.tvShowHistory.setTag(position);
        }

        @Override
        public int getItemCount() {
            return searthHistory==null?0:searthHistory.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            TextView tvShowHistory;
            ImageView ivDeleteHistory;
            public MyHolder(View itemView) {
                super(itemView);
                tvShowHistory = (TextView) itemView.findViewById(R.id.tv_show_history);
                ivDeleteHistory = (ImageView) itemView.findViewById(R.id.iv_delete_history);
                ivDeleteHistory.setOnClickListener(clickListener);
                tvShowHistory.setOnClickListener(clickListener);
            }
        }
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

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
