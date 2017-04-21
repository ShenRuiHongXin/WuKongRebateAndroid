package com.shenrui.wukongrebate.activity;

import android.view.View;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.InvateCodeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by heikki on 2017/4/13.
 */

@EActivity(R.layout.activity_invation_award)
public class InvationAwardActivity extends BaseActivity {
    @ViewById(R.id.icv_invate)
    InvateCodeView invateCodeView;

    @AfterViews
    void init(){
        invateCodeView.setInvateCode("6EBF7");
    }

    @Click({R.id.iv_invation_back,R.id.btn_invate})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_invation_back:
                MFGT.finish(this);
                break;
            case R.id.btn_invate:
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("悟空返利");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我在用悟空返利这款APP,非常好用，你也来买买买吧！填写我的邀请码还可以拿红包哦！我的邀请码:6EBF7");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://img.taopic.com/uploads/allimg/120820/214833-120R0222J553.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我在用悟空返利这款APP,非常好用，你也来买买买吧！填写我的邀请码还可以拿红包哦！我的邀请码:6EBF7");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("悟空返利");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
