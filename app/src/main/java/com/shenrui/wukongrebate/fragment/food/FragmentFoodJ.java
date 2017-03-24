package com.shenrui.wukongrebate.fragment.food;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.FoodJCommonActivity_;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.entities.FoodFragmentBtnItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_fragment_food_gl)
public class FragmentFoodJ extends Fragment {
    //精选推荐
    @ViewById(R.id.rv_food_gl_recommend)
    RecyclerView rvRecommend;

    LinearLayoutManager layoutManager;
    //内容
    List foodJDatas;
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

    private void initView(){
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rvRecommend.setLayoutManager(layoutManager);
        initData();
        foodAdapter = new FoodAdapter(context,foodJDatas);
        foodAdapter.setHeadDatas(headerDatas);
        foodAdapter.setBtnDatas(btnDatas);
        foodAdapter.setHasHeader(true);
        rvRecommend.setAdapter(foodAdapter);
    }
    private void initData(){
        foodJDatas = new ArrayList();
        foodJDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP,"周末二人世界 与你一起分享",R.drawable.food_j_shop_home_img_tmp,"Soulmate coffee","客运港/江滩·咖啡厅",67));
        foodJDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP,"周末二人世界 与你一起分享",R.drawable.food_j_shop_home_img_tmp,"Soulmate coffee","客运港/江滩·咖啡厅",67));
        headerDatas = new int[]{R.drawable.banner,R.drawable.banner,R.drawable.banner};
        btnDatas = new ArrayList();
        btnDatas.add(new FoodFragmentBtnItem(R.drawable.icon_juhui,"聚会", FoodJCommonActivity_.class));
        btnDatas.add(new FoodFragmentBtnItem(R.drawable.icon_diaojiaomeishi,"刁角美食", FoodJCommonActivity_.class));
        btnDatas.add(new FoodFragmentBtnItem(R.drawable.icon_jingpinmeishi,"精品美食", FoodJCommonActivity_.class));
    }
}
