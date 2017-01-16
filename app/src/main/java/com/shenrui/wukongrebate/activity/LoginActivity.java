package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

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
        //如果从注册界面跳转过来的
        String number = getIntent().getStringExtra("phoneNumber");
        if(number!=null){
            etUserName.setText(number);
        }
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
                    login();
                }
                break;
        }
    }

    private void login() {
        NetDao.login(this, userName, password, new OkHttpUtils.OnCompleteListener<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult result) {
                Log.e("DeDiWang result",result.toString());
                if(result!=null){
                    int code = result.getResult().getCode();
                    if(code == Constants.CODE_SUCCESS){
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //登录成功后将用户信息保存在首选项中
                        SharedPreferenceUtils.getInstance(LoginActivity.this).putUserInfo(result.getUserInfo());
                        SharedPreferenceUtils.getInstance(LoginActivity.this).putUserAuths(result.getUserAuths());
                        finish();
                    }else if(code == Constants.LOGIN_PHONE_NO_REGISTER){
                        Toast.makeText(LoginActivity.this, "该手机号还未注册", Toast.LENGTH_SHORT).show();
                    }else if(code == Constants.LOGIN_PASSWORD_ERROR){
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private boolean CheckFormat() {
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        if(userName.isEmpty()){
            etUserName.setError("手机号不能为空");
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
