package com.shenrui.wukongrebate.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MyPageAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    private List<String> titles;
    public MyPageAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
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

    @Override
    public void destroyItem(View container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

}
