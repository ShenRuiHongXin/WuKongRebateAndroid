package com.shenrui.wukongrebate.fragment.zhi;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.ZhiAdapter;
import com.shenrui.wukongrebate.entities.ZhiJxItem;
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

@EFragment(R.layout.fragment_jx)
public class HTFragment extends Fragment {
    private static final int ACTION_DOWNLOAD = 0;
    private static final int ACTION_PULL_UP = 1;
    private static final int ACTION_REFRESH = 2;
    private static final int ACTION_NO_NET = 3;
    //刷新组件
    @ViewById(R.id.jx_srl)
    SwipeRefreshLayout srl;
    //浮动标题栏
    @ViewById(R.id.jx_fab)
    FloatingActionButton fab;
    //内容
    @ViewById(R.id.jx_rv)
    RecyclerView rv;

    Context context;
    ZhiAdapter adapter;
    List<ZhiJxItem> lists = new ArrayList<>();
    LinearLayoutManager layoutManager;
    int pageNo = 1;

    @AfterViews
    void init(){
        context = getContext();
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        srl.setRefreshing(true);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        setListener();

        initData(ACTION_DOWNLOAD,pageNo);
    }

    //回到顶部
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
            if (action == ACTION_DOWNLOAD){
                lists.add(new ZhiJxItem(R.drawable.ht_home_img_one,"酒杯/红酒杯","特别杯口造型，独特而精致，整体造型流线优美，完美的与美酒结合。",false));
                lists.add(new ZhiJxItem(R.drawable.ht_home_img_two,"KIKO双色烘焙眼影","kiko 16最新款，干湿两用双色烘焙眼影，令眼部妆容明亮、浓郁，色彩可调节。为了获得整体引人注目",false));
                lists.add(new ZhiJxItem(R.drawable.ht_home_img_three,"Miele 滚轴式蒸汽熨烫机","这款来自德国的Miele滚轴式蒸汽熨烫机，全开放式滚轴轻松熨烫衣物。双冰箱技术能快速提高",false));
            }else if(action == ACTION_REFRESH){
                lists = new ArrayList<>();
                lists.add(new ZhiJxItem(R.drawable.ht_home_img_one,"酒杯/红酒杯","特别杯口造型，独特而精致，整体造型流线优美，完美的与美酒结合。",false));
                lists.add(new ZhiJxItem(R.drawable.ht_home_img_two,"KIKO双色烘焙眼影","kiko 16最新款，干湿两用双色烘焙眼影，令眼部妆容明亮、浓郁，色彩可调节。为了获得整体引人注目",false));
                lists.add(new ZhiJxItem(R.drawable.ht_home_img_three,"Miele 滚轴式蒸汽熨烫机","这款来自德国的Miele滚轴式蒸汽熨烫机，全开放式滚轴轻松熨烫衣物。双冰箱技术能快速提高",false));
            }
            LogUtil.d("lists size:" + lists.size());
            updateUi(action,lists);
        }else{
            updateUi(ACTION_NO_NET,null);
        }
    }

    @UiThread
    void updateUi(int action,List<ZhiJxItem> zhiJxItems) {
        switch (action){
            case ACTION_DOWNLOAD:
                adapter = new ZhiAdapter(context,lists);
                rv.setAdapter(adapter);
                srl.setRefreshing(false);
                break;
            case ACTION_REFRESH:
                LogUtil.d("zhiJxItems size:" + zhiJxItems.size());
                adapter.initData(zhiJxItems);
                srl.setRefreshing(false);
                break;
            case ACTION_PULL_UP:
                adapter.addData(zhiJxItems);
                break;
            case ACTION_NO_NET:
                Toast.makeText(context, getResources().getString(R.string.word_no_net), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
