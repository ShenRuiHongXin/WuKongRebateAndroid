package com.shenrui.wukongrebate.fragment;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.ActivityRecyAdapter;
import com.shenrui.wukongrebate.adapter.MyGridAdapter;
import com.shenrui.wukongrebate.adapter.RecyTenNewGoodsAdapter;
import com.shenrui.wukongrebate.view.ActivityView;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.MyGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.button;

/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.rebate_fragment_page)
public class FragmentRebate extends Fragment {

    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;
    //商品分类
    @ViewById(R.id.tl_goods_category)
    TabLayout tl_googs_category;
    //广告轮播栏
    @ViewById(R.id.cyclerotationview)
    CycleRotationView cycleRotationView;
    //签到
    @ViewById(R.id.mgv_sign)
    MyGridView myGridView;
    //活动
    @ViewById(R.id.activity_view)
    ActivityView activityView;
    //10点早上新
    @ViewById(R.id.ten_new_goods_recy)
    RecyclerView rv_ten_new;

    @AfterViews
    void init(){
        //标题栏
        listTitleView.get(3).setVisibility(View.GONE);
        ((TextView)listTitleView.get(2)).setText("悟空返利");
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.index_btn_city_n);

        //商品分类
        tl_googs_category.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(int i = 0;i<10;i++){
            tl_googs_category.addTab(tl_googs_category.newTab().setText("类目"+i));
        }

        //广告轮播栏
        String[] urls = {"http://p1.so.qhmsg.com/t01514641c357a98c81.jpg", "http://p4.so.qhmsg.com/t01244e62a3f44edf24.jpg", "http://p4.so.qhmsg.com/t01f017b2c06cc1124e.jpg"};
        cycleRotationView.setUrls(urls);
            // 点击事件
        cycleRotationView.setOnItemClickListener(new CycleRotationView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Click = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        //签到
        myGridView.setAdapter(new MyGridAdapter(getActivity()));

        //活动
        List<String> listTitle   = new ArrayList<String>();
        List<String> listContent = new ArrayList<String>();
        List<Integer> listIcon = new ArrayList<Integer>();
        listTitle.add("活动主题1");
        listTitle.add("热销品牌1");
        listTitle.add("标题1");
        listTitle.add("标题2");
        listContent.add("活动简介内容1");
        listContent.add("");
        listContent.add("宣传内容1");
        listContent.add("宣传内容2");
        listIcon.add(R.drawable.index_img_activity_n);
        listIcon.add(R.drawable.index_img_activity1_n);
        listIcon.add(R.drawable.index_img_activity2_n);
        listIcon.add(R.drawable.index_img_activity2_n);


        //10点早上新
        int spanCount = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_ten_new.setLayoutManager(gridLayoutManager);
        List listGoods = new ArrayList();
        for (int i = 0 ;i<10; i++) {
            listGoods.add(""+i);
        }
        rv_ten_new.setAdapter(new RecyTenNewGoodsAdapter(getActivity(),listGoods));
    }
}
