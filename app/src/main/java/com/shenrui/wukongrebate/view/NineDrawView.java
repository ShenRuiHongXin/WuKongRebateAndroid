package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by heikki on 2017/1/24.
 */

@EViewGroup(R.layout.nine_draw_view)
public class NineDrawView extends PercentLinearLayout {
    //奖品
    @ViewsById({R.id.iv_prize_1,R.id.iv_prize_2,R.id.iv_prize_3,R.id.iv_prize_4,R.id.iv_prize_5,R.id.iv_prize_6,R.id.iv_prize_7,R.id.iv_prize_8,R.id.iv_prize_9})
    List<ImageView> imageViewList;
    //背景
    @ViewsById({R.id.prl_1,R.id.prl_2,R.id.prl_3,R.id.prl_6,R.id.prl_9,R.id.prl_8,R.id.prl_7,R.id.prl_4})
    List<PercentRelativeLayout> percentRelativeLayoutList;
    //可抽奖次数
    @ViewById(R.id.tv_draw_times)
    TextView tvTimes;
    //开始
    ImageView ivStart;

    //剩余抽奖次数
    private int drawTimes = 3;
    //所有的视图
    private List<PercentRelativeLayout> views = new LinkedList<>();
    //变色时间间隔
    private int timeC= 50;
    //当前亮灯位置,从0开始
    private int lightPosition = 0;
    //需要转多少圈
    private int runCount = 5;
    //中奖的幸运位置,从0开始
    private int lunckyPosition = 4;

    public NineDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPrizeImage(int[] imgs){
        for(int i = 0; i<imgs.length; i++){
            imageViewList.get(i).setBackgroundResource(imgs[i]);
        }

        ivStart = imageViewList.get(4);

//        views.add(imageViewList.get(0));
//        views.add(imageViewList.get(1));
//        views.add(imageViewList.get(2));
//        views.add(imageViewList.get(5));
//        views.add(imageViewList.get(8));
//        views.add(imageViewList.get(7));
//        views.add(imageViewList.get(6));
//        views.add(imageViewList.get(3));

        for (PercentRelativeLayout prl : percentRelativeLayoutList) {
            views.add(prl);
        }

        try {
            ivStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivStart.setClickable(false);
                    ivStart.setEnabled(false);
                    tvTimes.setText("今日剩余" + drawTimes +"次");
                    runCount = 5;
                    timeC = 50;
                    views.get(lunckyPosition).setBackgroundColor(Color.TRANSPARENT);
                    lunckyPosition = randomNum(0,7);
                    new TimeCount(timeC*9,timeC).start();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成随机数
     * @param minNum
     * @param maxNum
     * @return
     */
    private int randomNum(int minNum,int maxNum) {
        int max = maxNum;
        int min = minNum;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            lightPosition = 0;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            Log.i(">>>","---"+lightPosition);
            //如果是最后一次滚动
            if (runCount>0){
                if (lightPosition>0){
                    views.get(lightPosition-1).setBackgroundColor(Color.TRANSPARENT);
//                    views.get(lightPosition-1).setBackgroundResource(R.drawable.prize_shape);
                }
                if (lightPosition<8){
//                    views.get(lightPosition).setBackgroundColor(Color.RED);
                    views.get(lightPosition).setBackgroundResource(R.drawable.prize_shape_red);
                }

            }else if (runCount==0){

                if (lightPosition<=lunckyPosition){
                    if (lightPosition>0){
                        views.get(lightPosition-1).setBackgroundColor(Color.TRANSPARENT);
//                        views.get(lightPosition-1).setBackgroundResource(R.drawable.prize_shape);
                    }
                    if (lightPosition<8){
//                        views.get(lightPosition).setBackgroundColor(Color.RED);
                        views.get(lightPosition).setBackgroundResource(R.drawable.prize_shape_red);
                    }
                }
            }

            lightPosition++;
        }
        @Override
        public void onFinish() {
            Log.i(">>>","onFinish=="+runCount);
            //如果不是最后一圈，需要还原最后一块的颜色
            PercentRelativeLayout ivLast= views.get(7);
            if (runCount!=0){
                ivLast.setBackgroundColor(Color.TRANSPARENT);
//                ivLast.setBackgroundResource(R.drawable.prize_shape);
                //最后几转速度变慢
                if (runCount<3) timeC += 200;
                new TimeCount(timeC*9,timeC).start();
                runCount--;
            }
            //如果是最后一圈且计时也已经结束
            if (runCount==0&&lightPosition==8){
                ivStart.setClickable(true);
                ivStart.setEnabled(true);
                tvTimes.setText("恭喜你抽中: "+ lunckyPosition);
                if (lunckyPosition!=views.size())
                    ivLast.setBackgroundColor(Color.TRANSPARENT);
//                    ivLast.setBackgroundResource(R.drawable.prize_shape);

            }

        }
    }
}
