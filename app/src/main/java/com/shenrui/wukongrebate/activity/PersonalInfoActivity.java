package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.BitmapUtils;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.PhotoPop;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    UserInfo userInfo;

    @AfterViews
    void initView(){
        toolbar_left_image.setImageResource(R.drawable.nav_icon_back);
        toolbar_left_text.setVisibility(View.GONE);
        toolbar_right_image.setVisibility(View.GONE);
        toolbar_title.setText("个人资料");
        initUserData();
    }

    private void initUserData() {
        userInfo = SharedPreferenceUtils.getInstance(this).getUserInfo();
        if(userInfo.getAvatar()!=null){
            //http://192.168.0.4:8080/WukongServer/resources/UserAvatar/26.jpg
            Glide.with(this).load(Constants.HOST+userInfo.getAvatar()).into(avatar);
        }
    }

    @Click({R.id.toolbar_left_image,R.id.avatar,R.id.userName,R.id.userSex,R.id.shippingAddress})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.toolbar_left_image:
                MFGT.finish(this);
                break;
            case R.id.avatar:
                photoPop = new PhotoPop(this,R.layout.activity_personal_info);
                break;
            case R.id.userName:
                MFGT.startActivity(this,UserNameActivity_.class);
                break;
            case R.id.userSex:
                //修改性别
                showUpdateSexDialog();
                break;
            case R.id.shippingAddress:

                break;
        }
    }
    int newSex;

    private void showUpdateSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_update_sex, null);
        builder.setView(layout);
        TextView men = (TextView) layout.findViewById(R.id.tv_men);
        TextView women = (TextView) layout.findViewById(R.id.tv_women);
        final AlertDialog dialog = builder.create();
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSex = 1;
                dialog.dismiss();
                updateSex();
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSex = 2;
                dialog.dismiss();
                updateSex();
            }
        });
        dialog.show();
    }

    private void updateSex() {
        if(userInfo.getSex() == newSex){
            Toast.makeText(this, "性别未修改", Toast.LENGTH_SHORT).show();
        }else{
            userInfo.setSex(newSex);
            NetDao.updateUserInfo(this, userInfo, new OkHttpUtils.OnCompleteListener<ResponseResult>() {
                @Override
                public void onSuccess(ResponseResult result) {
                    if(result!=null && result.getResult().getCode() == Constants.CODE_SUCCESS){
                        Toast.makeText(PersonalInfoActivity.this, "修改性别成功", Toast.LENGTH_SHORT).show();
                        //将修改后的信息保存在首选项中
                        SharedPreferenceUtils.getInstance(PersonalInfoActivity.this).putUserInfo(result.getUserInfo());
                    }
                    Log.e("DeDiWang",result.toString());
                }

                @Override
                public void onError(String error) {
                    Log.e("error",error);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoPop.setPhoto(requestCode,resultCode,data,userInfo.getId());
        if(requestCode == 2){
            //获取裁剪后的头像文件
            File file = photoPop.getLastFile();
            if(file!=null){
                //如果图片过大则进行压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(),options);
                options.inSampleSize = calculateInSampleSize(options,300,300);
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
                File avatarFile = BitmapUtils.saveBitmap(bitmap, file.getPath());
                //更新头像
                updateAvatar(avatarFile);
            }
        }
    }
    @Background
    void updateAvatar(File file) {
        final Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JPEG, file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userInfo", userInfoJson)
                .addFormDataPart("avatar", file.getName(), requestBody)
                .build();
        final Request request = new Request.Builder()
                .url(Constants.SERVICE_URL + "user_update")
                .post(multipartBody)
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("DeDiWang",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                ResponseResult result = gson.fromJson(json, ResponseResult.class);
                Log.e("DeDiWang",result.toString());
                if(result.getResult()!=null && result.getResult().getCode() == Constants.CODE_SUCCESS){
                    updateUi(result);
                }
            }
        });
    }

    @UiThread
    void updateUi(ResponseResult result){
        Toast.makeText(this, "修改头像成功", Toast.LENGTH_SHORT).show();
        userInfo.setAvatar(result.getUserInfo().getAvatar());
        SharedPreferenceUtils.getInstance(this).putUserInfo(userInfo);
        Glide.with(this).load(Constants.HOST + result.getUserInfo().getAvatar()).into(avatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.release();
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

    //计算inSampleSize的值
    public static int calculateInSampleSize(BitmapFactory.Options option,int reqWidth,int reqHeight){
        //原图片的高度和宽度
        int height = option.outHeight;
        int width = option.outWidth;
        int inSampleSize = 1;
        //如果原图片宽度或高度大于要求的宽度和高度，则计算取样系数
        if(height>reqHeight || width>reqWidth){
            while ((width/inSampleSize)>=reqWidth
                    || (height/inSampleSize)>=reqHeight){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
