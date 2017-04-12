package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.ScreenUtils;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by heikki on 2017/4/12.
 */

@EViewGroup(R.layout.wk_yuanbao_ticket)
public class WkYuanbaoTicket extends LinearLayout{
    @ViewById(R.id.ll_yb_layout)
    LinearLayout layout;
    //元宝面值
    @ViewById(R.id.tv_yuanbao_value)
    TextView tvYbValue;
    //元宝价格
    @ViewById(R.id.tv_jf_price)
    TextView tvYbPrice;
    //
    @ViewsById({R.id.tv_1,R.id.tv_3,R.id.tv_jf_price,R.id.tv_4})
    List<TextView> viewList;
    @ViewById(R.id.rl_3)
    RelativeLayout rl3;

    private Context context;

    public WkYuanbaoTicket(Context context) {
        super(context);
        this.context = context;
    }

    public WkYuanbaoTicket(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public WkYuanbaoTicket(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 设置优惠券面值和价格
     * @param ybValue
     * @param ybPrice
     */
    public void setYbData(int ybValue,int ybPrice){
        initView(this.context);
        tvYbValue.setText(ybValue + "元宝");
        tvYbPrice.setText(String.valueOf(ybPrice));
    }

    /**
     * 获取优惠券面值
     * @return
     */
    public int getYbValue(){
        String str = tvYbValue.getText().toString();
        return Integer.parseInt(str.substring(0,str.length()-2));
    }

    /**
     * 获取优惠券价格
     * @return
     */
    public int getYbPrice(){
        String str = tvYbPrice.getText().toString();
        return Integer.parseInt(str);
    }

    private void initView(Context context){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();

        params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.4f);
        params.height = (int) (ScreenUtils.getScreenWidth(context) * 0.4f * 0.556f);
        LogUtil.d("屏幕宽高:" + ScreenUtils.getScreenWidth(context) + "x" + ScreenUtils.getScreenHeight(context));
        LogUtil.d("优惠券宽高:" + params.width + "x" + params.height);
        layout.setLayoutParams(params);

        float percent = ScreenUtils.px2dp(context,params.width) / 200f;
        int padding = ScreenUtils.dp2px(context,10 * percent);
        layout.setPadding(padding,padding,padding,padding);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) rl3.getLayoutParams();
        LogUtil.d("间距:" +ScreenUtils.dp2px(context,10)+","+ ScreenUtils.dp2px(context,10 * percent));
        params2.setMargins(ScreenUtils.dp2px(context,10 * percent),0,0,0);
        rl3.setLayoutParams(params2);

        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tvYbValue.getLayoutParams();
        params3.setMargins(0,ScreenUtils.dp2px(context,5 * percent),0,ScreenUtils.dp2px(context,5 * percent));
        tvYbValue.setLayoutParams(params3);

        for (int i = 0; i < viewList.size(); i++){
            viewList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            if (i == viewList.size()-1){
                viewList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
//                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) viewList.get(i).getLayoutParams();
//                params1.setMargins(0,ScreenUtils.dp2px(context,15 * percent),0,0);
//                viewList.get(i).setLayoutParams(params);
            }
        }
    }

}
