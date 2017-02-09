package com.shenrui.wukongrebate.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.CityFoodActivity_;
import com.shenrui.wukongrebate.activity.FoodArticleActivity_;
import com.shenrui.wukongrebate.activity.FoodMarketActivity_;
import com.shenrui.wukongrebate.activity.FoodVideoActivity_;
import com.shenrui.wukongrebate.activity.WkMenuActivity_;
import com.shenrui.wukongrebate.activity.WordFoodActivity_;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.FoodArticleView;
import com.shenrui.wukongrebate.view.FoodCatsView;
import com.shenrui.wukongrebate.view.FoodQuestionView;
import com.shenrui.wukongrebate.view.FoodVideoView;

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
    //美食问答
    @ViewsById({R.id.fqv_food_question,R.id.fqv_food_question1,R.id.fqv_food_question2})
    List fqvFoodQuestion;
    //悟空菜单
    @ViewById(R.id.fcv_food_cats)
    FoodCatsView fcvFoodCats;
    //美食周刊
    @ViewById(R.id.fav_food_article)
    FoodArticleView favFoodArticle;
    //美食视频
    @ViewById(R.id.fvv_food_video)
    FoodVideoView fvvFoodVideo;

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

        //悟空菜单
        fcvFoodCats.setWkMenuOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), WkMenuActivity_.class));
            }
        });
        fcvFoodCats.setCityFoodOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CityFoodActivity_.class));
            }
        });
        fcvFoodCats.setWordFoodOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), WordFoodActivity_.class));
            }
        });
        fcvFoodCats.setFoodmarketOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), FoodMarketActivity_.class));
            }
        });

        //美食问答
        for (Object v : fqvFoodQuestion){
            ((FoodQuestionView)v).setTvFoodQuestion();
        }

        //美食周刊
        favFoodArticle.setMoreOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), FoodArticleActivity_.class));
            }
        });

        //美食视频
        fvvFoodVideo.setMoreOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), FoodVideoActivity_.class));
            }
        });
    }

}
