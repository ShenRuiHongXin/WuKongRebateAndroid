package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.SearchActivity_;
import com.shenrui.wukongrebate.adapter.RebateAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.RebateMenuData;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by heikki on 2016/12/28.
 * 待优化：
 * 1.每次加载界面都会重新请求网络，影响速度。解决思路：APP启动时请求网络数据，并将获得的数据用sharedpreferences或SQLite等技术缓存，
 * 若在不退出的APP的情况下重新加载FragmentRebate，则直接取缓存的数据；用户刷新数据的时候则请求网络更新数据，并更新缓存；若APP退
 * 出，则清空缓存，下次启动再次请求网络数据并缓存；
 */

@EFragment(R.layout.rebate_fragment_page)
public class FragmentRebate extends BaseFragment{
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_UP = 1;
    @ViewById(R.id.ll_search)
    LinearLayout layoutSearch;
    //扫一扫按钮
    @ViewById(R.id.iv_scan)
    ImageView ivScan;
    //搜索框
    @ViewById(R.id.tv_search)
    TextView tvSearch;
    //首页内容
    @ViewById(R.id.recy_main)
    RecyclerView recyMain;
    //进度条
    @ViewById(R.id.ll_progressBar)
    LinearLayout ll_progressBar;
    //首页菜单数据
    RebateMenuData rebateMenuData;
    //首页新品数据
    List<TbkItem> goodsData;
    Context context;
    RebateAdapter adapter;
    GridLayoutManager layoutManager;

    int mDistanceY;
    int pageNo = 1;
    //界面初始化
    @AfterViews
    void init() {
        context = getContext();
        showProgressBar();
        initView();
        initDatas();
        setListener();
    }

    private void setListener() {
        //上拉加载监听
        recyMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition>=adapter.getItemCount()-1){
                    pageNo = pageNo + 1;
                    downloadNewGoodsList();
                }
            }
            //搜索栏透明度渐变
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mDistanceY += dy;
                if (mDistanceY<=0){
                    layoutSearch.setBackgroundColor(Color.argb(0,255,255,255));
                }else if (mDistanceY<=400){
                    float scale = (float)mDistanceY / (float) 400;
                    layoutSearch.setBackgroundColor(Color.argb((int) (255*scale),255,255,255));
                }else{
                    layoutSearch.setBackgroundColor(Color.argb(255,255,255,255));
                }
            }
        });
    }

    private void initView() {
        rebateMenuData = new RebateMenuData();
        goodsData = new ArrayList<>();
        adapter = new RebateAdapter(context, rebateMenuData ,goodsData);
        layoutManager = new GridLayoutManager(context, 2);
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
        recyMain.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view)!=0){
                    if (parent.getChildPosition(view)%2!=0){
                        outRect.set(4,0,3,4);
                    }else{
                        outRect.set(3,0,4,4);
                    }
                }
            }
        });
        recyMain.setAdapter(adapter);
        recyMain.setLayoutManager(layoutManager);
    }

    //首页数据加载
    @Background
    void initDatas() {
        if (Utils.isNetworkConnected(getActivity())){
            String[] urls = {"http://p1.so.qhmsg.com/t01514641c357a98c81.jpg", "http://p4.so.qhmsg.com/t01244e62a3f44edf24.jpg", "http://p4.so.qhmsg.com/t01f017b2c06cc1124e.jpg"};
            rebateMenuData.setCycleList(urls);//轮播图
            Map<String, Object> map = GetNetWorkDatas.getSuperGoods("冬装女", 1);
            goodsData= (List<TbkItem>) map.get("goodsList");
            if(goodsData == null){
                showProgressBar();
            }
            updataUi(ACTION_DOWNLOAD);
        }else{
            showProgressBar();
        }
    }

    @Background
    void downloadNewGoodsList(){
        Map<String, Object> map = GetNetWorkDatas.getSuperGoods("冬装女", pageNo);
        goodsData = (List<TbkItem>) map.get("goodsList");
        updataUi(ACTION_PULL_UP);
    }

    @UiThread
    void updataUi(int action) {
        switch (action){
            case ACTION_DOWNLOAD:
                adapter.initData(rebateMenuData,goodsData);
                hideProgressBar();
                break;
            case ACTION_PULL_UP:
                adapter.addData(goodsData);
                break;
        }
    }

    //显示/隐藏进度条
    @UiThread
    void showProgressBar(){
        ll_progressBar.setVisibility(View.VISIBLE);
        recyMain.setVisibility(View.GONE);
    }

    @UiThread
    void hideProgressBar(){
        ll_progressBar.setVisibility(View.GONE);
        recyMain.setVisibility(View.VISIBLE);
    }

    @Click({R.id.tv_search,R.id.iv_scan})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.tv_search:
                MFGT.startActivity(context, SearchActivity_.class);
                break;
            case R.id.iv_scan:

                break;
        }
    }
}
