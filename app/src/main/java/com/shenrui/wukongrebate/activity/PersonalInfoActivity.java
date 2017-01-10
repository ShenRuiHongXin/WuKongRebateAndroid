package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.PhotoPop;

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

                break;
            case R.id.userSex:

                break;
            case R.id.shippingAddress:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoPop.setPhoto(requestCode,resultCode,data,avatar);
    }
}
