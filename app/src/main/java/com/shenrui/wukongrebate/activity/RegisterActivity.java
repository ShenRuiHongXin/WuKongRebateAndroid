package com.shenrui.wukongrebate.activity;

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

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @ViewById(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @ViewById(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @ViewById(R.id.et_messageCode)
    EditText etMessageCode;
    @ViewById(R.id.et_password)
    EditText etPassword;
    @ViewById(R.id.et_rePassword)
    EditText etRePassword;
    @ViewById(R.id.btn_getCode)
    Button btnGetCode;
    @ViewById(R.id.btn_check)
    Button btnCheck;
    @ViewById(R.id.btn_register)
    Button btnRegister;

    String password;
    @AfterViews
    void init(){
        toolbar_left_image.setVisibility(View.GONE);
        toolbar_left_text.setText("关闭");
        toolbar_title.setText("注册");
        toolbar_right_image.setVisibility(View.GONE);
        toolbar_right_text.setVisibility(View.GONE);
    }
    @Click({R.id.toolbar_left_text,R.id.btn_getCode,R.id.btn_check,R.id.btn_register})
    void ClickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_text:
                finish();
                break;
            case R.id.btn_getCode:
                //获取短信验证码
                break;
            case R.id.btn_check:
                //开始验证

                break;
            case R.id.btn_register:
                if(checkPassword()){
                    //注册操作
                }
                break;
        }
    }

    private boolean checkPassword() {
        password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        if(password.isEmpty()){
            etPassword.setError("密码不能为空");
            etPassword.requestFocus();
            return false;
        }
        if(rePassword.isEmpty()){
            etRePassword.setError("确认密码不能为空");
            etRePassword.requestFocus();
            return false;
        }
        if(!rePassword.equals(password)){
            etRePassword.setError("两次密码不一致");
            etRePassword.requestFocus();
            return false;
        }
        return true;
    }
}
