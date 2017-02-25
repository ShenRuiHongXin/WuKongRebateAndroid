package com.shenrui.wukongrebate.activity;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_common)
public class CommonActivity extends BaseActivity {
    @ViewById(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @ViewById(R.id.toolbar_left_text)
    TextView toolbar_left_text;
    @ViewById(R.id.toolbar_title)
    TextView toolbar_title;
    @ViewById(R.id.toolbar_right_image)
    ImageView toolbar_right_image;

    @ViewById(R.id.iv_open_local_service)
    ImageView ivLocalService;
    @ViewById(R.id.clear_cache)
    LinearLayout clearCacle;
    @AfterViews
    void initView() {
        toolbar_left_image.setVisibility(View.VISIBLE);
        toolbar_left_image.setImageResource(R.drawable.nav_icon_back);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_title.setText("通用");
        toolbar_right_image.setVisibility(View.GONE);
    }
    boolean isOpenLocalService = true;//默认开启位置服务
    @Click({R.id.toolbar_left_image,R.id.iv_open_local_service,R.id.clear_cache})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                MFGT.finish(this);
                break;
            case R.id.iv_open_local_service:
                isOpenLocalService = !isOpenLocalService;
                switchService();
                break;
            //清除缓存
            case R.id.clear_cache:
                clearCache();
                break;
        }
    }

    private void clearCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_clear_cache, null);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();
        layout.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.btnSure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除缓存

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void switchService() {
        if(isOpenLocalService){
            ivLocalService.setImageResource(R.drawable.mine_btn_yes);
            //开启位置服务

        }else{
            ivLocalService.setImageResource(R.drawable.mine_btn_no);
            //关闭位置服务

        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
