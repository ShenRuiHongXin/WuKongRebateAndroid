package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MyPageAdapter;
import com.shenrui.wukongrebate.fragment.duo.DuoItemFragment;
import com.shenrui.wukongrebate.fragment.duo.DuoItemFragment_;
import com.shenrui.wukongrebate.fragment.duo.RenQiFragment_;
import com.shenrui.wukongrebate.utils.MFGT;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EActivity(R.layout.activity_duo_bao)
public class DuoBaoActivity extends BaseActivity {
    @ViewById(R.id.magic_indicator)
    MagicIndicator magic;
    @ViewById(R.id.duo_vp)
    ViewPager vp;
    Context context;

    @AfterViews
    void init(){
        context = this;
        initTabs();
    }

    private void initTabs() {
        final String[] titles = {"人气","最新","最快","价格"};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RenQiFragment_());
        for(int i=1;i<titles.length;i++){
            DuoItemFragment fragment = new DuoItemFragment_();
            Bundle bundle = new Bundle();
            bundle.putString("title",titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        vp.setOffscreenPageLimit(3);
        MyPageAdapter adapter = new MyPageAdapter(this.getSupportFragmentManager(), fragments, Arrays.asList(titles));
        vp.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.length;
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView titleView = new ColorTransitionPagerTitleView(context);
                titleView.setNormalColor(Color.GRAY);
                titleView.setSelectedColor(Color.RED);
                titleView.setText(titles[index]);
                titleView.setTextSize(15);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(index);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(45);
                indicator.setLineHeight(5);
                indicator.setRoundRadius(10);
                indicator.setColors(Color.RED);
                return indicator;
            }
        });
        magic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic,vp);
    }

    @Click(R.id.iv_duo_back)
    void clickEvent(View view){
        MFGT.finish(this);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
