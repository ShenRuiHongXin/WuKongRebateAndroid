package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2017/2/6.
 */

@EViewGroup(R.layout.food_cats_view)
public class FoodCatsView extends LinearLayout {
    @ViewById
    LinearLayout ll_wk_menu,ll_city_food,ll_word_food,ll_food_market;

    public FoodCatsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setWkMenuOnclickListener(OnClickListener onclickListener){
        ll_wk_menu.setOnClickListener(onclickListener);
    }
    public void setCityFoodOnclickListener(OnClickListener onclickListener){
        ll_city_food.setOnClickListener(onclickListener);
    }
    public void setWordFoodOnclickListener(OnClickListener onclickListener){
        ll_word_food.setOnClickListener(onclickListener);
    }
    public void setFoodmarketOnclickListener(OnClickListener onclickListener){
        ll_food_market.setOnClickListener(onclickListener);
    }
}
