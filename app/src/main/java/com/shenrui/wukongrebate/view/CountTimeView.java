package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2017/2/22.
 */

@EViewGroup(R.layout.layout_count_time)
public class CountTimeView extends LinearLayout{
    @ViewById(R.id.tv_hour)
    TextView tvHour;
    @ViewById(R.id.tv_minute)
    TextView tvMin;
    @ViewById(R.id.tv_second)
    TextView tvSec;
    @ViewById(R.id.layout_count_time)
    LinearLayout layoutCountTime;

    private int mHour;
    private int mMinute;
    private int mSecond;
    private boolean isRun = true;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                computeTime();
                tvHour.setText(mHour+"");
                tvMin.setText(mMinute+"");
                tvSec.setText(mSecond+"");
                if (mHour==0&&mMinute==0&&mSecond==0){
                    layoutCountTime.setVisibility(GONE);
                }
            }
        }
    };

    public CountTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTime(int hour,int minute,int second){
        this.mHour = hour;
        this.mMinute = minute;
        this.mSecond = second;
        startCountTime();
    }

    private void startCountTime() {
        new Thread(){
            @Override
            public void run() {
                while (isRun){
                    try {
                        Thread.sleep(1000);
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMinute--;
            mSecond = 59;
            if (mMinute < 0) {
                mMinute = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    isRun = false;
                }
            }
        }
    }
}
