package com.shenrui.wukongrebate.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_user_name)
public class UserNameActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @ViewById(R.id.et_username)
    EditText etUserName;
    @ViewById(R.id.iv_delete)
    ImageView ivDelete;
    @ViewById(R.id.btn_save)
    Button btnSave;

    @AfterViews
    void init(){
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
        toolbar_title.setText("会员名");
        toolbar_right_image.setVisibility(View.GONE);
        initUserData();
    }

    private void initUserData() {
        UserInfo userInfo = SharedPreferenceUtils.getInstance(this).getUserInfo();
        if(userInfo!=null){
            etUserName.setText(userInfo.getNick_name());
        }
    }

    @Click({R.id.toolbar_left_image,R.id.iv_delete,R.id.btn_save})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                finish();
                break;
            case R.id.iv_delete:
                if(etUserName.getText()!=null){
                    etUserName.setText("");
                }
                break;
            case R.id.btn_save:
                //修改数据库中用户名，并上传至服务器

                break;
        }
    }
}
