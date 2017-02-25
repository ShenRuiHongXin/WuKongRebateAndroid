package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.SuperActivity_;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2017/2/18.
 * 悟空返利界面 限时秒，超级返，团购
 */

@EViewGroup(R.layout.rebate_four_item)
public class RebateItemView extends LinearLayout {
    @ViewById(R.id.ll_time_limit)
    LinearLayout ll_time_limit;
    @ViewById(R.id.ll_super)
    LinearLayout ll_super;
    @ViewById(R.id.ll_group)
    LinearLayout ll_group;

    @ViewById(R.id.tv_time_limit_title)
    TextView tvTimeLimitTitle;

    @ViewById(R.id.count_time_view)
    CountTimeView llCountTime;

    @ViewById(R.id.tv_super_title)
    TextView tvSuperTitle;
    @ViewById(R.id.tv_group_title)
    TextView tvGroupTitle;
    @ViewById(R.id.iv_time_limit)
    ImageView ivTimeLimit;
    @ViewById(R.id.iv_super_good1)
    ImageView ivSuperGood1;
    @ViewById(R.id.iv_super_good2)
    ImageView ivSuperGood2;
    @ViewById(R.id.iv_group_good1)
    ImageView ivGroupGood1;
    @ViewById(R.id.iv_group_good2)
    ImageView ivGroupGood2;

    public RebateItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置倒计时开始时间
    public void setCountTime(int hour,int minute,int second){
        llCountTime.setTime(hour,minute,second);
    }

    //设置模块各item标题
    public void setTitles(String str_time,String str_super,String str_group){
        tvTimeLimitTitle.setText(str_time);
        tvSuperTitle.setText(str_super);
        tvGroupTitle.setText(str_group);
    }

    //设置模块各item图片
    public void setImages(Context context,String url_time,String url_super_good1,String url_super_good2,String url_group_good1,String url_group_good2){
        Glide.with(context).load(url_time).into(ivTimeLimit);
        Glide.with(context).load(url_super_good1).into(ivSuperGood1);
        Glide.with(context).load(url_super_good2).into(ivSuperGood2);
        Glide.with(context).load(url_group_good1).into(ivGroupGood1);
        Glide.with(context).load(url_group_good2).into(ivGroupGood2);
    }

    @Click({R.id.ll_time_limit,R.id.ll_super,R.id.ll_group})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.ll_time_limit:
                Toast.makeText(getContext(), "限时秒", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_super:
                MFGT.startActivity(getContext(), SuperActivity_.class);
                break;
            case R.id.ll_group:
                Toast.makeText(getContext(), "悟空团购", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
