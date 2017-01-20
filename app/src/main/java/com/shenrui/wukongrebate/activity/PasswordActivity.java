package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_password)
public class PasswordActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;

    @ViewById(R.id.et_oldPassword)
    EditText etOldPassword;
    @ViewById(R.id.et_newPassword)
    EditText etNewPassword;
    @ViewById(R.id.btn_savePassword)
    Button btnSave;

    UserAuths userAuths;
    @AfterViews
    void initViews(){
        toolbar_left_image.setVisibility(View.VISIBLE);
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_title.setText("修改登录密码");
        toolbar_right_image.setVisibility(View.GONE);
        userAuths = SharedPreferenceUtils.getInstance(this).getUserAuths();
    }
    String oldPassword;
    String newPassword;
    @Click({R.id.btn_savePassword,R.id.toolbar_left_image})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.btn_savePassword:
                oldPassword = etOldPassword.getText().toString();
                newPassword = etNewPassword.getText().toString();
                if(checkFormat()){
                    updateAuths();
                }
                break;
            case R.id.toolbar_left_image:
                finish();
                break;
        }

    }

    private void updateAuths() {
        userAuths.setCredential(newPassword);
        NetDao.updateUserAuths(this, userAuths, new OkHttpUtils.OnCompleteListener<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult result) {
                Log.e("DeDiWang",result.toString());
                if(result.getResult()!=null && result.getResult().getCode() == Constants.CODE_SUCCESS){
                    Toast.makeText(PasswordActivity.this, "修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                    SharedPreferenceUtils.getInstance(PasswordActivity.this).clearAll();
                    //跳到登录界面
                    startActivity(new Intent(PasswordActivity.this,LoginActivity_.class).putExtra("phoneNumber",userAuths.getIdentifier()));
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("DeDiWang",error);
            }
        });
    }

    private boolean checkFormat() {
        if(oldPassword.isEmpty()){
            etOldPassword.setError("原密码不能为空");
            etOldPassword.requestFocus();
            return false;
        }
        if(newPassword.isEmpty()){
            etNewPassword.setError("新密码不能为空");
            etNewPassword.requestFocus();
            return false;
        }
        if(!oldPassword.equals(userAuths.getCredential())){
            Toast.makeText(this, "原密码不正确", Toast.LENGTH_SHORT).show();
            etOldPassword.requestFocus();
            return false;
        }
        if(oldPassword.equals(newPassword)){
            Toast.makeText(this, "新密码不能与原密码一致", Toast.LENGTH_SHORT).show();
            etNewPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.release();
    }
}
