package com.shenrui.wukongrebate.fragment.food;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.FoodArticleActivity_;
import com.shenrui.wukongrebate.activity.FoodMenuActivity_;
import com.shenrui.wukongrebate.activity.FoodVideoActivity_;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.entities.FoodFragmentBtnItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 美食攻略
 */
@EFragment(R.layout.fragment_fragment_food_gl)
public class FragmentFoodGL extends Fragment {
    //精彩推送
    @ViewById(R.id.rv_food_gl_recommend)
    RecyclerView rvRecommend;

    LinearLayoutManager layoutManager;
    //内容
    List foodDatas;
    //轮播
    int[] headerDatas;
    //按钮
    List btnDatas;

    Context context;
    FoodAdapter foodAdapter;

    @AfterViews
    void init(){
        context = getContext();
        initView();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rvRecommend.setLayoutManager(layoutManager);
        initData();
        foodAdapter = new FoodAdapter(context,foodDatas);
        foodAdapter.setHeadDatas(headerDatas);
        foodAdapter.setBtnDatas(btnDatas);
        foodAdapter.setHasHeader(true);
        rvRecommend.setAdapter(foodAdapter);
    }

    private void initData(){
        foodDatas = new ArrayList();
        foodDatas.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,"营养齐全的【Cobb Salad】",R.drawable.food_gl_home_img_tmp));
        foodDatas.add(new FoodContentItem(FoodAdapter.TYPE_VIDEO,"食汇寿司——在家也可以做",R.drawable.food_gl_video_img_tmp));
        headerDatas = new int[]{R.drawable.banner,R.drawable.banner,R.drawable.banner};
        btnDatas = new ArrayList();
        btnDatas.add(new FoodFragmentBtnItem(R.drawable.btn_caipu,"菜谱", FoodMenuActivity_.class));
        btnDatas.add(new FoodFragmentBtnItem(R.drawable.btn_shipin,"视频", FoodVideoActivity_.class));
        btnDatas.add(new FoodFragmentBtnItem(R.drawable.btn_zhoukan,"周刊", FoodArticleActivity_.class));
    }



}
