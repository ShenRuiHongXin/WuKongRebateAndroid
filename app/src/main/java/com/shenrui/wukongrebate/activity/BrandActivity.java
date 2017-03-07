package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.BrandAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.UatmTbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//品牌页展示
@EActivity(R.layout.activity_brand)
public class BrandActivity extends Activity {
    @ViewById(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @ViewById(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @ViewById(R.id.backdrop)
    ImageView iv;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @ViewById(R.id.brand_rv)
    RecyclerView rv;
    private static final int ACTION_DOWNLOAD = 1;
    private static final int ACTION_PULL_UP = 2;
    private static final int ACTION_NO_NET = 3;
    int favorites_id;//该品牌的选品库id
    List<UatmTbkItem> list;
    int totals;
    BrandAdapter adapter;
    GridLayoutManager layoutManager;
    int pageNo = 1;

    @AfterViews
    void init(){
        initView();
        initData(ACTION_DOWNLOAD,1);
        setListener();
    }

    private void setListener() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && position>= adapter.getItemCount()-1){
                    pageNo = pageNo+1;
                    initData(ACTION_PULL_UP,pageNo);
                }
            }
        });
    }

    @Background
    void initData(int action,int pageNo){
        if (Utils.isNetworkConnected(this)){
            if (adapter.isMore()){
                Map<String, Object> map = GetNetWorkDatas.getFavoritesGoods(favorites_id, pageNo, Constants.PAGE_SIZE);
                List<UatmTbkItem> goodsList = (List<UatmTbkItem>) map.get(Constants.GOODS);
                totals = (int) map.get(Constants.TOTALS);
                updateUi(action,goodsList);
            }
        }else{
            updateUi(ACTION_NO_NET,null);
        }
    }

    @UiThread
    void updateUi(int action,List<UatmTbkItem> list){
        switch (action){
            case ACTION_DOWNLOAD:
                adapter.initData(list);
                adapter.setMore(adapter.getItemCount()<totals);
                break;
            case ACTION_PULL_UP:
                adapter.addData(list);
                adapter.setMore(adapter.getItemCount()<totals);
                break;
            case ACTION_NO_NET:
                Toast.makeText(this, getString(R.string.word_no_net), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initView() {
        favorites_id = getIntent().getIntExtra(Constants.FAVORITES_ID, 0);
        collapsingToolbarLayout.setTitle("品牌名称");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        floatingActionButton.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.nav_icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.finish(BrandActivity.this);
            }
        });
        list = new ArrayList<>();
        adapter = new BrandAdapter(list,this);
        rv.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this,2);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view)%2==0){
                    outRect.set(10,5,5,5);
                }else{
                    outRect.set(5,5,10,5);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

}
