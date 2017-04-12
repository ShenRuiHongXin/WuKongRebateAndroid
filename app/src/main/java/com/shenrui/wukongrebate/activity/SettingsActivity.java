package com.shenrui.wukongrebate.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
//    @ViewById(R.id.secret)
//    LinearLayout secret;
    @ViewById(R.id.message)
    LinearLayout message;
//    @ViewById(R.id.common)
//    LinearLayout common;
//    @ViewById(R.id.area)
//    LinearLayout area;
    @ViewById(R.id.about)
    LinearLayout about;
    @ViewById(R.id.btn_exit)
    Button btnExit;

    boolean isLogined;
    @AfterViews
    void initView() {
        toolbar_left_image.setVisibility(View.VISIBLE);
        toolbar_left_image.setImageResource(R.drawable.nav_icon_back);
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

    @Click({R.id.btn_exit,R.id.toolbar_left_image,R.id.personalInfo,R.id.security,R.id.about})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                MFGT.finish(this);
                break;
            //退出当前账户
            case R.id.btn_exit:
                String identityType = SharedPreferenceUtils.getInstance(SettingsActivity.this).getUserAuths().getIdentity_type();
                //如果为淘宝帐号则取消授权
                if (identityType.equals(Constants.TAOBAO)){
                    AlibcLogin.getInstance().logout(SettingsActivity.this, new LogoutCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(SettingsActivity.this, "登出成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(SettingsActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //如果为其他三方帐号也要取消授权
                if (identityType.equals(Constants.SINA) || identityType.equals(Constants.QQ)
                        || identityType.equals(Constants.WECHAT)){
                    Platform platform = null;
                    switch (identityType){
                        case Constants.SINA:
                            platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                            break;
                        case Constants.QQ:
                            platform = ShareSDK.getPlatform(QQ.NAME);
                            break;
                        case Constants.WECHAT:
                            platform = ShareSDK.getPlatform(Wechat.NAME);
                            break;
                    }
                    platform.removeAccount(true);
                    Log.e("DeDiWang","platform.removeAccount(true);");
                }
                SharedPreferenceUtils.getInstance(this).clearAll();
                MFGT.finish(this);
                break;
            //个人资料
            case R.id.personalInfo:
                if(isLogined){
                    MFGT.startActivity(this,PersonalInfoActivity_.class);
                }else{
                    MFGT.startActivity(this,LoginActivity_.class);
                }
                break;
            //账户与安全
            case R.id.security:
                if(isLogined){
                    MFGT.startActivity(this,SecurityActivity_.class);
                }else{
                    MFGT.startActivity(this,LoginActivity_.class);
                }
                break;
//            //隐私
//            case R.id.secret:
//
//                break;
            //消息与通知
            case R.id.message:

                break;
//            //通用
//            case R.id.common:
//                MFGT.startActivity(this,CommonActivity_.class);
//                break;
//            //地区
//            case R.id.area:
//
//                break;
            //关于悟空返利
            case R.id.about:
                MFGT.startActivity(this,AboutActivity_.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserData();
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
