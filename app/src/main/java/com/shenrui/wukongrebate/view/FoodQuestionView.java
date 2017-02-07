package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2017/2/7.
 */

@EViewGroup(R.layout.food_question)
public class FoodQuestionView extends RelativeLayout {
    @ViewById(R.id.tv_food_q_title)
    TextView tvFoodQuestion;

    public FoodQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTvFoodQuestion(){
        String q = "美食问答：请问拍食品时，你们会用哪些增加美感的道具？";
        SpannableString ss1=  new SpannableString(q);
        ss1.setSpan(new RelativeSizeSpan(1.25f), 0,5, 0); // set size
//        ss1.setSpan(new ForegroundColorSpan(), 0, 5, 0);// set color
        tvFoodQuestion.setText(ss1);
    }
}
