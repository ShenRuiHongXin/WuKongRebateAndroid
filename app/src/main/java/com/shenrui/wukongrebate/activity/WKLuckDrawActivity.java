package com.shenrui.wukongrebate.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.WKLuckDrawAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

@EActivity(R.layout.activity_wkluck_draw)
public class WKLuckDrawActivity extends BaseActivity{
    //标题栏
    @ViewsById({R.id.toolbar_left_text, R.id.toolbar_left_image, R.id.toolbar_title, R.id.toolbar_right_image})
    List<View> listTitleView;
    @ViewById(R.id.drawRv)
    RecyclerView drawRv;

    LinearLayoutManager linearLayoutManager;
    WKLuckDrawAdapter adapter;
    @AfterViews
    void init() {
        ((ImageView) listTitleView.get(1)).setImageResource(R.drawable.common_btn_back_n);
        (listTitleView.get(0)).setVisibility(View.GONE);
        ((TextView) listTitleView.get(2)).setText("悟空抽奖");
        listTitleView.get(3).setVisibility(View.VISIBLE);
        ((ImageView)listTitleView.get(3)).setImageResource(R.drawable.common_btn_back_n);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        drawRv.setLayoutManager(linearLayoutManager);
        adapter = new WKLuckDrawAdapter(this);
        drawRv.setAdapter(adapter);
    }

    @Click({R.id.toolbar_left_image,R.id.toolbar_right_image})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                finish();
                break;
            case R.id.toolbar_right_image:
                //刷新数据

                break;
        }
    }
}