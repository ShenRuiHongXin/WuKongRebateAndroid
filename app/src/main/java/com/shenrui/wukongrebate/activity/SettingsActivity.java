package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.PhotoPop;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{
    ImageView ivAvatar;
    TextView tv_toolbar_left;
    ImageView iv_toolbar_left;
    TextView tv_toolbar_title;
    ImageView iv_toolbar_right;
    PhotoPop photoPop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        setListener();
    }

    private void setListener() {
        iv_toolbar_left.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }

    private void initView() {
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_toolbar_left = (TextView) findViewById(R.id.toolbar_left_text);
        iv_toolbar_left = (ImageView) findViewById(R.id.toolbar_left_image);
        iv_toolbar_right = (ImageView) findViewById(R.id.toolbar_right_image);
        tv_toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        iv_toolbar_left.setImageResource(R.drawable.common_btn_back_n);
        tv_toolbar_left.setVisibility(View.GONE);
        tv_toolbar_title.setText("设置");
        iv_toolbar_right.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_image:
                finish();
                break;
            case R.id.iv_avatar:
                photoPop = new PhotoPop(this,R.layout.activity_settings);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoPop.setPhoto(requestCode,resultCode,data,ivAvatar);
    }
}
