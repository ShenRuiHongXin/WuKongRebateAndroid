package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.ScreenUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by heikki on 2017/4/12.
 */

@EActivity(R.layout.activity_my_vip)
public class MyVipActivity extends BaseActivity {
    @ViewsById({R.id.crl_1,R.id.crl_2,R.id.crl_3})
    List<RelativeLayout> layoutList;
    @ViewById(R.id.tv_2)
    TextView tvVipRanking;

    @AfterViews
    void init(){
        initView();
        String format = getResources().getString(R.string.vip_ranking);
        String result = String.format(format,0.6100*100);
        SpannableStringBuilder builder = new SpannableStringBuilder(result);
        ForegroundColorSpan gold = new ForegroundColorSpan(getResources().getColor(R.color.vip_gold));
        builder.setSpan(gold,2,result.indexOf("%")+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvVipRanking.setText(builder);
    }

    void initView(){
        int screenWidth = ScreenUtils.getScreenWidth(this);
        int smallPadding = (int) (screenWidth * 0.04);
        int largePadding = (int) (screenWidth * 0.05);

        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) layoutList.get(0).getLayoutParams();
        params1.width = (int) (screenWidth * 0.21);
        params1.height = params1.width;
        params1.setMargins(largePadding,0,0,0);
        layoutList.get(0).setLayoutParams(params1);

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) layoutList.get(1).getLayoutParams();
        params2.width = (int) (screenWidth * 0.4);
        params2.height = params2.width;
        params2.setMargins(smallPadding,0,smallPadding,0);
        layoutList.get(1).setLayoutParams(params2);

        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) layoutList.get(2).getLayoutParams();
        params3.width = (int) (screenWidth * 0.21);
        params3.height = params3.width;
        params3.setMargins(0,0,largePadding,0);
        layoutList.get(2).setLayoutParams(params3);
    }

    @Click({R.id.iv_vip_back,R.id.crl_1,R.id.crl_3,R.id.tv_duobao_zhekou,R.id.tv_fanli_zengzhi,R.id.tv_jifen_duihuan,R.id.tv_tixian_edu})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_vip_back:
                MFGT.finish(this);
                break;
            case R.id.crl_1:
                Intent intent = new Intent(this,JfGlCommonActivity_.class);
                intent.putExtra("target",1);
                MFGT.startActivity(this,intent);
                break;
            case R.id.crl_3:
                Intent in = new Intent(this,JfGlCommonActivity_.class);
                in.putExtra("target",3);
                MFGT.startActivity(this,in);
                break;
            case R.id.tv_duobao_zhekou:
                Intent i1 = new Intent(this,VipPrivilegeActivity_.class);
                i1.putExtra("target",1);
                MFGT.startActivity(this,i1);
                break;
            case R.id.tv_fanli_zengzhi:
                Intent i2 = new Intent(this,VipPrivilegeActivity_.class);
                i2.putExtra("target",2);
                MFGT.startActivity(this,i2);
                break;
            case R.id.tv_jifen_duihuan:
                Intent i3 = new Intent(this,VipPrivilegeActivity_.class);
                i3.putExtra("target",3);
                MFGT.startActivity(this,i3);
                break;
            case R.id.tv_tixian_edu:
                Intent i4 = new Intent(this,VipPrivilegeActivity_.class);
                i4.putExtra("target",4);
                MFGT.startActivity(this,i4);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
