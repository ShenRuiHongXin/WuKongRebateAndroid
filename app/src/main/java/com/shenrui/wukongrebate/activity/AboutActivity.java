package com.shenrui.wukongrebate.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;

    @AfterViews
    void init(){
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_title.setText("关于悟空返利");
        toolbar_right_image.setVisibility(View.GONE);
    }
    @Click({R.id.toolbar_left_image,R.id.newVersion,R.id.appAgreement,R.id.copyRight,R.id.helpCenter})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                MFGT.finish(this);
                break;
            //新版本检测
            case R.id.newVersion:

                break;
            //版权信息
            case R.id.copyRight:

                break;
            //软件许可协议
            case R.id.appAgreement:

                break;
            //帮助中心
            case R.id.helpCenter:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
