package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/1/4.
 */

public class TaskItem {
    public static final int OFFICIAL_TASK = 0;
    public static final int SHOP_TASK = 1;

    private int taskType;

    public TaskItem(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
}
