package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2017/4/12.
 */

@EActivity(R.layout.activity_yuanbao)
public class YuanbaoActivity extends BaseActivity {
    @ViewById(R.id.tv_yb_yue_title)
    TextView tvTitle;
    @ViewById(R.id.tv_yuanbao_1)
    TextView tvYb1;
    @ViewById(R.id.tv_yuanbao_2)
    TextView tvYb2;
    @ViewById(R.id.tv_yue_hint)
    TextView tvYue1;
    @ViewById(R.id.tv_cz_tx)
    TextView tvCzTx;

    public static final int YUANBAO = 1;
    public static final int YUE = 2;
    private int target;

    @AfterViews
    void init(){
        Intent intent = getIntent();
        target = intent.getIntExtra("target",0);
        switch (target){
            case YUANBAO:
                tvTitle.setText("元宝");
                tvCzTx.setText("提现");
                tvYue1.setVisibility(View.GONE);
                break;
            case YUE:
                tvTitle.setText("余额");
                tvCzTx.setText("充值");
                tvYb1.setVisibility(View.GONE);
                tvYb2.setVisibility(View.GONE);
                break;
        }
    }

    @Click({R.id.iv_yb_back,R.id.rl_cz_tx})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_yb_back:
                MFGT.finish(this);
                break;
            case R.id.rl_cz_tx:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
