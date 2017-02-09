package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2017/2/6.
 */

@EViewGroup(R.layout.food_article_view)
public class FoodArticleView extends RelativeLayout {
    @ViewById(R.id.tv_food_article_more)
    TextView tvArticleMore;

    public FoodArticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMoreOnclickListener(OnClickListener onclickListener){
        tvArticleMore.setOnClickListener(onclickListener);
    }
}
