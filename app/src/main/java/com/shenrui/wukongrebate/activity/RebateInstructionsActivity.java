package com.shenrui.wukongrebate.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by heikki on 2017/1/23.
 */

@EActivity(R.layout.activity_rebate_instructions)
public class RebateInstructionsActivity extends BaseActivity{
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    @AfterViews
    void init(){
        //标题栏
        listTitleView.get(3).setVisibility(View.GONE);
        ((TextView)listTitleView.get(2)).setText("悟空返利教程");
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.common_btn_back_n);
        listTitleView.get(0).setVisibility(View.GONE);

    }


    @Click(R.id.toolbar_left_image)
    void click(){
        finish();
    }
}
