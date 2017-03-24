package com.shenrui.wukongrebate.activity;

import android.view.View;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_shop_detail)
public class ShopDetailActivity extends BaseActivity {




    @Click({R.id.iv_shop_detail_back})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_shop_detail_back:
                MFGT.finish(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
