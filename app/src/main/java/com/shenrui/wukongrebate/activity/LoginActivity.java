package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
    @ViewById(R.id.toolbar_right_text)
    TextView tv_register;

    @ViewById(R.id.tv_find_password)
    TextView tvFindPassword;
    @ViewById(R.id.et_userNumber)
    EditText etUserName;
    @ViewById(R.id.et_userPassword)
    EditText etPassword;
    @ViewById(R.id.btn_login)
    Button btnLogin;

    Context context;
    String userName,password;
    static Handler loginHandler = null;//用于第三方登录的Handler
    String userId,userNick,userIcon,userPassword,loginType;//三方帐号信息
    @AfterViews
    void init(){
        toolbar_left_image.setVisibility(View.GONE);
        toolbar_left_text.setText("关闭");
        toolbar_title.setText("登录");
        toolbar_right_image.setVisibility(View.GONE);
        tv_register.setVisibility(View.VISIBLE);
        tv_register.setText("注册");
        context = this;
        //如果从注册界面跳转过来的
        String number = getIntent().getStringExtra("phoneNumber");
        if(number!=null){
            etUserName.setText(number);
        }
        initHandler();
    }

    private void initHandler() {
        loginHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==999){
                    otherLogin();
                }
            }
        };
    }

    private void otherLogin() {
        NetDao.otherLogin(context, loginType, userId, userNick, userIcon,userPassword, new OkHttpUtils.OnCompleteListener<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult result) {
                Log.e("DeDiWang result",result.toString());
                int code = result.getResult().getCode();
                if(code == Constants.CODE_SUCCESS){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登录成功后将用户信息保存在首选项中
                    SharedPreferenceUtils.getInstance(LoginActivity.this).putUserInfo(result.getUserInfo());
                    SharedPreferenceUtils.getInstance(LoginActivity.this).putUserAuths(result.getUserAuths());
                    MFGT.finish(LoginActivity.this);
                }else{
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("DeDiWang error",error);
            }
        });
    }

    @Click({R.id.toolbar_left_text,R.id.toolbar_right_text,R.id.btn_login,R.id.tv_find_password,
            R.id.iv_taobao,R.id.iv_sina,R.id.iv_qq,R.id.iv_wechat})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_text:
                MFGT.finish(this);
                break;
            case R.id.toolbar_right_text:
                //进入注册界面
                MFGT.startActivity(this,RegisterActivity_.class);
                finish();
                break;
            case R.id.btn_login:
                if(CheckFormat()){
                    //登录操作
                    login();
                }
                break;
            case R.id.tv_find_password:
                MFGT.startActivity(this,FindPasswordActivity_.class);
                finish();
                break;
            case R.id.iv_taobao://淘宝登录
                taobaoLogin();
                break;
            case R.id.iv_sina://新浪微博登录
                otherLogin(Constants.SINA);
                break;
            case R.id.iv_qq://qq登录
                otherLogin(Constants.QQ);
                break;
            case R.id.iv_wechat:
                otherLogin(Constants.WECHAT);
                break;
        }
    }

    private void taobaoLogin() {
        AlibcLogin.getInstance().showLogin(this, new AlibcLoginCallback() {
            @Override
            public void onSuccess() {
                Session session = AlibcLogin.INSTANCE.getSession();
                Log.e("DeDiWang session",session.toString());
                String openId = session.openId;
                String nick = session.nick;
                String icon = session.avatarUrl;
                NetDao.otherLogin(context, Constants.TAOBAO, openId, nick, icon, null, new OkHttpUtils.OnCompleteListener<ResponseResult>() {
                    @Override
                    public void onSuccess(ResponseResult result) {
                        Log.e("DeDiWang result",result.toString());
                        int code = result.getResult().getCode();
                        if(code == Constants.CODE_SUCCESS){
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            //登录成功后将用户信息保存在首选项中
                            SharedPreferenceUtils.getInstance(LoginActivity.this).putUserInfo(result.getUserInfo());
                            SharedPreferenceUtils.getInstance(LoginActivity.this).putUserAuths(result.getUserAuths());
                            MFGT.finish(LoginActivity.this);
                        }else{
                            Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void otherLogin(final String logintype) {
        Platform platform = null;
        switch (logintype){
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
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        PlatformActionListener listener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //输出所有授权信息
                PlatformDb db = platform.getDb();
                String s = db.exportData();
                Log.e("DeDiWang", "输出所有授权信息" + s);
                userId = db.getUserId();
                userNick = db.getUserName();
                userIcon = db.getUserIcon();
                userPassword = db.getToken();
                loginType = logintype;
                Message message = Message.obtain();
                message.what = 999;
                loginHandler.sendMessage(message);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                throwable.printStackTrace();
                Log.e("DeDiWang","onError"+platform.getDb().exportData());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e("DeDiWang","onCancel"+platform.getDb().exportData());
            }
        };
        //判断该平台是否已经授权
        Log.e("DeDiWang",platform.isAuthValid()+"");
        platform.setPlatformActionListener(listener);
        if (platform.isAuthValid()){
            //已经授权，直接从数据库拿取用户信息登录
            PlatformDb db = platform.getDb();
            userId = db.getUserId();
            userNick = db.getUserName();
            userIcon = db.getUserIcon();
            userPassword = db.getToken();
            loginType = logintype;
            otherLogin();
            Log.e("DeDiWang","已经授权，直接从数据库拿取用户信息登录");
        }else {
            //authorize与showUser单独调用一个即可
            //qq.authorize();//单独授权,OnComplete返回的hashmap是空的
            platform.showUser(null);//授权并获取用户信息
            //移除授权
            //qq.removeAccount(true);
        }
    }

    private void login() {
        NetDao.login(this, userName, password, new OkHttpUtils.OnCompleteListener<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult result) {
                Log.e("DeDiWang result",result.toString());
                int code = result.getResult().getCode();
                if(code == Constants.CODE_SUCCESS){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登录成功后将用户信息保存在首选项中
                    SharedPreferenceUtils.getInstance(LoginActivity.this).putUserInfo(result.getUserInfo());
                    SharedPreferenceUtils.getInstance(LoginActivity.this).putUserAuths(result.getUserAuths());
                    MFGT.finish(LoginActivity.this);
                }else if(code == Constants.LOGIN_PHONE_NO_REGISTER){
                    Toast.makeText(LoginActivity.this, "该手机号还未注册", Toast.LENGTH_SHORT).show();
                }else if(code == Constants.LOGIN_PASSWORD_ERROR){
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.release();
        loginHandler = null;
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }
}
