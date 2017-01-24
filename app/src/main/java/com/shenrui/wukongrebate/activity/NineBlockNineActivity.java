package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.CatsItemLocal;
import com.shenrui.wukongrebate.fragment.FragmentNineAll;
import com.shenrui.wukongrebate.fragment.FragmentNineItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_nine_block_nine)
public class NineBlockNineActivity extends BaseActivity{
    @ViewById(R.id.iv_nine_back)
    ImageView ivBack;
    @ViewById(R.id.iv_nine_find)
    ImageView ivFind;
    @ViewById(R.id.nine_tabs)
    TabLayout tabs;
    @ViewById(R.id.nine_vp)
    ViewPager vp;

    @AfterViews
    void init(){
        //分类栏
        for(CatsItemLocal cats: Constants.ItemNineCats){
            TabLayout.Tab tab = tabs.newTab();
            tab.setText(cats.getName());
            tab.setTag(cats);
            tabs.addTab(tab);
        }
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for(CatsItemLocal cats : Constants.ItemNineCats){
            Fragment fragment;
            if(cats.getName().equals("全部")){
                fragment = new FragmentNineAll();
            }else{
                fragment = new FragmentNineItem();
            }
            Bundle bundle = new Bundle();
            bundle.putIntArray("cids",cats.getCids());
            fragment.setArguments(bundle);
            fragments.add(fragment);
            titles.add(cats.getName());
        }
        MyPageAdater adater = new MyPageAdater(getSupportFragmentManager(),fragments,titles);
        vp.setAdapter(adater);
        tabs.setupWithViewPager(vp);
    }

    @Click({R.id.iv_nine_back,R.id.iv_nine_find})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_nine_back:
                finish();
                break;
            case R.id.iv_nine_find:

                break;
        }
    }

    class MyPageAdater extends FragmentPagerAdapter{
        List<Fragment> fragments;
        List<String> titles;
        public MyPageAdater(FragmentManager fm,List<Fragment> fragmentList,List<String> titleList) {
            super(fm);
            this.fragments = fragmentList;
            this.titles = titleList;
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
