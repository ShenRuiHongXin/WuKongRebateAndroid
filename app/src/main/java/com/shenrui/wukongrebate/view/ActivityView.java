package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EView;


/**
 * Created by heikki on 2016/12/30.
 */

public class ActivityView extends LinearLayout {
    private LinearLayout ll_left;
    private LinearLayout ll_right_top;
    private RelativeLayout rl_right_normal_1;
    private RelativeLayout rl_right_normal_2;

    private TextView tv_left_title;
    private TextView tv_right_top_title;
    private TextView tv_right_normal_title_1;
    private TextView tv_right_normal_title_2;


    public ActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.activity_view, this);

        // 获取控件
        tv_left_title = (TextView) findViewById(R.id.tv_left_title);
        tv_right_top_title = (TextView) findViewById(R.id.tv_right_top_title);
        tv_right_normal_title_1 = (TextView) findViewById(R.id.tv_right_normal_title_1);
        tv_right_normal_title_2 = (TextView) findViewById(R.id.tv_right_normal_title_2);
    }

    // 设置标题的方法
    public void setTitleText(String title) {
        tv_left_title.setText(title);
        tv_right_top_title.setText(title);
        tv_right_normal_title_1.setText(title);
        tv_right_normal_title_2.setText(title);
    }
}
