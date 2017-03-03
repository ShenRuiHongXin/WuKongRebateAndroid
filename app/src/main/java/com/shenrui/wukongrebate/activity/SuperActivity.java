package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.shenrui.wukongrebate.adapter.MyPageAdapter;
import com.shenrui.wukongrebate.fragment.supers.FragmentSuperAll_;
import com.shenrui.wukongrebate.fragment.supers.FragmentSuperItem;
import com.shenrui.wukongrebate.fragment.supers.FragmentSuperItem_;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.MyGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EActivity(R.layout.activity_super)
public class SuperActivity extends BaseActivity{
    @ViewById(R.id.tv_super_title)
    TextView tvTitle;
    @ViewById(R.id.tv_super_new_search)
    TextView tvSearch;
    @ViewById(R.id.tabs_super)
    TabLayout tabs;
    @ViewById(R.id.iv_expand_super)
    ImageView ivExpand;
    @ViewById(R.id.vp_super)
    ViewPager vp;

    List<String> titles;
    List<Fragment> fragments;
    PopupWindow pop;
    boolean isExpand = false;

    @AfterViews
    void init(){
        initTabs();
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkSearchStatus();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabs() {
        String[] texts = {"今日上新","女装","男装","食品","美妆","居家","内衣","运动"};
        titles = Arrays.asList(texts);
        fragments = new ArrayList<>();
        fragments.add(new FragmentSuperAll_());
        for(int i=0;i<texts.length-1;i++){
            FragmentSuperItem item = new FragmentSuperItem_();
            Bundle bundle = new Bundle();
            bundle.putString("title",titles.get(i+1));
            item.setArguments(bundle);
            fragments.add(item);
        }
        MyPageAdapter adapter = new MyPageAdapter(this.getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(adapter);
        tabs.setupWithViewPager(vp);
    }

    @Click({R.id.iv_super_back,R.id.iv_expand_super,R.id.tv_super_new_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_super_back:
                MFGT.finish(this);
                break;
            case R.id.iv_expand_super:
                if (pop == null){
                    initPop();
                }else{
                    if (!isExpand){
                        pop.showAsDropDown(tabs,0,0);
                    }else{
                        pop.dismiss();
                    }
                }
                isExpand = !isExpand;
                checkExpandStatus();
                break;
            case R.id.tv_super_new_search:
                MFGT.startActivity(this,SuperSearchActivity_.class);
                break;
        }
    }

    private void initPop() {
        View layout = View.inflate(this, R.layout.layout_category, null);
        MyGridView gridView = (MyGridView) layout.findViewById(R.id.gridView_category);
        View outView = layout.findViewById(R.id.outView);
        final CategoryAdapter adapter = new CategoryAdapter(this,titles);
        gridView.setAdapter(adapter);
        pop = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //分类标签项点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (vp.getCurrentItem()!=position){
                    vp.setCurrentItem(position);
                    checkSearchStatus();
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
        //设置点击外面pop窗销毁
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAsDropDown(tabs,0,0);
    }

    //显示/隐藏搜索栏
    private void checkSearchStatus(){
        int currentItem = vp.getCurrentItem();
        if (currentItem == 0){
            tvTitle.setVisibility(View.VISIBLE);
            tvSearch.setVisibility(View.GONE);
        }else{
            tvTitle.setVisibility(View.GONE);
            tvSearch.setVisibility(View.VISIBLE);
        }
    }

    //切换分类箭头方向
    private void checkExpandStatus() {
        if (isExpand){
            ivExpand.setImageResource(R.drawable.nav_icon_backup);
        }else{
            ivExpand.setImageResource(R.drawable.nav_icon_backdown);
        }
    }

    //分类pop窗的适配器
    class CategoryAdapter extends BaseAdapter {
        Context context;
        List<String> texts;

        public CategoryAdapter(Context context, List<String> texts) {
            this.context = context;
            this.texts = texts;
        }

        @Override
        public int getCount() {
            return texts.size();
        }

        @Override
        public Object getItem(int position) {
            return texts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CategoryAdapter.Holder holder;
            if(convertView == null){
                holder = new CategoryAdapter.Holder();
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_category_item,null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_category);
                convertView.setTag(holder);
            }else{
                holder = (CategoryAdapter.Holder) convertView.getTag();
            }
            holder.tv.setText(texts.get(position));
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

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
