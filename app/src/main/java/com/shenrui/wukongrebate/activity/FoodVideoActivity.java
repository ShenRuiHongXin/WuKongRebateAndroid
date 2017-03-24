package com.shenrui.wukongrebate.activity;

import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/2/7.
 */

@EActivity(R.layout.activity_food_video)
public class FoodVideoActivity extends BaseActivity {
    @ViewById(R.id.et_food_video_search)
    EditText et;
    @ViewById(R.id.tv_food_video_search)
    TextView tvSearch;
    @ViewById(R.id.food_video_srl)
    SwipeRefreshLayout srl;
    @ViewById(R.id.food_video_rv)
    RecyclerView rv;

    FoodAdapter adapter;
    LinearLayoutManager layoutManager;
    List foodVideoDatas;

    @AfterViews
    void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        setListener();
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
            }
        });
    }

    private void initData(){
        foodVideoDatas = new ArrayList();
        foodVideoDatas.add(new FoodContentItem(FoodAdapter.TYPE_VIDEO,"食汇寿司——在家也可以做",R.drawable.food_gl_video_img_tmp));
        foodVideoDatas.add(new FoodContentItem(FoodAdapter.TYPE_VIDEO,"食汇寿司——在家也可以做",R.drawable.food_gl_video_img_tmp));
        foodVideoDatas.add(new FoodContentItem(FoodAdapter.TYPE_VIDEO,"食汇寿司——在家也可以做",R.drawable.food_gl_video_img_tmp));
    }

    private void initView() {
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        initData();
        adapter = new FoodAdapter(this,foodVideoDatas);
        rv.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0,0,0,40);
            }
        });
    }

    @Click({R.id.iv_food_video_back,R.id.tv_food_video_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_food_video_back:
                MFGT.finish(this);
                break;
            case R.id.tv_food_video_search:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

}
