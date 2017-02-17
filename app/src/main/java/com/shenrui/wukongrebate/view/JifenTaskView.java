package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by heikki on 2017/2/14.
 */

@EViewGroup(R.layout.jifen_task_view)
public class JifenTaskView extends LinearLayout {
    public JifenTaskView(Context context) {
        super(context);
    }

    public JifenTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
