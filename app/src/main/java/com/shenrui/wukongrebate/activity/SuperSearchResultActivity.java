package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.NineGoodsAdapter;
import com.shenrui.wukongrebate.adapter.SuperGoodsAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_super_search_result)
public class SuperSearchResultActivity extends Activity {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_DOWN = 1;
    private static final int ACTION_PULL_UP = 2;
    @ViewById(R.id.tv_super_search_result)
    TextView tv_search_result;//显示搜索结果的商品名称和数量
    @ViewById(R.id.srl_super_search_result)
    SwipeRefreshLayout srl;
    @ViewById(R.id.tv_super_search_result_refresh)
    TextView tv_refresh;
    @ViewById(R.id.rv_super_search_result)
    RecyclerView rv;
    @ViewById(R.id.iv_rank_price_expand)
    ImageView ivRankPrice;
    @ViewById(R.id.iv_rank_volume_expand)
    ImageView ivRankVolume;
    @ViewById(R.id.layout_circle)
    LinearLayout layoutCircle;
    @ViewById(R.id.tv_goods_current)
    TextView tvCurrentNumber;
    @ViewById(R.id.tv_goods_total)
    TextView tvTotalNumber;

    Context context;
    String goods;//搜索的商品关键字
    int pageNo = 1;//商品页数
    List<TbkItem> goodsList;
    SuperGoodsAdapter adapter;
    GridLayoutManager gridManager;
    String totals;
    boolean isSortByPriceDesc = false;//是否价格降序排列
    boolean isSortByVolumeDesc = false;//是否销量降序排列

    @AfterViews
    void init(){
        context = this;
        //获得搜索界面传过来的关键字
        goods = getIntent().getStringExtra("super_search_goods");
        tv_search_result.setText(goods);
        initView();
        downloadGoodsList(ACTION_DOWNLOAD,1);
        setListener();
    }

    @Background
    void downloadGoodsList(int action,int pageNo){
        if (Utils.isNetworkConnected(this)){
            Map<String, Object> result = GetNetWorkDatas.getSuperGoods(goods, pageNo);
            List<TbkItem> superGoods = (List<TbkItem>) result.get("goodsList");
            if (action!=ACTION_PULL_UP){
                totals = (String) result.get("totals");
            }
            updateUi(action,superGoods);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();
                    srl.setRefreshing(false);
                    tv_refresh.setVisibility(View.GONE);
                }
            });
        }
    }

    @UiThread
    void updateUi(int action,List<TbkItem> list){
        //显示商品总数
        if (totals!=null){
            tv_search_result.setText(goods+"("+totals+")");
        }
        switch (action){
            case ACTION_DOWNLOAD:
                adapter.initData(list);
                break;
            case ACTION_PULL_DOWN://下拉
                srl.setRefreshing(false);
                adapter.initData(list);
                tv_refresh.setVisibility(View.GONE);
                break;
            case ACTION_PULL_UP://上拉
                adapter.addData(list);
                break;
        }
    }

    private void setListener() {
        //下拉刷新监听
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv_refresh.setVisibility(View.VISIBLE);
                downloadGoodsList(ACTION_PULL_DOWN,1);
            }
        });
        //上拉加载下一页
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = gridManager.findLastVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE && lastPosition>=adapter.getItemCount()-1){
                    pageNo = pageNo +1;
                    downloadGoodsList(ACTION_PULL_UP,pageNo);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = gridManager.findLastVisibleItemPosition();
                if (position>5){
                    layoutCircle.setVisibility(View.VISIBLE);
                    tvCurrentNumber.setText(String.valueOf(position+1));
                    tvTotalNumber.setText(totals);
                }else{
                    layoutCircle.setVisibility(View.GONE);
                }
            }
        });
        //置顶监听
        layoutCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.scrollToPosition(0);
            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(getResources().getColor(R.color.green));
        goodsList = new ArrayList<>();
        adapter = new SuperGoodsAdapter(this,goodsList);
        gridManager = new GridLayoutManager(this,2);
        rv.setAdapter(adapter);
        rv.setLayoutManager(gridManager);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view)%2==0){
                    outRect.set(16,8,8,8);
                }else{
                    outRect.set(8,8,16,8);
                }
            }
        });
    }

    @Click({R.id.iv_super_search_result_back,R.id.tv_super_search_result,R.id.layout_rank_price,R.id.layout_rank_volume})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_super_search_result_back:
                MFGT.finish(this);
                break;
            case R.id.tv_super_search_result:
                //进入超级返搜索界面
                MFGT.finish(this);
                break;
            case R.id.layout_rank_price://按价格排序
                if (!isSortByPriceDesc){
                    adapter.setSortBy(Constants.SORT_PRICE_DESC);
                }else{
                    adapter.setSortBy(Constants.SORT_PRICE_ASC);
                }
                isSortByPriceDesc = !isSortByPriceDesc;
                break;
            case R.id.layout_rank_volume://按销量排序
                if (!isSortByVolumeDesc){
                    adapter.setSortBy(Constants.SORT_VOLUME_DESC);
                }else{
                    adapter.setSortBy(Constants.SORT_VOLUME_ASC);
                }
                isSortByVolumeDesc = !isSortByVolumeDesc;
                break;
        }
    }


    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
