package com.shenrui.wukongrebate.activity;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.view.FoodVideoView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by heikki on 2017/2/7.
 */

@EActivity(R.layout.activity_food_video)
public class FoodVideoActivity extends BaseActivity {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;
    //类目
    @ViewById(R.id.tl_food_video)
    TabLayout tlFoodVideoCats;

    @ViewsById({R.id.fvv_food_video_1,R.id.fvv_food_video_2,R.id.fvv_food_video_3,R.id.fvv_food_video_4})
    List fvvList;

    String[] cats = {"全部","本周热门","家常菜","烘焙类","热门菜","湘菜"};

    @AfterViews
    void init() {
        //标题栏
        listTitleView.get(3).setVisibility(View.GONE);
        ((TextView) listTitleView.get(2)).setText("美食视频");
        ((ImageView) listTitleView.get(1)).setImageResource(R.drawable.nav_icon_back);
        listTitleView.get(0).setVisibility(View.GONE);

        //类目
        tlFoodVideoCats.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (String str : cats){
            TabLayout.Tab tab = tlFoodVideoCats.newTab();
            tab.setText(str);
            tlFoodVideoCats.addTab(tab);
        }

        for (Object fvv : fvvList){
            ((FoodVideoView)fvv).setMoreVisibility(View.GONE);
            ((FoodVideoView)fvv).setFoodVideoTitle("传菜作者");
            ((FoodVideoView)fvv).setFoodVideoSynopsis("菜品名称+简单介绍简单介绍简单介绍简单介绍");
        }
    }

    @Click(R.id.toolbar_left_image)
    void clickEvent(){
        finish();
    }
}
