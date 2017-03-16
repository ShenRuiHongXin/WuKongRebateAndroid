package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_security)
public class SecurityActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;

    @ViewById(R.id.userName)
    TextView tvUserName;
    @ViewById(R.id.tv_show_number)
    TextView tvShowNumber;

    UserAuths userAuths;
    @AfterViews
    void init(){
        toolbar_left_image.setVisibility(View.VISIBLE);
        toolbar_left_image.setImageResource(R.drawable.nav_icon_back);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_title.setText("账户与安全");
        toolbar_right_image.setVisibility(View.GONE);
        initUserData();
    }

    private void initUserData() {
        UserInfo userInfo = SharedPreferenceUtils.getInstance(this).getUserInfo();
        if(userInfo!=null){
            tvUserName.setText(userInfo.getNick_name());
        }
        userAuths = SharedPreferenceUtils.getInstance(this).getUserAuths();
        if(userAuths!=null && userAuths.getIdentity_type().equals(Constants.TYPE_PHONE)){
            tvShowNumber.setText(userAuths.getIdentifier());
        }
    }

    @Click({R.id.toolbar_left_image,R.id.updatePassword})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                MFGT.finish(this);
                break;
            case R.id.updatePassword:
                if (!userAuths.getIdentity_type().equals(Constants.TYPE_PHONE)){
                    Toast.makeText(this, getResources().getString(R.string.other_login_warn), Toast.LENGTH_SHORT).show();
                }else{
                    MFGT.startActivity(this,PasswordActivity_.class);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
