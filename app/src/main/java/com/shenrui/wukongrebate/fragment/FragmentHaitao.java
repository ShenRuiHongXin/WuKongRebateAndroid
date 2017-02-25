package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.fragment.haitao.FragmentRecommend;
import com.shenrui.wukongrebate.fragment.haitao.FragmentSelected;
import com.shenrui.wukongrebate.view.MyGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.haitao_fragment_page)
public class FragmentHaitao extends BaseFragment {
    Context context;
    @ViewById(R.id.tabs_haitao)
    TabLayout tabs;
    @ViewById(R.id.vp_haitao)
    ViewPager vp;
    @ViewById(R.id.iv_expand)
    ImageView ivExpand;
    @ViewById(R.id.iv_find)
    ImageView ivFind;
    String[] titles;
    List<Fragment> fragments;
    PopupWindow pop;
    CategoryAdapter adapter;
    boolean isExpand = false;//分类pop窗是否展开
    @AfterViews
    void init(){
        context = getContext();
        initTabs();
    }

    @Click({R.id.iv_find,R.id.iv_expand})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_find:

                break;
            case R.id.iv_expand:
                if (pop == null){
                    initPop();
                }else{
                    if(!isExpand){
                        pop.showAsDropDown(tabs,0,0);
                    }else{
                        pop.dismiss();
                    }
                }
                isExpand = !isExpand;
                checkExpandStatus();
                break;
        }
    }

    //弹出分类项的pop窗
    private void initPop() {
        View layout = View.inflate(context, R.layout.layout_category, null);
        MyGridView gridView = (MyGridView) layout.findViewById(R.id.gridView_category);
        View outView = layout.findViewById(R.id.outView);
        adapter = new CategoryAdapter(titles);
        gridView.setAdapter(adapter);
        //分类标签项点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (vp.getCurrentItem()!=position){
                    vp.setCurrentItem(position);
                    //切换item背景和字体颜色
                    adapter.notifyDataSetInvalidated();
                    if(pop!=null){
                        pop.dismiss();
                        isExpand = !isExpand;
                        checkExpandStatus();
                    }
                }
            }
        });
        outView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop!=null){
                    pop.dismiss();
                    isExpand = !isExpand;
                    checkExpandStatus();
                }
            }
        });
        pop = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置点击外面pop窗销毁
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAsDropDown(tabs,0,0);
    }

    //切换分类箭头方向
    private void checkExpandStatus() {
        if (isExpand){
            ivExpand.setImageResource(R.drawable.nav_icon_back);
        }else{
            ivExpand.setImageResource(R.drawable.common_btn_down_n);
        }
    }
    private void initTabs() {
        titles = new String[]{"悟空推荐","母婴","美妆","箱包","美食保健","饰品","家电家居","直邮数码"};
        for(int i=0;i<titles.length;i++){
            tabs.addTab(tabs.newTab().setText(titles[i]));
        }
        fragments = new ArrayList<>();
        fragments.add(new FragmentRecommend());
        for(int i=0;i<7;i++){
            Bundle bundle = new Bundle();
            bundle.putString("title",titles[i+1]);
            FragmentSelected fragment = new FragmentSelected();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        vp.setAdapter(new PageAdatper(getFragmentManager()));
        tabs.setupWithViewPager(vp);
    }

    class PageAdatper extends FragmentPagerAdapter{

        public PageAdatper(FragmentManager fm) {
            super(fm);
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
            return titles[position];
        }
    }

    //分类pop窗的适配器
    class CategoryAdapter extends BaseAdapter{
        String[] texts;
        public CategoryAdapter(String[] texts) {
            this.texts = texts;
        }

        @Override
        public int getCount() {
            return texts.length;
        }

        @Override
        public Object getItem(int position) {
            return texts[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView == null){
                holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_category_item,null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_category);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            holder.tv.setText(texts[position]);
            if(vp.getCurrentItem() == position){
                holder.tv.setBackgroundResource(R.drawable.category_textview_selected);
                holder.tv.setTextColor(getResources().getColor(R.color.white));
            }else{
                holder.tv.setBackgroundResource(R.drawable.category_textview);
                holder.tv.setTextColor(getResources().getColor(R.color.black));
            }
            return convertView;
        }

        class Holder{
            TextView tv;
        }
    }
}
