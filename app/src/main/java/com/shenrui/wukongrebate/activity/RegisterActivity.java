package com.shenrui.wukongrebate.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    private static final int TIME_ING = 0;
    private static final int TIME_OVER = 1;
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @ViewById(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @ViewById(R.id.et_messageCode)
    EditText etMessageCode;
    @ViewById(R.id.et_nick)
    EditText etNick;
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
    @ViewById(R.id.layout_type_two)
    LinearLayout layout_type_two;
    String password;
    String nick;
    String phoneNumber;
    String messageCode;
    EventHandler eh;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Toast.makeText(RegisterActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
                    layout_type_two.setVisibility(View.VISIBLE);
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };
    Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                case TIME_ING:
                    btnGetCode.setText(msg.arg2+"秒后重新发送");
                    break;
                case TIME_OVER:
                    btnGetCode.setClickable(true);
                    btnGetCode.setText("重新发送");
                    break;
            }
        }
    };
    @AfterViews
    void init(){
        toolbar_left_image.setVisibility(View.GONE);
        toolbar_left_text.setText("关闭");
        toolbar_title.setText("注册");
        toolbar_right_image.setVisibility(View.GONE);

        //initSMS();
        eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = Message.obtain();
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                handler.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    @Click({R.id.toolbar_left_text,R.id.btn_getCode,R.id.btn_check,R.id.btn_register})
    void ClickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_text:
                finish();
                break;
            case R.id.btn_getCode:
                phoneNumber = etPhoneNumber.getText().toString();
                //获取短信验证码
                if(!phoneNumber.isEmpty()){
                    SMSSDK.getVerificationCode("86", phoneNumber);
                    Toast.makeText(this, "短信已发送", Toast.LENGTH_SHORT).show();
                    countTime();
                }
                break;
            case R.id.btn_check:
                messageCode = etMessageCode.getText().toString();
                //开始验证
                if(!messageCode.isEmpty()){
                    SMSSDK.submitVerificationCode("86",phoneNumber,messageCode);
                }
                break;
            case R.id.btn_register:
                if(checkPassword()){
                    //注册操作

                }
                break;
        }
    }

    //倒计时
    private void countTime() {
        btnGetCode.setClickable(false);
        btnGetCode.setBackgroundColor(getResources().getColor(R.color.mainGrey));
        new Thread(){
            @Override
            public void run() {
                for(int i=60;i>=0;i--){
                    try {
                        Message message = Message.obtain();
                        message.arg1= TIME_ING;
                        message.arg2=i;
                        timeHandler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = Message.obtain();
                msg.arg1= TIME_OVER;
                timeHandler.sendMessage(msg);
            }
        }.start();
    }

    private boolean checkPassword() {
        nick = etNick.getText().toString();
        password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        if(nick.isEmpty()){
            etNick.setError("密码不能为空");
            etNick.requestFocus();
            return false;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
        handler = null;
        timeHandler = null;
    }
}
