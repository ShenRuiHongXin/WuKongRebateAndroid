package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


/**
 * Created by heikki on 2016/12/30.
 */

@EViewGroup(R.layout.activity_view)
public class ActivityView extends LinearLayout {
    LinearLayout ll_left;
    LinearLayout ll_right_top;
    RelativeLayout rl_right_normal_1;
    RelativeLayout rl_right_normal_2;

    @ViewById
    TextView tv_left_title,tv_right_top_title,tv_right_normal_title_1,tv_right_normal_title_2;


    public ActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 设置标题的方法
    public void setTitleText(String title) {
        tv_left_title.setText(title);
        tv_right_top_title.setText(title);
        tv_right_normal_title_1.setText(title);
        tv_right_normal_title_2.setText(title);
    }
}
