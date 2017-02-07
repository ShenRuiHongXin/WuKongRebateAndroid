package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by heikki on 2017/2/6.
 */

@EViewGroup(R.layout.food_article_view)
public class FoodArticleView extends RelativeLayout {
    public FoodArticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
