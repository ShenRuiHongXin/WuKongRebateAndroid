package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;

    @ViewById(R.id.et_userNumber)
    EditText etUserName;
    @ViewById(R.id.et_userPassword)
    EditText etPassword;
    @ViewById(R.id.btn_login)
    Button btnLogin;
    @ViewById(R.id.btn_register)
    Button btnRegister;
    String userName,password;
    @AfterViews
    void init(){
        toolbar_left_image.setVisibility(View.GONE);
        toolbar_left_text.setText("关闭");
        toolbar_title.setText("登录");
        toolbar_right_image.setVisibility(View.GONE);
    }
    @Click({R.id.toolbar_left_text,R.id.btn_register,R.id.btn_login})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_text:
                finish();
                break;
            case R.id.btn_register:
                //进入注册界面
                startActivity(new Intent(this,RegisterActivity_.class));
                finish();
                break;
            case R.id.btn_login:
                if(CheckFormat()){
                    //登录操作
                }
                break;
        }
    }

    private boolean CheckFormat() {
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        if(userName.isEmpty()){
            etUserName.setError("用户名不能为空");
            etUserName.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            etPassword.setError("密码不能为空");
            etUserName.requestFocus();
            return false;
        }
        return true;
    }
}
