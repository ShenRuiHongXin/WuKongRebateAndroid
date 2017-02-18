package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2017/2/18.
 * 悟空返利界面 超级返，9块9，悟空夺宝，悟空团购模块
 */

@EViewGroup(R.layout.rebate_four_item)
public class RebateItemView extends LinearLayout {

    @ViewById(R.id.ll_left_rebate)
    LinearLayout ll_left;
    @ViewById(R.id.ll_right_top_nine)
    LinearLayout ll_right_top;
    @ViewById(R.id.ll_right_bottom_left_award)
    LinearLayout ll_right_bottom_left;
    @ViewById(R.id.ll_right_bottom_right_group)
    LinearLayout ll_right_bottom_right;

    @ViewById(R.id.tv_good1_super)
    TextView tvGood1;
    @ViewById(R.id.tv_good2_nine)
    TextView tvGood2;
    @ViewById(R.id.tv_good3_award)
    TextView tvGood3;
    @ViewById(R.id.tv_good4_group)
    TextView tvGood4;
    @ViewById(R.id.iv_good1_super)
    ImageView ivGood1;
    @ViewById(R.id.iv_good2_nine)
    ImageView ivGood2;
    @ViewById(R.id.iv_good3_award)
    ImageView ivGood3;
    @ViewById(R.id.iv_good4_group)
    ImageView ivGood4;

    public RebateItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置模块各item标题
    public void setTitles(String str_super,String str_nine,String str_award,String str_group){
        tvGood1.setText(str_super);
        tvGood2.setText(str_nine);
        tvGood3.setText(str_award);
        tvGood4.setText(str_group);
    }

    //设置模块各item图片
    public void setImages(Context context,String url_super,String url_nine,String url_award,String url_group){
        Glide.with(context).load(url_super).into(ivGood1);
        Glide.with(context).load(url_nine).into(ivGood2);
        Glide.with(context).load(url_award).into(ivGood3);
        Glide.with(context).load(url_group).into(ivGood4);
    }

    @Click({R.id.ll_left_rebate,R.id.ll_right_top_nine,R.id.ll_right_bottom_left_award,R.id.ll_right_bottom_right_group})
    void clickEvent(View view){
        itemOnClick.itemClick(view.getId());
    }

    RebateViewItemOnClick itemOnClick;

    public interface RebateViewItemOnClick{
        void itemClick(int itemId);
    }

    //设置模块各item的监听事件
    public void setRebateViewOnClick(RebateViewItemOnClick onClick){
        this.itemOnClick = onClick;
    }
}
