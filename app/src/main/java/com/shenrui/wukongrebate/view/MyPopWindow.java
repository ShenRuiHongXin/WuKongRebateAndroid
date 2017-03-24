package com.shenrui.wukongrebate.view;

import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by heikki on 2017/3/22.
 */

public class MyPopWindow extends PopupWindow {
    private boolean isExpand = false;

    public MyPopWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
