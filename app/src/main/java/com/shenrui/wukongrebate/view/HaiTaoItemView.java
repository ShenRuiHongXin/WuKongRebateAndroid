package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2017/2/10.
 */
@EViewGroup(R.layout.activity_haitao)
public class HaiTaoItemView extends LinearLayout{

    public HaiTaoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @ViewById(R.id.ll_left)
    LinearLayout ll_left;
    @ViewById(R.id.ll_right_top)
    LinearLayout ll_right_top;
    @ViewById(R.id.ll_right_bottom)
    LinearLayout ll_right_bottom;

    @ViewById(R.id.tv_good1)
    TextView tvGood1;
    @ViewById(R.id.tv_good2)
    TextView tvGood2;
    @ViewById(R.id.tv_good3)
    TextView tvGood3;
    @ViewById(R.id.iv_good1)
    ImageView ivGood1;
    @ViewById(R.id.iv_good2)
    ImageView ivGood2;
    @ViewById(R.id.iv_good3)
    ImageView ivGood3;

    //设置商品标题和图片
    public void setGoodTitle(String left,String rightTop,String rightBottom) {
        tvGood1.setText(left);
        tvGood2.setText(rightTop);
        tvGood3.setText(rightBottom);
    }
    public void setGoodImage(int left,int rightTop,int rightBottom){
        ivGood1.setImageResource(left);
        ivGood2.setImageResource(rightTop);
        ivGood3.setImageResource(rightBottom);
    }

    HaiTaoViewItemOnClick onClick;

    @Click({R.id.ll_left,R.id.ll_right_top,R.id.ll_right_bottom})
    void onClick(View v) {
        onClick.itemClick(v.getId());
    }

    //单项点击事件
    public interface HaiTaoViewItemOnClick{
        void itemClick(int itemId);
    }
    public void setItemClick(HaiTaoViewItemOnClick itemClick){
        onClick = itemClick;
    }
}
