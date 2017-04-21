package com.shenrui.wukongrebate.activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MyPageAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.fragment.FragmentTimeLimit;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_time_limit)
public class TimeLimitActivity extends BaseActivity {
    @ViewById(R.id.tabs_time_limit)
    TabLayout tabs;
    @ViewById(R.id.vp_time_one_limit)
    ViewPager vpo;
    @ViewById(R.id.tv_time_counting)
    TextView tvTimeLeft;

    private List<TbkItem> likeGoods;
    String[] titles;
    List<Fragment> fragments;
    private int curHour;
    Handler handler;

    @AfterViews
    void init(){
        getWindow().setBackgroundDrawable(null);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                timeCounting();
                sendEmptyMessageDelayed(1,1000);
            }
        };
        getLikeGoodsData();
    }

    @UiThread
    void initView(String goodsList){
        titles = new String[]{"10:00","20:00"};
        fragments = new ArrayList<>();
        fragments.add(FragmentTimeLimit.newInstance(10,goodsList));
        fragments.add(FragmentTimeLimit.newInstance(20,goodsList));
        for (int i=0;i<titles.length;i++){
            tabs.addTab(tabs.newTab().setText(titles[i]));
        }
        vpo.setAdapter(new MyPageAdapter(getSupportFragmentManager(),fragments, Arrays.asList(titles)));
        tabs.setupWithViewPager(vpo);

        tabs.getTabAt(curHour < 20 ? 0 : 1).select();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvTimeLeft.getLayoutParams();
                if (tabs.getSelectedTabPosition() == 0){
                    params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    tvTimeLeft.setVisibility(curHour >= 10 ? View.VISIBLE : View.GONE);
                }else{
                    params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    tvTimeLeft.setVisibility(curHour >= 20 ? View.VISIBLE : View.GONE);
                }
                tvTimeLeft.setLayoutParams(params);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        handler.sendEmptyMessage(curHour);
    }

    void timeCounting(){
        //获取当前时间
        SimpleDateFormat formatHour = new SimpleDateFormat("HH");
        SimpleDateFormat formatMins = new SimpleDateFormat("mm");
        SimpleDateFormat formatSeco = new SimpleDateFormat("ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String hour = formatHour.format(curDate);
        String mins = formatMins.format(curDate);
        String seco = formatSeco.format(curDate);
        curHour = Integer.parseInt(hour);
        subTimeLeft(curHour,Integer.parseInt(mins),Integer.parseInt(seco));
    }

    void subTimeLeft(int curHour,int curMin,int curSec) {
        int secLeft = 0;
        int minLeft = 0;
        int hourLeft = 0;
        if (curSec != 0 && curMin != 0){
            secLeft = 60 - curSec;
            minLeft = 59 - curMin;
            hourLeft = 23 - curHour;
        }else if(curSec == 0 && curMin != 0){
            minLeft = 60 - curMin;
            hourLeft = 23 - curHour;
        }else if(curSec != 0 && curMin == 0){
            secLeft = 60 - curSec;
            minLeft = 59;
            hourLeft = 23 - curHour;
        }else{
            hourLeft = 24 - curHour;
        }
        tvTimeLeft.setVisibility(((curHour >= 10 && tabs.getSelectedTabPosition() == 0) || (curHour >= 20 && tabs.getSelectedTabPosition() == 1)) ? View.VISIBLE : View.GONE);
        tvTimeLeft.setText(String.format(getResources().getString(R.string.time_left),translateTime(hourLeft),translateTime(minLeft),translateTime(secLeft)));
    }

    String translateTime(int time){
        return time >= 10 ? String.valueOf(time) : "0"+time;
    }

    @Click({R.id.iv_time_limit_back,R.id.iv_time_limit_toggle})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_time_limit_back:
                MFGT.finish(this);
                break;
            case R.id.iv_time_limit_toggle:
                LogUtil.d("当前页面:" + vpo.getCurrentItem());
                ((FragmentTimeLimit)fragments.get(vpo.getCurrentItem())).changeLayout();
                break;
        }
    }


    //猜你喜欢
    @Background
    void getLikeGoodsData(){
        Map<String, Object> result = GetNetWorkDatas.getSearchGoods("口红", 1,10);
        likeGoods = (List<TbkItem>) result.get("goodsList");
        initView(new Gson().toJson(likeGoods));
//        initLikeGoodsView();
    }
//    @UiThread
//    void initLikeGoodsView(){
//        GridLayoutManager layoutManager;
//        layoutManager = new GridLayoutManager(TimeLimitActivity.this, 2){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//
//        rcvLick.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                if (parent.getChildPosition(view)!=0){
//                    if (parent.getChildPosition(view)%2!=0){
//                        outRect.set(4,0,3,4);
//                    }else{
//                        outRect.set(3,0,4,4);
//                    }
//                }
//            }
//        });
//        BaseGoodsAdapter adapter = new BaseGoodsAdapter(TimeLimitActivity.this,likeGoods);
//        rcvLick.setAdapter(adapter);
//        rcvLick.setLayoutManager(layoutManager);
//    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
