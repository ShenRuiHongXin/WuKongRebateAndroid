package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.PhotoPop;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_personal_info)
public class PersonalInfoActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @ViewById(R.id.avatar)
    ImageView avatar;
    @ViewById(R.id.userName)
    LinearLayout userName;
    @ViewById(R.id.userSex)
    LinearLayout userSex;
    @ViewById(R.id.shippingAddress)
    LinearLayout shippingAddress;
    PhotoPop photoPop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @AfterViews
    void initView(){
        toolbar_left_image.setImageResource(R.drawable.common_btn_back_n);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_right_image.setVisibility(View.GONE);
        toolbar_title.setText("个人资料");
        initUserData();
    }

    private void initUserData() {
        UserInfo userInfo = SharedPreferenceUtils.getInstance(this).getUserInfo();
        if(userInfo.getAvatar()!=null){
            Glide.with(this).load(userInfo.getAvatar()).into(avatar);
        }
    }

    @Click({R.id.toolbar_left_image,R.id.avatar,R.id.userName,R.id.userSex,R.id.shippingAddress})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                finish();
                break;
            case R.id.avatar:
                photoPop = new PhotoPop(this,R.layout.activity_personal_info);
                break;
            case R.id.userName:
                startActivity(new Intent(this,UserNameActivity_.class));
                break;
            case R.id.userSex:
                //修改性别
                updateSex();
                break;
            case R.id.shippingAddress:

                break;
        }
    }

    private void updateSex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_update_sex, null);
        builder.setView(layout);
        TextView men = (TextView) layout.findViewById(R.id.tv_men);
        TextView women = (TextView) layout.findViewById(R.id.tv_women);
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalInfoActivity.this, "男", Toast.LENGTH_SHORT).show();
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalInfoActivity.this, "女", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoPop.setPhoto(requestCode,resultCode,data,avatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserData();
    }
}
