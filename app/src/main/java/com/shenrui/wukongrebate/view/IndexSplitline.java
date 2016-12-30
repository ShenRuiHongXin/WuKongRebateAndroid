package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

/**
 * Created by heikki on 2016/12/30.
 */

public class IndexSplitline extends RelativeLayout {
    private TextView tv_splitline_content;

    public IndexSplitline(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.index_splitline, this);
        // 获取控件
        tv_splitline_content = (TextView) findViewById(R.id.tv_splitline_content);
    }

    public void setSplitlineContent(String content){
        tv_splitline_content.setText(content);
    }
}
