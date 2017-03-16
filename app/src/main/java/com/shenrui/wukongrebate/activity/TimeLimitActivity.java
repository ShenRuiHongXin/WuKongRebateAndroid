package com.shenrui.wukongrebate.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.fragment.timelimit.TimeOneFragment_;
import com.shenrui.wukongrebate.fragment.timelimit.TimeThreeFragment_;
import com.shenrui.wukongrebate.fragment.timelimit.TimeTwoFragment_;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_time_limit)
public class TimeLimitActivity extends BaseActivity {
    @ViewById(R.id.tabs_time_limit)
    TabLayout tabs;
    @ViewById(R.id.vp_time_limit)
    ViewPager vp;

    List<Fragment> fragments;

    @AfterViews
    void init(){
        getWindow().setBackgroundDrawable(null);
        initTabs();
        setListener();
    }

    @Click(R.id.iv_time_limit_back)
    void clickEvent(View view){
        MFGT.finish(this);
    }

    private void setListener() {
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv_time)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) tab.getCustomView().findViewById(R.id.tv_status)).setTextColor(getResources().getColor(R.color.white));
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv_time)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) tab.getCustomView().findViewById(R.id.tv_status)).setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabs() {
        fragments = new ArrayList<>();
        fragments.add(new TimeOneFragment_());
        fragments.add(new TimeTwoFragment_());
        fragments.add(new TimeThreeFragment_());
        vp.setOffscreenPageLimit(2);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        vp.setAdapter(adapter);
        tabs.setupWithViewPager(vp);

        String[] titles = new String[]{"07:00-10:00","11:00-14:00","15:00-18:00"};
        for(int i=0;i<adapter.getCount();i++){
            TabLayout.Tab tab = tabs.getTabAt(i);
            tab.setCustomView(R.layout.layout_tab_time_item);
            TextView tvTime = (TextView) tab.getCustomView().findViewById(R.id.tv_time);
            TextView tvStatus = (TextView) tab.getCustomView().findViewById(R.id.tv_status);
            if (i == 0) {
                //设置第一个tab的TextView是被选择的样式
                tvTime.setTextColor(getResources().getColor(R.color.white));
                tvStatus.setTextColor(getResources().getColor(R.color.white));
            }
            tvTime.setText(titles[i]);//设置tab上的文字
        }
    }

    class MyFragmentAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragments;
        MyFragmentAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.fragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
