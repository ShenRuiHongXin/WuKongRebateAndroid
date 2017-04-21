package com.shenrui.wukongrebate.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2017/4/13.
 */

@EActivity(R.layout.activity_vip_privilege)
public class VipPrivilegeActivity extends BaseActivity {
    @ViewById(R.id.tv_vip_privilege_name)
    TextView tvTitle;
    @ViewById(R.id.tv_son_title)
    TextView tvSonTitle;
    @ViewById(R.id.tv_cons)
    TextView tvCons;
    @ViewById(R.id.tv_vip_privilege_content)
    TextView tvContent;

    @AfterViews
    void init(){
        Bundle bundle = getIntent().getExtras();
        int target = bundle.getInt("target");
        int imgRes = 0;
        String title = "";
        String cons = "";
        String content = "";
        switch (target){
            case 1:
                title = "夺宝折扣";
                cons = "折扣说明";
                content = getResources().getString(R.string.duobao_zhekou_content);
                imgRes = R.drawable.duobao_zhekou_large;
                break;
            case 2:
                title = "返利增值";
                cons = "增值说明(在返利金基础上叠加)";
                content = getResources().getString(R.string.fanli_zengzhi_content);
                imgRes = R.drawable.fanli_zengzhi_large;
                break;
            case 3:
                title = "积分兑换";
                tvCons.setVisibility(View.GONE);
                tvContent.setVisibility(View.GONE);
                imgRes = R.drawable.jifen_duihuan_large;
                break;
            case 4:
                title = "提现额度";
                tvCons.setVisibility(View.GONE);
                content = getResources().getString(R.string.tixian_edu_content);
                imgRes = R.drawable.tixian_edu_large;
        }
        tvTitle.setText(title);
        tvCons.setText(cons);
        tvContent.setText(content);
        tvSonTitle.setText(title);
        Drawable drawable= ResourcesCompat.getDrawable(this.getResources(),imgRes,null);//getResources().getDrawable(imgRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvSonTitle.setCompoundDrawables(null,drawable,null,null);
    }

    @Click({R.id.iv_vip_privilege_back})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_vip_privilege_back:
                MFGT.finish(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
