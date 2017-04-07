package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shenrui.wukongrebate.utils.LogUtil;

import java.io.Serializable;

import me.kaelaela.verticalviewpager.VerticalViewPager;

/**
 * Created by heikki on 2017/4/5.
 */

public class MyVerticalViewPager extends VerticalViewPager implements Serializable{
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    //
    private boolean isRvEnd = false;
    private boolean isFirstVis = true;

    public MyVerticalViewPager(Context context) {
        super(context);
    }

    public MyVerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setIsRvEnd(boolean isRvEnd) {
        this.isRvEnd = isRvEnd;
    }

    public void setFirstVis(boolean firstVis) {
        isFirstVis = firstVis;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
//        LogUtil.d("MyVerticalViewPager dispatchTouchEvent:" + b);
        return b;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean b = super.onInterceptTouchEvent(e);
//        LogUtil.d("MyVerticalViewPager onInterceptTouchEvent:" + b);
//        if(e.getAction() == MotionEvent.) {}
        LogUtil.d("MotionEvent Action:" + e.getAction());
//        LogUtil.d("MotionEvent Action ACTION_BUTTON_PRESS:" + (e.getAction() == MotionEvent.ACTION_BUTTON_PRESS));
//        LogUtil.d("MotionEvent Action ACTION_MOVE:" + (e.getAction() == MotionEvent.ACTION_MOVE));
        //2017年4月5日18:02:29 到此
//        if (e.getAction() == MotionEvent.ACTION_MOVE){
//            return true;
//        }
//        return true;
        return super.onInterceptTouchEvent(e);
//        return isRvEnd;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
            requestDisallowInterceptTouchEvent(true);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            requestDisallowInterceptTouchEvent(false);
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 50) {
                LogUtil.d("MyVerticalViewPager" + "向上滑");
            } else if(y2 - y1 > 50) {
                LogUtil.d("MyVerticalViewPager" + "向下滑");
//                if (isRvEnd){
//                    setIsRvEnd(false);
//                    onInterceptTouchEvent(event);
//
//                }
            }
//            else if(x1 - x2 > 50) {
//                LogUtil.d("MyRecycleView" + "向左滑");
//            } else if(x2 - x1 > 50) {
//                LogUtil.d("MyRecycleView" + "向右滑");
//            }
        }
        return super.onTouchEvent(event);
    }
}
