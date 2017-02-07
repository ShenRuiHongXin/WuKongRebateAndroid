package com.shenrui.wukongrebate.fragment;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.FoodQuestionView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;


/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.food_fragment_page)
public class FragmentFood extends BaseFragment {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;
    //类目
    @ViewById(R.id.tl_food)
    TabLayout tlFood;
    //轮播图
    @ViewById(R.id.crv_food)
    CycleRotationView crvFood;

    @ViewsById({R.id.fqv_food_question,R.id.fqv_food_question1,R.id.fqv_food_question2})
    List fqvFoodQuestion;

    private String[] cats = {"美食攻略","精选美食","美食活动"};

    @AfterViews
    void init(){
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.index_btn_city_n);
        ((TextView)listTitleView.get(2)).setText("美食馆");
        listTitleView.get(3).setVisibility(View.GONE);
        LogUtil.i("FragmentFood created");

        //类目
        for (String str : cats){
            TabLayout.Tab tab = tlFood.newTab();
            tab.setText(str);
            tlFood.addTab(tab);
        }

        //轮播图
        int[] imgs = {R.drawable.food_banner1,R.drawable.food_banner2,R.drawable.index_banner1,R.drawable.index_banner2};
        crvFood.setImages(imgs);
//        crvFood.setVisibility(View.GONE);

        for (Object v : fqvFoodQuestion){
            ((FoodQuestionView)v).setTvFoodQuestion();
        }
    }

}
