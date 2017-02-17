package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.CityActivity_;
import com.shenrui.wukongrebate.activity.MainActivity_;
import com.shenrui.wukongrebate.adapter.SignContentRecyAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.RecyItemIndexData;
import com.shenrui.wukongrebate.entities.SignRecyItemData;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

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
public class FragmentRebate extends BaseFragment{
    //扫一扫按钮
    @ViewById(R.id.btn_scan)
    Button btnScan;

    //首页内容
    @ViewById(R.id.recy_main)
    RecyclerView recyMain;

    //进度条
    @ViewById(R.id.ll_progressBar)
    LinearLayout ll_progressBar;

    //首页数据
    RecyItemIndexData recyItemIndexData;
    List<SignRecyItemData> listData;

    Context context;

    //界面初始化
    @AfterViews
    void init() {
        LogUtil.i("FragmentRebate created");
        context = getContext();
//        ((ImageView) listTitleView.get(1)).setImageResource(R.drawable.index_btn_city_n);
//        ((TextView) listTitleView.get(2)).setText("悟空返利");
//        listTitleView.get(3).setVisibility(View.GONE);
//
//        ((TextView)listTitleView.get(0)).setText(SharedPreferenceUtils.getInstance(context).getCurrentCity());

        showProgressBar();

        recyMain.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });

//        searchView.setEditTextOnlickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SearchActivity_.class);
//                if(Build.VERSION.SDK_INT >= 21){
//                    getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), searchView, "sharedView").toBundle());
//                }else{
//                    getActivity().startActivity(intent);
//                }
//            }
//        });
        initDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("FragmentRebate destroyed");
    }

    //首页数据加载
    @Background
    void initDatas() {
        LogUtil.d("mainActivity mainData: " + MainActivity_.mainData);
        if (MainActivity_.mainData == null) {
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
        } else {
            listData = MainActivity_.mainData;
            updataUi();
        }

    }
    @UiThread
    void updataUi() {
        MainActivity_.mainData = listData;
        recyMain.setAdapter(new SignContentRecyAdapter(getActivity(), listData));

        hideProgressBar();
    }


//    //根据分类获取商品
//    @Background
//    void getCatsGoods(CatsItemLocal catsItemLocal){
//        updateCatsGoods(GetNetWorkDatas.getCatsGoodsFromTaobao(catsItemLocal));
//    }
//    @UiThread
//    void updateCatsGoods(final List list){
//        RecyTenNewGoodsAdapter adapter = new RecyTenNewGoodsAdapter(getActivity(), list);
//        //点击列表项进入商品详情
//        adapter.setOnItemClickLitener(new RecyTenNewGoodsAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
//                intent.putExtra("num_iid",((TenGoodsData)list.get(position)).getNum_iid());
//                context.startActivity(intent);
//
//            }
//        });
//        recyMain.setAdapter(adapter);
//        hideProgressBar();
//    }


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

    @Click({R.id.toolbar_left_text,R.id.toolbar_left_image})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_text:
            case R.id.toolbar_left_image:
                startActivity(new Intent(getContext(), CityActivity_.class));
                break;
        }

    }
}
