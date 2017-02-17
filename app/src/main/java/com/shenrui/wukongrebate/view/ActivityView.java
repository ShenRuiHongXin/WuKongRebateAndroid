package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
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

    @ViewById
    TextView tv_left_content,tv_right_normal_content_1,tv_right_normal_content_2;

    @ViewById
    ImageView iv_left_icon,iv_right_top_icon,iv_right_normal_icon_1,iv_right_normal_icon_2;


    public ActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 设置标题
    public void setTitleText(String[] title) {
        tv_left_title.setText(title[0]);
        tv_right_top_title.setText(title[1]);
        tv_right_normal_title_1.setText(title[2]);
        tv_right_normal_title_2.setText(title[3]);
    }

    //设置简介内容
    public void setTitleContent(String[] content){
        tv_left_content.setText(content[0]);
        tv_right_normal_content_1.setText(content[1]);
        tv_right_normal_content_2.setText(content[2]);
    }

    //设置图标
    public void setTitleIcon(int[] imgs){
        iv_left_icon.setImageResource(imgs[0]);
        iv_right_top_icon.setImageResource(imgs[1]);
        iv_right_normal_icon_1.setImageResource(imgs[2]);
        iv_right_normal_icon_2.setImageResource(imgs[3]);
    }
}
