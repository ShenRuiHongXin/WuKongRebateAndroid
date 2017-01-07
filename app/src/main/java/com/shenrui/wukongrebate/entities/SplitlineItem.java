package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/1/4.
 */

public class SplitlineItem {
    public static final int OFFICIAL_TASK_LINE = 0;
    public static final int SHOP_TASK_LINE = 1;
    public static final int CENTER_LINE = 2;
    public static final int END_LINE = 3;

    private int lineType;

    public SplitlineItem(int lineType) {
        this.lineType = lineType;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }
}
