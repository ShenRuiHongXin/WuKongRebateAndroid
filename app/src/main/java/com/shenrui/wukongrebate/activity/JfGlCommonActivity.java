package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
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

@EActivity(R.layout.activity_jf_gl_common)
public class JfGlCommonActivity extends BaseActivity {
    @ViewById(R.id.tv_jf_gl_name)
    TextView tvTitle;
    @ViewById(R.id.tv_jf_gl_content)
    TextView tvContent;

    @AfterViews
    void init(){
        Bundle bundle = getIntent().getExtras();
        int target = bundle.getInt("target");
        String title;
        String content;
        if (target == 1){
            title = getResources().getString(R.string.jf_jiedu);
            content = getResources().getString(R.string.jf_jiedu_content);
        }else{
            title = getResources().getString(R.string.ts_gonglue);
            content = getResources().getString(R.string.ts_gonglue_content);
        }
        tvTitle.setText(title);
        tvContent.setText(content);
    }

    @Click(R.id.iv_jf_gl_back)
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_jf_gl_back:
                MFGT.finish(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
