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

@EViewGroup(R.layout.food_video_view)
public class FoodVideoView extends RelativeLayout {
    @ViewById(R.id.tv_food_video_more)
    TextView tvVideoMore;

    @ViewById(R.id.tv_food_video_title)
    TextView tvFoodVideoTitle;

    @ViewById(R.id.tv_food_video_synopsis)
    TextView tvFoodVideoSynopsis;

    public FoodVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMoreOnclickListener(OnClickListener onclickListener){
        tvVideoMore.setOnClickListener(onclickListener);
    }

    public void setMoreVisibility(int visibility){
        tvVideoMore.setVisibility(visibility);
    }

    public void setFoodVideoTitle(String foodVideoTitle){
        tvFoodVideoTitle.setText(foodVideoTitle);
    }

    public void setFoodVideoSynopsis(String foodVideoSynopsis){
        tvFoodVideoSynopsis.setText(foodVideoSynopsis);
    }
}
