package com.shenrui.wukongrebate.fragment.foodmenu;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.CookBookBean;
import com.shenrui.wukongrebate.entities.CookbookRespBox;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.utils.MyToast;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.view.CycleRotationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/3/30.
 */

@EFragment(R.layout.fragment_three_meal)
public class FragmentCookbookThreeMeal extends Fragment {
    @ViewById(R.id.crv_cookbook_three_meal)
    CycleRotationView crvThreeMeal;
    @ViewById(R.id.tl_cookbook_three_meal)
    TabLayout tlThreeMeal;
    @ViewById(R.id.rv_cookbook_three_meal)
    RecyclerView rvThreeMeal;
    @ViewById(R.id.pb_three_meal)
    ProgressBar pbThreeMeal;
    @ViewById(R.id.pb_three_meal_rv)
    ProgressBar pbThreeMealRv;
    @ViewById(R.id.sv_three_meal)
    ScrollView svThreeMeal;

    Context context;
    int[] headerDatas;
    String[] titles;
    List<FoodContentItem> foodContentItems;
    FoodAdapter adapter;
    LinearLayoutManager layoutManager;

    @AfterViews
    void init(){
        this.context = getActivity();
        svThreeMeal.setVisibility(View.INVISIBLE);
        pbThreeMeal.setVisibility(View.VISIBLE);
        initData();
    }

    void initView(){
        //轮播
        crvThreeMeal.setImages(headerDatas);
        //三餐分类
        for (int i=0;i<titles.length;i++){
            tlThreeMeal.addTab(tlThreeMeal.newTab().setText(titles[i]));
        }
        tlThreeMeal.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MyToast.showToast(context,tab.getText().toString());
                rvThreeMeal.setVisibility(View.INVISIBLE);
                pbThreeMealRv.setVisibility(View.VISIBLE);
                NetDao.getFoodMenuDataByCat(context, tab.getText().toString(), 3, new OkHttpUtils.OnCompleteListener<CookbookRespBox>() {
                    @Override
                    public void onSuccess(CookbookRespBox result) {
                        foodContentItems.clear();
                        for (int i=0; i<result.getRespData().getResult().getNum(); i++){
                            CookBookBean cookBookBean = result.getRespData().getResult().getList().get(i);
                            foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,cookBookBean));
                        }
                        adapter.notifyDataSetChanged();
                        rvThreeMeal.setVisibility(View.VISIBLE);
                        pbThreeMealRv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //内容
        adapter = new FoodAdapter(context,foodContentItems);
        adapter.setHasFooter(false);
        rvThreeMeal.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvThreeMeal.setLayoutManager(layoutManager);
        svThreeMeal.setVisibility(View.VISIBLE);
        pbThreeMeal.setVisibility(View.GONE);
    }

    void initData(){
        headerDatas = new int[]{R.drawable.banner,R.drawable.banner,R.drawable.banner};
        titles = new String[]{"早餐","午餐","下午茶","晚餐","夜宵"};

        foodContentItems = new ArrayList<>();
        NetDao.getFoodMenuDataByCat(context, "早餐", 3, new OkHttpUtils.OnCompleteListener<CookbookRespBox>() {
            @Override
            public void onSuccess(CookbookRespBox result) {
                for (int i=0; i<result.getRespData().getResult().getNum(); i++){
                    CookBookBean cookBookBean = result.getRespData().getResult().getList().get(i);
                    foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,cookBookBean));
                }
                initView();
            }

            @Override
            public void onError(String error) {

            }
        });
//        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,"果蔬沙拉",R.drawable.img_one));
//        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,"简易早餐包",R.drawable.img_two));
//        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,"鸡蛋糯米卷",R.drawable.img_three));

    }
}
