package com.shenrui.wukongrebate.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.SearchActivity;
import com.shenrui.wukongrebate.activity.SearchActivity_;
import com.shenrui.wukongrebate.adapter.RecyTenNewGoodsAdapter;
import com.shenrui.wukongrebate.adapter.SignContentRecyAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.CatsItemLocal;
import com.shenrui.wukongrebate.entities.RecyItemIndexData;
import com.shenrui.wukongrebate.entities.SignRecyItemData;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.Utils;
import com.shenrui.wukongrebate.view.SearchView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2016/12/28.
 * 待优化：
 * 1.每次加载界面都会重新请求网络，影响速度。解决思路：APP启动时请求网络数据，并将获得的数据用sharedpreferences或SQLite等技术缓存，
 * 若在不退出的APP的情况下重新加载FragmentRebate，则直接取缓存的数据；用户刷新数据的时候则请求网络更新数据，并更新缓存；若APP退
 * 出，则清空缓存，下次启动再次请求网络数据并缓存；
 */

@EFragment(R.layout.rebate_fragment_page)
public class FragmentRebate extends BaseFragment implements TabLayout.OnTabSelectedListener{

    //标题栏
    @ViewsById({R.id.toolbar_left_text, R.id.toolbar_left_image, R.id.toolbar_title, R.id.toolbar_right_image})
    List<View> listTitleView;

    //搜索栏
    @ViewById(R.id.sv_searchView)
    SearchView searchView;

    //分类栏
    @ViewById(R.id.tl_goods_category)
    TabLayout tl_goods_category;

    //首页内容
    @ViewById(R.id.recy_main)
    RecyclerView recyMain;

    //进度条
    @ViewById(R.id.ll_progressBar)
    LinearLayout ll_progressBar;

    //首页数据
    RecyItemIndexData recyItemIndexData;
    List<SignRecyItemData> listData;

    //界面初始化
    @AfterViews
    void init() {
        ((ImageView) listTitleView.get(1)).setImageResource(R.drawable.index_btn_city_n);
        ((TextView) listTitleView.get(2)).setText("悟空返利");
        listTitleView.get(3).setVisibility(View.GONE);

        showProgressBar();

        recyMain.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });

        //商品分类
        tl_goods_category.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (CatsItemLocal cats : Constants.Itemcats) {
            TabLayout.Tab tab = tl_goods_category.newTab();
            tab.setText(cats.getName());
            tab.setTag(cats);
            tl_goods_category.addTab(tab);
        }
        tl_goods_category.setOnTabSelectedListener(this);

        searchView.setEditTextOnlickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity_.class);
                if(Build.VERSION.SDK_INT >= 21){
                    getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), searchView, "sharedView").toBundle());
                }else{
                    getActivity().startActivity(intent);
                }
            }
        });

        initDatas();
    }

    //首页数据加载
    @Background
    void initDatas() {
        if (Utils.isNetworkConnected(getActivity())){
            recyItemIndexData = new RecyItemIndexData();

            String[] urls = {"http://p1.so.qhmsg.com/t01514641c357a98c81.jpg", "http://p4.so.qhmsg.com/t01244e62a3f44edf24.jpg", "http://p4.so.qhmsg.com/t01f017b2c06cc1124e.jpg"};
            recyItemIndexData.setCycleList(urls);//轮播图
            recyItemIndexData.setCjfanList(new ArrayList());//超级返
            recyItemIndexData.setSignList(new ArrayList());//签到
            recyItemIndexData.setActiviList(new ArrayList());//活动
            List listTmp = GetNetWorkDatas.getTenNewGoods();
            if(listTmp == null){
                initDatas();
            }
            recyItemIndexData.setTenList(listTmp);//早10点上新
            listData = new ArrayList<>();
            listData.add(new SignRecyItemData(recyItemIndexData, SignRecyItemData.MAIN_ITEM));
            updataUi();
        }else{
            initDatas();
        }

    }
    @UiThread
    void updataUi() {
        recyMain.setAdapter(new SignContentRecyAdapter(getActivity(), listData, this));

        hideProgressBar();
    }


    //分类类目切换，处理数据
    public void handleNewData(TabLayout.Tab tab) {
        int p = tab.getPosition();
        LogUtil.i("cats: " + p);
        if(p == 0){
            recyMain.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyMain.setAdapter(new SignContentRecyAdapter(getActivity(), listData, this));
        }else{
            showProgressBar();
            int spanCount2 = 2;
            recyMain.setLayoutManager(new GridLayoutManager(getActivity(), spanCount2));

            getCatsGoods((CatsItemLocal)(tab.getTag()));
        }
    }


    //根据分类获取商品
    @Background
    void getCatsGoods(CatsItemLocal catsItemLocal){
        updateCatsGoods(GetNetWorkDatas.getCatsGoodsFromTaobao(catsItemLocal));
    }
    @UiThread
    void updateCatsGoods(List list){
        recyMain.setAdapter(new RecyTenNewGoodsAdapter(getActivity(), list));
        hideProgressBar();
    }


    //分类点击事件监听
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        handleNewData(tab);
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
        recyMain.setVisibility(View.INVISIBLE);
    }
    @UiThread
    void hideProgressBar(){
        ll_progressBar.setVisibility(View.GONE);
        recyMain.setVisibility(View.VISIBLE);
    }
}
