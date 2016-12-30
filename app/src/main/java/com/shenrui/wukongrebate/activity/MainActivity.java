package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MainViewPagerAdapter;
import com.shenrui.wukongrebate.fragment.FragmentCircle;
import com.shenrui.wukongrebate.fragment.FragmentCircle_;
import com.shenrui.wukongrebate.fragment.FragmentFood;
import com.shenrui.wukongrebate.fragment.FragmentFood_;
import com.shenrui.wukongrebate.fragment.FragmentHaitao;
import com.shenrui.wukongrebate.fragment.FragmentHaitao_;
import com.shenrui.wukongrebate.fragment.FragmentMine;
import com.shenrui.wukongrebate.fragment.FragmentRebate;
import com.shenrui.wukongrebate.fragment.FragmentRebate_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    //git dev
//    @ViewById(R.id.toolbar)
//    Toolbar mToolbar;

    @ViewById(R.id.vp_content)
    ViewPager vp_content;

    @ViewsById({R.id.ll_rebate, R.id.ll_food, R.id.ll_circle, R.id.ll_haitao, R.id.ll_mine})
    List ll_list;

    @ViewsById({R.id.iv_rebate, R.id.iv_food, R.id.iv_circle, R.id.iv_haitao, R.id.iv_mine})
    List iv_list;

    @ViewsById({R.id.tv_rebate, R.id.tv_food, R.id.tv_circle, R.id.tv_haitao, R.id.tv_mine})
    List tv_list;

    private List<View> views;
    private List<Fragment> fragmentList;

    // ViewPager适配器MainViewPagerAdapter
    private MainViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
//        mToolbar.setTitle("");
//        setSupportActionBar(mToolbar);

        vp_content.addOnPageChangeListener(this);

        fragmentList   = new ArrayList<Fragment>();

        fragmentList.add(new FragmentRebate_());
        fragmentList.add(new FragmentFood_());
        fragmentList.add(new FragmentCircle_());
        fragmentList.add(new FragmentHaitao_());
        fragmentList.add(new FragmentMine());

        this.adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp_content.setAdapter(adapter);
    }

    @Click({R.id.ll_rebate, R.id.ll_food, R.id.ll_circle, R.id.ll_haitao, R.id.ll_mine})
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
            case R.id.ll_circle:
                tmpPosition = 2;
                break;
            case R.id.ll_haitao:
                tmpPosition = 3;
                break;
            case R.id.ll_mine:
                tmpPosition = 4;
                break;
        }
        selectPage(tmpPosition);
    }

    //重置界面
    private void resetView() {
        //
        ((ImageView)iv_list.get(0)).setImageResource(R.drawable.common_icon_hot_n);
        ((ImageView)iv_list.get(1)).setImageResource(R.drawable.common_icon_cate_n);
        ((ImageView)iv_list.get(2)).setImageResource(R.drawable.common_icon_health_n);
        ((ImageView)iv_list.get(3)).setImageResource(R.drawable.rebate);
        ((ImageView)iv_list.get(4)).setImageResource(R.drawable.common_icon_monkey_n);
        //
        ((TextView)tv_list.get(0)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        ((TextView)tv_list.get(1)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        ((TextView)tv_list.get(2)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        ((TextView)tv_list.get(3)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        ((TextView)tv_list.get(4)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
    }

    //设置选中页
    private void selectPage(int position){
        switch (position) {
            case 0:
                ((ImageView)iv_list.get(0)).setImageResource(R.drawable.common_icon_hot_s);
                ((TextView)tv_list.get(0)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(0);
                break;
            case 1:
                ((ImageView)iv_list.get(1)).setImageResource(R.drawable.common_icon_cate_s);
                ((TextView)tv_list.get(1)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(1);
                break;
            case 2:
                ((ImageView)iv_list.get(2)).setImageResource(R.drawable.common_icon_health_s);
                ((TextView)tv_list.get(2)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(2);
                break;
            case 3:
                ((ImageView)iv_list.get(3)).setImageResource(R.drawable.rebate_s);
                ((TextView)tv_list.get(3)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(3);
                break;
            case 4:
                ((ImageView)iv_list.get(4)).setImageResource(R.drawable.common_icon_monkey_s);
                ((TextView)tv_list.get(4)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
                vp_content.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetView();
        //
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
