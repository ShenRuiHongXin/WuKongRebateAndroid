package com.shenrui.wukongrebate.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.NineContentAdapter;
import com.shenrui.wukongrebate.adapter.SearchGoodsAdater;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.CatsItemLocal;
import com.shenrui.wukongrebate.entities.RecyItemIndexData;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.baichuan.android.trade.AlibcContext.initData;

@EActivity(R.layout.activity_nine_block_nine)
public class NineBlockNineActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{
    private static final int ACTION_PULL_UP = 0;
    private static final int ACTION_DOWNLOAD2 = 1;
    private static final int ACTION_DOWNLOAD = 2;
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;

    @ViewById(R.id.nine_tabs)
    TabLayout tabs;
    @ViewById(R.id.nine_rv)
    RecyclerView nineRv;
    //进度条
    @ViewById(R.id.nine_progressBar)
    LinearLayout ll_progressBar;

    SearchGoodsAdater adater;
    NineContentAdapter contentAdapter;
    GridLayoutManager gridLayoutManager;

    List<TenGoodsData> nineGoodsList = new ArrayList<>();
    int pageNo = 1;
    RecyItemIndexData datas;
    @AfterViews
    void init(){
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_title.setText("9块9");
        toolbar_right_image.setImageResource(R.drawable.index_btn_find_n);
        //分类栏
        for(CatsItemLocal cats: Constants.ItemNineCats){
            TabLayout.Tab tab = tabs.newTab();
            tab.setText(cats.getName());
            tab.setTag(cats);
            tabs.addTab(tab);
        }
        setListener();
        //加载第一页“全部”的数据
        initDatas();
    }

    private void initDatas() {
        datas = new RecyItemIndexData();
        String[] urls = {"http://p1.so.qhmsg.com/t01514641c357a98c81.jpg", "http://p4.so.qhmsg.com/t01244e62a3f44edf24.jpg", "http://p4.so.qhmsg.com/t01f017b2c06cc1124e.jpg"};
        datas.setCycleList(urls);
        datas.setTenList(nineGoodsList);
        contentAdapter = new NineContentAdapter(this, datas);
        nineRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        nineRv.setAdapter(contentAdapter);
        showProgressBar();
        downloadDatas("9.9", 1, ACTION_DOWNLOAD2);
    }

    @Background
    void downloadDatas(String q, int pageNo, int action) {
        List<TenGoodsData> list = GetNetWorkDatas.getSearchGoods(q, pageNo);
        updateUi(list,action);
    }
    @UiThread
    void updateUi(List<TenGoodsData> list,int action) {
        switch (action){
            case ACTION_DOWNLOAD:
                adater.initData(list);
                break;
            case ACTION_PULL_UP:
                adater.addData(list);
                break;
            case ACTION_DOWNLOAD2:
                contentAdapter.initData(list);
                break;
        }
        hideProgressBar();
    }

    private void setListener() {
        tabs.addOnTabSelectedListener(this);
        toolbar_left_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition()==0){
            initDatas();
        }else{
            showProgressBar();
            adater = new SearchGoodsAdater(this,nineGoodsList);
            nineRv.setAdapter(adater);
            gridLayoutManager = new GridLayoutManager(this,2);
            nineRv.setLayoutManager(gridLayoutManager);
            //获取分类的9.9商品数据
            CatsItemLocal cats = (CatsItemLocal) tab.getTag();
            downloadDatas("9.9"+cats.getName(),1,ACTION_DOWNLOAD);
            //上拉加载下一页
            nineRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    int lastPosition = gridLayoutManager.findLastVisibleItemPosition();
                    if(newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition>=adater.getItemCount()-1){
                        pageNo = pageNo+1;
                        int position = tabs.getSelectedTabPosition();
                        CatsItemLocal cats = (CatsItemLocal) tabs.getTabAt(position).getTag();
                        String q = "";
                        if(!cats.getName().equals("全部")){
                            q = "9.9"+cats.getName();
                        }else{
                            q = "9.9";
                        }
                        downloadDatas(q,pageNo,ACTION_PULL_UP);
                    }
                }
            });
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //显示/隐藏进度条
    @UiThread
    void showProgressBar(){
        ll_progressBar.setVisibility(View.VISIBLE);
        nineRv.setVisibility(View.INVISIBLE);
    }
    @UiThread
    void hideProgressBar(){
        ll_progressBar.setVisibility(View.GONE);
        nineRv.setVisibility(View.VISIBLE);
    }
}
