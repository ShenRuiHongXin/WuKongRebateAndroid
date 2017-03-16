package com.shenrui.wukongrebate.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MyPageAdapter;
import com.shenrui.wukongrebate.fragment.zhi.BKFragment;
import com.shenrui.wukongrebate.fragment.zhi.BKFragment_;
import com.shenrui.wukongrebate.fragment.zhi.HTFragment_;
import com.shenrui.wukongrebate.fragment.zhi.JXFragment_;
import com.shenrui.wukongrebate.fragment.zhi.SCFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

@EFragment(R.layout.zhi_fragment_page)
public class FragmentZhi extends BaseFragment {
    @ViewById(R.id.zhi_iv_search)
    ImageView iv_search;
    @ViewById(R.id.zhi_tabs)
    TabLayout tabs;
    @ViewById(R.id.zhi_vp)
    ViewPager vp;

    @AfterViews
    void init(){
        initTabs();
    }

    private void initTabs() {
        String[] titles = {"精选","尚潮","百科","海淘"};
        List<String> list = Arrays.asList(titles);
        for(String str:titles){
            tabs.addTab(tabs.newTab().setText(str));
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new JXFragment_());
        fragments.add(new SCFragment_());
        fragments.add(new BKFragment_());
        fragments.add(new HTFragment_());
        //设置缓存页数，不销毁之前的状态
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(new MyPageAdapter(getFragmentManager(),fragments,list));
        tabs.setupWithViewPager(vp);

    }
}
