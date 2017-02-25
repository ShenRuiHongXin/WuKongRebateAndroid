package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.NineGoodsAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.ACache;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


//显示九块九商品搜索结果的界面
@EActivity(R.layout.activity_nine_search_result)
public class NineSearchResultActivity extends Activity {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_DOWN = 1;
    private static final int ACTION_PULL_UP = 2;
    @ViewById(R.id.tv_nine_search_result)
    TextView tv_search_result;//显示搜索结果的商品名称和数量
    @ViewById(R.id.srl_nine_search_result)
    SwipeRefreshLayout srl;
    @ViewById(R.id.tv_nine_search_result_refresh)
    TextView tv_refresh;
    @ViewById(R.id.rv_nine_search_result)
    RecyclerView rv;
    @ViewById(R.id.layout_nine_circle)
    LinearLayout layoutCircle;
    @ViewById(R.id.tv_nine_goods_current)
    TextView tvCurrentNumber;
    @ViewById(R.id.tv_nine_goods_total)
    TextView tvTotalNumber;

    String goods;//搜索的商品关键字
    int pageNo = 1;//商品页数
    List<TbkItem> goodsList;
    NineGoodsAdapter adapter;
    GridLayoutManager gridManager;
    String totals;

    @AfterViews
    void init(){
        //获得搜索界面传过来的关键字
        goods = getIntent().getStringExtra("search_goods");
        tv_search_result.setText(goods);
        initView();
        downloadGoodsList(ACTION_DOWNLOAD,1);
        setListener();
    }

    @Background
    void downloadGoodsList(int action,int pageNo){
        if (Utils.isNetworkConnected(this)){
            Map<String, Object> result = GetNetWorkDatas.getNineGoods(goods, pageNo);
            List<TbkItem> nineGoods = (List<TbkItem>) result.get("goodsList");
            if (action!=ACTION_PULL_UP){
                totals = (String) result.get("totals");
            }
            updateUi(action,nineGoods);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NineSearchResultActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();
                    srl.setRefreshing(false);
                    tv_refresh.setVisibility(View.GONE);
                }
            });
        }
    }

    @UiThread
    void updateUi(int action,List<TbkItem> list){
        if (totals != null){
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
                if(position>5){
                    layoutCircle.setVisibility(View.VISIBLE);
                    tvCurrentNumber.setText(String.valueOf(position+1));
                    tvTotalNumber.setText(totals);
                }else{
                    layoutCircle.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(getResources().getColor(R.color.green));
        goodsList = new ArrayList<>();
        adapter = new NineGoodsAdapter(this,goodsList);
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

    @Click({R.id.iv_nine_search_result_back,R.id.tv_nine_search_result,R.id.layout_nine_circle})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_nine_search_result_back:
                MFGT.finish(this);
                break;
            case R.id.tv_nine_search_result:
                //进入九块九搜索界面
                MFGT.finish(this);
                break;
            case R.id.layout_nine_circle:
                rv.scrollToPosition(0);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
