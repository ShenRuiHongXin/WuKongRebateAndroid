package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MainViewPagerAdapter;
import com.shenrui.wukongrebate.fragment.FragmentFood_;
import com.shenrui.wukongrebate.fragment.FragmentHaitao_;
import com.shenrui.wukongrebate.fragment.FragmentMine;
import com.shenrui.wukongrebate.fragment.FragmentRebate_;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.view.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    Context context;
    @ViewById(R.id.vp_content)
    NoScrollViewPager vp_content;

    @ViewsById({R.id.ll_rebate, R.id.ll_food, R.id.ll_haitao, R.id.ll_mine})
    List ll_list;

    @ViewsById({R.id.iv_rebate, R.id.iv_food, R.id.iv_haitao, R.id.iv_mine})
    List iv_list;

    @ViewsById({R.id.tv_rebate, R.id.tv_food, R.id.tv_haitao, R.id.tv_mine})
    List tv_list;

    private List<View> views;
    private List<Fragment> fragmentList;

    // ViewPager适配器MainViewPagerAdapter
    private MainViewPagerAdapter adapter;

    public static List mainData = null;

    private void addStatusBarView() {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));
        ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);
        decorView.addView(view, params);
    }

    public int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addStatusBarView();
        context = this;
    }

    @AfterViews
    void init() {
        LogUtil.i("Mainactivity oncreate");

        vp_content.addOnPageChangeListener(this);

        fragmentList   = new ArrayList<Fragment>();

        fragmentList.add(new FragmentRebate_());
        fragmentList.add(new FragmentFood_());
        fragmentList.add(new FragmentHaitao_());
        fragmentList.add(new FragmentMine());

        this.adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp_content.setAdapter(adapter);
    }

    @Click({R.id.ll_rebate, R.id.ll_food, R.id.ll_haitao, R.id.ll_mine})
    void clickEvent(View view) {
        resetView();
        int tmpPosition = 0;
        switch (view.getId()) {
            case R.id.ll_rebate:
                tmpPosition = 0;
                break;
            case R.id.ll_food:
                tmpPosition = 1;
                break;
            case R.id.ll_haitao:
                tmpPosition = 2;
                break;
            case R.id.ll_mine:
                tmpPosition = 3;
                break;
        }
        selectPage(tmpPosition);
    }

    //重置界面
    private void resetView() {
        ((ImageView)iv_list.get(0)).setImageResource(R.drawable.common_icon_bag_n);
        ((ImageView)iv_list.get(1)).setImageResource(R.drawable.common_icon_tropical_n);
        ((ImageView)iv_list.get(2)).setImageResource(R.drawable.common_icon_earth_n);
        ((ImageView)iv_list.get(3)).setImageResource(R.drawable.common_icon_monkey_n);

        ((TextView)tv_list.get(0)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        ((TextView)tv_list.get(1)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        ((TextView)tv_list.get(2)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        ((TextView)tv_list.get(3)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
    }

    //设置选中页
    private void selectPage(int position){
        switch (position) {
            case 0:
                ((ImageView)iv_list.get(0)).setImageResource(R.drawable.common_icon_bag_s);
                ((TextView)tv_list.get(0)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(0);
                break;
            case 1:
                ((ImageView)iv_list.get(1)).setImageResource(R.drawable.common_icon_tropical_s);
                ((TextView)tv_list.get(1)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(1);
                break;
            case 2:
                ((ImageView)iv_list.get(2)).setImageResource(R.drawable.common_icon_earth_s);
                ((TextView)tv_list.get(2)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(2);
                break;
            case 3:
                ((ImageView)iv_list.get(3)).setImageResource(R.drawable.common_icon_monkey_s);
                ((TextView)tv_list.get(3)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(3);
                break;
        }
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 1000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
