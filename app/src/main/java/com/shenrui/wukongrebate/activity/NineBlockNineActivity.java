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
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.CatsItemLocal;
import com.shenrui.wukongrebate.fragment.FragmentNineAll;
import com.shenrui.wukongrebate.fragment.FragmentNineItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.MyGridView;

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
    @ViewById(R.id.iv_expand_nine)
    ImageView ivExpand;

    List<String> titles;//分类标签
    PopupWindow pop;//分类窗口
    boolean isExpand = false;//分类窗是否展开

    @AfterViews
    void init(){
        getWindow().setBackgroundDrawable(null);
        initTabs();
    }

    private void initTabs() {
        //分类栏
        for(CatsItemLocal cats: Constants.ItemNineCats){
            TabLayout.Tab tab = tabs.newTab();
            tab.setText(cats.getName());
            tab.setTag(cats);
            tabs.addTab(tab);
        }
        List<Fragment> fragments = new ArrayList<>();
        titles = new ArrayList<>();
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
        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager(),fragments,titles);
        vp.setAdapter(adapter);
        tabs.setupWithViewPager(vp);
    }

    //弹出分类窗
    private void initPop(){
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

    //切换分类箭头方向
    private void checkExpandStatus() {
        if (isExpand){
            ivExpand.setImageResource(R.drawable.nav_icon_backup);
        }else{
            ivExpand.setImageResource(R.drawable.nav_icon_backdown);
        }
    }

    @Click({R.id.iv_nine_back,R.id.iv_nine_find,R.id.iv_expand_nine})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_nine_back:
                MFGT.finish(this);
                break;
            case R.id.iv_nine_find:
                MFGT.startActivity(this,NineSearchActivity_.class);
                break;
            case R.id.iv_expand_nine:
                if(pop == null){
                    initPop();
                }else{
                    if(isExpand){
                        pop.dismiss();
                    }else{
                        pop.showAsDropDown(tabs,0,0);
                    }
                }
                isExpand = !isExpand;
                checkExpandStatus();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
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
                holder.tv.setBackgroundResource(R.drawable.category_textview_selected_green);
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
