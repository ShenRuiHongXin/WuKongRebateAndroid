package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_settings)
public class SettingsActivity extends BaseActivity{
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;

    @ViewById(R.id.personalInfo)
    LinearLayout personalInfo;
    @ViewById(R.id.security)
    LinearLayout security;
    @ViewById(R.id.secret)
    LinearLayout secret;
    @ViewById(R.id.message)
    LinearLayout message;
    @ViewById(R.id.common)
    LinearLayout common;
    @ViewById(R.id.area)
    LinearLayout area;
    @ViewById(R.id.about)
    LinearLayout about;
    @ViewById(R.id.btn_exit)
    Button btnExit;

    boolean isLogined;
    @AfterViews
    void initView() {
        toolbar_left_image.setVisibility(View.VISIBLE);
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_title.setText("设置");
        toolbar_right_image.setVisibility(View.GONE);

        initUserData();
    }

    private void initUserData() {
        if(SharedPreferenceUtils.getInstance(this).getUserInfo()!=null){
            isLogined = true;
            btnExit.setVisibility(View.VISIBLE);
        }else{
            isLogined = false;
            btnExit.setVisibility(View.GONE);
        }
    }

    @Click({R.id.btn_exit,R.id.toolbar_left_image,R.id.personalInfo,R.id.security,R.id.secret,R.id.message,R.id.common,R.id.area,R.id.about})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                finish();
                break;
            //退出当前账户
            case R.id.btn_exit:
                SharedPreferenceUtils.getInstance(this).clearAll();
                finish();
                break;
            //个人资料
            case R.id.personalInfo:
                if(isLogined){
                    startActivity(new Intent(this,PersonalInfoActivity_.class));
                }else{
                    startActivity(new Intent(this,LoginActivity_.class));
                }
                break;
            //账户与安全
            case R.id.security:
                if(isLogined){
                    startActivity(new Intent(this,SecurityActivity_.class));
                }else{
                    startActivity(new Intent(this,LoginActivity_.class));
                }
                break;
            //隐私
            case R.id.secret:

                break;
            //消息与通知
            case R.id.message:

                break;
            //通用
            case R.id.common:
                startActivity(new Intent(this,CommonActivity_.class));
                break;
            //地区
            case R.id.area:

                break;
            //关于悟空返利
            case R.id.about:
                startActivity(new Intent(this,AboutActivity_.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserData();
    }
}
