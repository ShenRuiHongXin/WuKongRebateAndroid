package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MyPageAdapter;
import com.shenrui.wukongrebate.fragment.food.FragmentFoodGL_;
import com.shenrui.wukongrebate.fragment.food.FragmentFoodJ_;
import com.shenrui.wukongrebate.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EFragment(R.layout.food_fragment_page)
public class FragmentFood extends BaseFragment {
    @ViewById(R.id.food_tabs)
    TabLayout tabs;
    @ViewById(R.id.food_vp)
    ViewPager vp;

    Context context;
    String[] titles;
    List<Fragment> fragments;

    @AfterViews
    void init(){
        context = getContext();
        initTabs();
    }

    private void initTabs() {
        LogUtil.d("FragmentFood created!");
        titles = new String[]{"美食攻略","美食记"};
        fragments = new ArrayList<>();
        fragments.add(new FragmentFoodGL_());
        fragments.add(new FragmentFoodJ_());
        for (int i=0;i<titles.length;i++){
            tabs.addTab(tabs.newTab().setText(titles[i]));
        }
        vp.setAdapter(new MyPageAdapter(getFragmentManager(),fragments, Arrays.asList(titles)));
        tabs.setupWithViewPager(vp);
    }
}
