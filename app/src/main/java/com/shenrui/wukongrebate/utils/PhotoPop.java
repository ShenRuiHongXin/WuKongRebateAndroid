package com.shenrui.wukongrebate.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.shenrui.wukongrebate.R;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/12/29.
 */
/**
 *   1.用PopupWindow显示悬浮窗口：包含拍照和从相册选取图片两个按钮
 *   2.启动系统的拍照Activity
 *   3.启动系统的从相册选取图片的Activity
 *   4.启动系统的裁剪的Activity
 *   5.处理拍照Activity、从相册选取Actviity和裁剪Activity返回的结果
 */
public class PhotoPop {
    private static final int ACTION_CAPTURE=0;
    private static final int ACTION_CHOOSE=1;
    private static final int ACTION_CROP=2;
    Activity context;
    PopupWindow popupWindow;
    File file;

    //需要pop窗弹出的父布局id
    public PhotoPop(Activity context, int parentId) {
        View parent = View.inflate(context, parentId, null);
        this.context = context;
        View layout = initPop(parent);
        setListener(layout);
    }

    private void setListener(View layout) {
        //拍照按钮的监听
        layout.findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });
        //从相册选取按钮的监听
        layout.findViewById(R.id.btn_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        //取消按钮的监听
        layout.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private View initPop(View parent) {
        View layout = View.inflate(context, R.layout.photo_pop_up, null);
        float density = context.getResources().getDisplayMetrics().density;
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, (int) density * 145);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.PhotoPopUp);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
        return layout;
    }

    public void startCamera() {
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        file = new File(dir, System.currentTimeMillis() + "");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        context.startActivityForResult(intent,ACTION_CAPTURE);
    }

    public void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        context.startActivityForResult(intent,ACTION_CHOOSE);
    }

    public void setPhoto(int requestCode, int resultCode,Intent data ,ImageView imageView){
        if(resultCode!=context.RESULT_OK){
            return;
        }
        switch (requestCode){
            case ACTION_CAPTURE:
                startCropActivity(Uri.fromFile(file),300,300);
                break;
            case ACTION_CHOOSE:
                startCropActivity(data.getData(),300,300);
                break;
            case ACTION_CROP:
                showPhoto(data, imageView);
                popupWindow.dismiss();
                break;
        }
    }

    private void showPhoto(Intent data, ImageView imageView) {
        //Bitmap bitmap = data.getParcelableExtra("data");
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(data.getData()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
    }
    //裁剪图片
    private void startCropActivity(Uri data, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");

        //intent.putExtra("outputX", outputX);
        //intent.putExtra("outputY", outputY);


        intent.putExtra("outputFormintent.putExtra(\"return-data\", true);at", Bitmap.CompressFormat.PNG.toString());

        context.startActivityForResult(intent,ACTION_CROP);
    }
}
