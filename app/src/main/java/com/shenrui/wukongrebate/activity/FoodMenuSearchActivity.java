package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.CookbookRespBox;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/4/5.
 */

@EActivity(R.layout.activity_food_menu_search)
public class FoodMenuSearchActivity extends BaseActivity {
    @ViewById(R.id.et_food_menu_search)
    EditText et;
    @ViewById(R.id.rv_food_menu_search_content)
    RecyclerView rvSearchResult;
    @ViewById(R.id.pb_cookbook_search)
    ProgressBar pbSearch;

    private FoodAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<FoodContentItem> foodContentItemList;

    @AfterViews
    void init(){
        Bundle bundle = getIntent().getExtras();
        String keyWord = bundle.getString("keyWord");
        String classid = bundle.getString("classid");
        LogUtil.d("keyword:" + keyWord+" classid:" + classid);
        et.setHint(keyWord);
        if (keyWord != null || classid != null){
            initData(keyWord,classid);
        }
    }

    void initData(String keyWord ,String classid){
        showProgressBar();
        NetDao.getFoodMenuDataBySearch(this, keyWord,classid, 10, new OkHttpUtils.OnCompleteListener<CookbookRespBox>() {
            @Override
            public void onSuccess(CookbookRespBox result) {
                LogUtil.d("搜索结果:" + result.getRespData().getResult().getNum());
                foodContentItemList = new ArrayList<FoodContentItem>();
                for (int i=0; i<result.getRespData().getResult().getNum(); i++){
                    foodContentItemList.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,result.getRespData().getResult().getList().get(i)));
                }
                initView(foodContentItemList);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    void initView(List<FoodContentItem> catsList){
        adapter = new FoodAdapter(this,catsList);
        adapter.setHasFooter(false);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvSearchResult.setAdapter(adapter);
        rvSearchResult.setLayoutManager(layoutManager);
        hideProgressBar();
    }

    @Click({R.id.iv_food_menu_search_back,R.id.tv_food_menu_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_food_menu_search_back:
                MFGT.finish(this);
                break;
            case R.id.tv_food_menu_search:
                if (!et.getText().toString().trim().equals("") && et.getText().toString().trim() != null){
                    et.setHint(et.getText().toString().trim());
                    initData(et.getText().toString().trim(),null);
                }
                break;

        }
    }

    private void showProgressBar(){
        pbSearch.setVisibility(View.VISIBLE);
        rvSearchResult.setVisibility(View.GONE);
    }
    private void hideProgressBar(){
        pbSearch.setVisibility(View.GONE);
        rvSearchResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
