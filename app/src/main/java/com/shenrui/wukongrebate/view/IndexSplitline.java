package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2016/12/30.
 */

@EViewGroup(R.layout.index_splitline)
public class IndexSplitline extends RelativeLayout {
    @ViewById
    TextView tv_splitline_content;

    public IndexSplitline(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSplitlineContent(String content){
        tv_splitline_content.setText(content);
    }
}
