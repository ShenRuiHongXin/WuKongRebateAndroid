package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by heikki on 2017/2/14.
 */

@EViewGroup(R.layout.jifen_task_view)
public class JifenTaskView extends LinearLayout {
    @ViewById(R.id.tv_jf_task_sign_count)
    TextView tvSignJfCount;
    @ViewById(R.id.tv_jf_task_sign)
    TextView tvSign;
    @ViewById(R.id.tv_jf_task_share_count)
    TextView tvShareJfCount;
    @ViewById(R.id.tv_jf_task_share)
    TextView tvShare;

    public JifenTaskView(Context context) {
        super(context);
    }

    public JifenTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click({R.id.tv_jf_task_sign,R.id.tv_jf_task_share})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.tv_jf_task_sign:

                break;
            case R.id.tv_jf_task_share:
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
        oks.setText("搜索淘宝/天猫宝贝，拿悟空返利");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://img.taopic.com/uploads/allimg/120820/214833-120R0222J553.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我在用悟空返利这款APP,非常好用，你也来买买买吧！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("悟空返利");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(getContext());
    }
}
