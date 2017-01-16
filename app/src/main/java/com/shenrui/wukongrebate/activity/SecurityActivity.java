package com.shenrui.wukongrebate.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.entities.UserInfo;
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
    @AfterViews
    void init(){
        toolbar_left_image.setVisibility(View.VISIBLE);
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
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
        UserAuths userAuths = SharedPreferenceUtils.getInstance(this).getUserAuths();
        if(userAuths!=null){
            tvShowNumber.setText(userAuths.getIdentifier());
        }
    }

    @Click({R.id.toolbar_left_image,R.id.updateNumber,R.id.updatePassword})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                finish();
                break;
            //修改手机号码
            case R.id.updateNumber:

                break;
            //修改登录密码
            case R.id.updatePassword:

                break;
        }
    }
}
