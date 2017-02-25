package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.os.Handler;
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
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@EActivity(R.layout.activity_find_password)
public class FindPasswordActivity extends BaseActivity {
    private static final int TIME_INT = 0;
    private static final int TIME_OVER = 1;
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @ViewById(R.id.toolbar_right_text)
    TextView tv_register;

    @ViewById(R.id.et_find_phoneNumber)
    EditText etPhoneNumber;
    @ViewById(R.id.et_find_messageCode)
    EditText etMessageCode;
    @ViewById(R.id.et_find_password)
    EditText etPassword;
    @ViewById(R.id.et_find_rePassword)
    EditText etRePassword;
    @ViewById(R.id.btn_find_getCode)
    Button btnGetCode;
    @ViewById (R.id.btn_find_check)
    Button btnCheck;
    @ViewById(R.id.btn_find_sure)
    Button btnSure;
    @ViewById(R.id.layout_find_type_two)
    LinearLayout layout_find_type_two;

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
                    Toast.makeText(FindPasswordActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
                    btnCheck.setBackgroundResource(R.drawable.radius_grey);
                    btnCheck.setText("已验证");
                    btnCheck.setClickable(false);
                    layout_find_type_two.setVisibility(View.VISIBLE);
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
                case TIME_INT:
                    btnGetCode.setBackgroundResource(R.drawable.radius_grey);
                    btnGetCode.setText(msg.arg2+"秒后重新发送");
                    btnGetCode.setClickable(false);
                    break;
                case TIME_OVER:
                    btnGetCode.setBackgroundResource(R.drawable.radius_green);
                    btnGetCode.setText("重新发送");
                    btnGetCode.setClickable(true);
                    break;
            }
        }
    };
    @AfterViews
    void init(){
        toolbar_left_image.setVisibility(View.GONE);
        toolbar_left_text.setText("关闭");
        toolbar_title.setText("找回密码");
        toolbar_right_image.setVisibility(View.GONE);
        tv_register.setVisibility(View.VISIBLE);
        tv_register.setText("注册");

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
    String password;
    String rePassword;
    String phoneNumber;
    @Click({R.id.btn_find_getCode,R.id.btn_find_check,R.id.btn_find_sure,
            R.id.toolbar_left_text,R.id.toolbar_right_text})
    void clienEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_text:
                MFGT.finish(this);
                break;
            case R.id.toolbar_right_text:
                startActivity(new Intent(this,RegisterActivity_.class));
                break;
            case R.id.btn_find_getCode:
                phoneNumber = etPhoneNumber.getText().toString();
                //获取验证码
                if(!phoneNumber.isEmpty()){
                    SMSSDK.getVerificationCode("86", phoneNumber);
                    Toast.makeText(this, "短信已发送", Toast.LENGTH_SHORT).show();
                    countTime();
                }
                break;
            case R.id.btn_find_check:
                String messageCode = etMessageCode.getText().toString();
                //开始验证
                if(!messageCode.isEmpty()){
                    SMSSDK.submitVerificationCode("86",phoneNumber,messageCode);
                }
                break;
            case R.id.btn_find_sure:
                //确认修改密码
                password = etPassword.getText().toString();
                rePassword = etRePassword.getText().toString();
                if(checkFormat()){
                    updatePassword();
                }
                break;
        }
    }
    //倒计时
    private void countTime() {
        new Thread(){
            @Override
            public void run() {
                for(int i = 60;i>=0;i--){
                    Message msg = Message.obtain();
                    msg.arg1 = TIME_INT;
                    msg.arg2 = i;
                    timeHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message message = Message.obtain();
                message.arg1 = TIME_OVER;
                timeHandler.sendMessage(message);
            }
        }.start();
    }

    private void updatePassword() {
        UserAuths userAuths = new UserAuths();
        userAuths.setIdentity_type("phone");
        userAuths.setIdentifier(phoneNumber);
        userAuths.setCredential(password);
        NetDao.updateUserAuths(this, userAuths, new OkHttpUtils.OnCompleteListener<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult result) {
                Log.e("DeDiWang",result.toString());
                if(result.getResult()!=null){
                    if(result.getResult().getCode() == Constants.CODE_SUCCESS){
                        Toast.makeText(FindPasswordActivity.this, "成功找回密码", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FindPasswordActivity.this, LoginActivity_.class).putExtra("phoneNumber", FindPasswordActivity.this.phoneNumber);
                        MFGT.startActivity(FindPasswordActivity.this,intent);
                        finish();
                    }else if (result.getResult().getCode() == Constants.LOGIN_PHONE_NO_REGISTER){
                        Toast.makeText(FindPasswordActivity.this, "该手机号还未注册", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private boolean checkFormat() {
        if(password.isEmpty()){
            etPassword.setError("密码不能为空");
            etPassword.requestFocus();
            return false;
        }
        if(!password.matches("[a-zA-Z0-9]{8,16}")){
            etPassword.setError("密码格式错误");
            etPassword.requestFocus();
            return false;
        }
        if(rePassword.isEmpty()){
            etRePassword.setError("密码不能为空");
            etRePassword.requestFocus();
            return false;
        }
        if(!password.equals(rePassword)){
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
        OkHttpUtils.release();
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
