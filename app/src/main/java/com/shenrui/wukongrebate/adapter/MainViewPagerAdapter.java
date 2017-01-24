package com.shenrui.wukongrebate.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shenrui.wukongrebate.utils.LogUtil;

import java.util.List;


/**
 * Created by heikki on 2016/12/28.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public MainViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentList.get(arg0);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtil.d("main position: " + position);
        if (position == 0) {
            super.destroyItem(container, position, object);
        }
    }
}
