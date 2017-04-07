package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shenrui.wukongrebate.utils.LogUtil;

/**
 * Created by heikki on 2017/4/5.
 */

public class MyRecycleView extends RecyclerView {
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    //
    private boolean isDispatch = true;

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDispatch(boolean dispatch) {
        isDispatch = dispatch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
//        LogUtil.d("MyRecycleView dispatchTouchEvent:" + b);
//        return isDispatch ? b :isDispatch;
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean b = super.onInterceptTouchEvent(e);
//        LogUtil.d("MyRecycleView onInterceptTouchEvent:" + b);
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 50) {
                LogUtil.d("MyRecycleView" + "向上滑");
            } else if(y2 - y1 > 50) {
                LogUtil.d("MyRecycleView" + "向下滑");
            }
//            else if(x1 - x2 > 50) {
//                LogUtil.d("MyRecycleView" + "向左滑");
//            } else if(x2 - x1 > 50) {
//                LogUtil.d("MyRecycleView" + "向右滑");
//            }
        }
        LogUtil.d("isDispatch:" + isDispatch);
//        if (isDispatch){
            return super.onTouchEvent(event);

//        }
//        return false;
    }
}
