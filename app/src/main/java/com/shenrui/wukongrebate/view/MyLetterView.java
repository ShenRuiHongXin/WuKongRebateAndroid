package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shenrui.wukongrebate.R;

/**
 * Created by Administrator on 2017/2/4.
 */

public class MyLetterView extends View {

    private Paint mPaint;
    private boolean isShowBg = false;//用于区分是否显示view的背景
    private OnSlidingListener mOnSlidingListener;//滑动此view的监听器
    private int choose = -1;//用于标记当前所选中的位置
    private TextView mTvDialog;//用于接受从activity中传过来的，中间用于展示字母的TextView
    private String[] letter = { "定位", "热门", "A", "B", "C", "D",
            "E", "F", "G", "H","J", "K", "L", "M", "N","P", "Q",
            "R", "S", "T","W", "X", "Y", "Z"};

    public MyLetterView(Context context) {
        super(context);
    }

    public MyLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
        mPaint.setColor(getResources().getColor(R.color.black));
    }

    public MyLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //当此View被按下时所显示的背景颜色
        /*if(isShowBg){
            canvas.drawColor(getResources().getColor(R.color.mineGrey));
        }*/
        //计算每个字符所占的高度
        float singleHeight = getHeight()/letter.length;
        int width = getWidth();
        for(int i=0;i<letter.length;i++){
            String text = letter[i];
            float xPosition = width/2 - mPaint.measureText(text)/2;
            float yPosition = singleHeight*(i+1);
            //通过不断改变yPosition将数组中的数据一个一个绘制到自定义的View中
            canvas.drawText(text,xPosition,yPosition,mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int position = (int) (event.getY()/getHeight()*letter.length);
        int oldChoose = choose;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                isShowBg = true;
                if(oldChoose!=position && mOnSlidingListener!=null){
                    if(position>0 && position<letter.length){
                        //将滑动到的字母传递到activity
                        mOnSlidingListener.sliding(letter[position]);
                        choose = position;
                        if(mTvDialog!=null){
                            mTvDialog.setVisibility(VISIBLE);
                            mTvDialog.setText(letter[position]);
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isShowBg = true;
                if(oldChoose!=position && mOnSlidingListener!=null){
                    if(position>=0 && position<letter.length){
                        mOnSlidingListener.sliding(letter[position]);
                        choose = position;
                        if(mTvDialog!=null){
                            mTvDialog.setVisibility(VISIBLE);
                            mTvDialog.setText(letter[position]);
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isShowBg = false;
                choose = -1;
                if(mTvDialog!=null){
                    mTvDialog.setVisibility(GONE);
                }
                invalidate();
                break;
        }
        return true;
    }

    //MyLetterView的一个滑动的监听
    public interface OnSlidingListener {
        void sliding(String str);
    }
    public void setOnSlidingListener(OnSlidingListener mOnSlidingListener){
        this.mOnSlidingListener = mOnSlidingListener;
    }

    public void setTextView(TextView mTvDialog){
        this.mTvDialog = mTvDialog;
    }
}
