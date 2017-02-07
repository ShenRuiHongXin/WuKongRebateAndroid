package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by heikki on 2017/2/6.
 */

@EViewGroup(R.layout.food_cats_view)
public class FoodCatsView extends LinearLayout {
    public FoodCatsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
