package com.shenrui.wukongrebate.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.NineDrawView;
import com.shenrui.wukongrebate.view.WkYuanbaoTicket;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 积分签到界面
 * Created by heikki on 2017/1/3.
 */

@EActivity(R.layout.activity_sign)
public class SignActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    //优惠券
    @ViewById(R.id.wyt_1)
    WkYuanbaoTicket wkYuanbaoTicket;
    @ViewById(R.id.wyt_2)
    WkYuanbaoTicket wkYuanbaoTicket2;

    //
    @ViewById(R.id.crv_sign)
    CycleRotationView crvSign;
    //抽奖
    @ViewById(R.id.ndv_luck_draw)
    NineDrawView nineDrawView;

//    @ViewById(R.id.vp_sign_content)
//    ViewPager vp_sign_content;
//
//    //底部
//    @ViewsById({R.id.ll_exchange,R.id.ll_award})
//    List list_sign_ll;
//
//    @ViewsById({R.id.iv_exchange,R.id.iv_award})
//    List list_sign_iv;
//
//    @ViewsById({R.id.tv_exchange,R.id.tv_award})
//    List list_sign_tv;

//    private List<Fragment> fragmentList;
    // ViewPager适配器MainViewPagerAdapter
//    private MainViewPagerAdapter adapter;

    @AfterViews
    void init(){
        //标题栏
        ((ImageView)listTitleView.get(3)).setImageResource(R.drawable.nav_icon_share);
        ((TextView)listTitleView.get(2)).setText("签到领积分");
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.nav_icon_back);
        listTitleView.get(0).setVisibility(View.GONE);
        wkYuanbaoTicket.setYbData(1,1000);
        wkYuanbaoTicket2.setYbData(2,2000);
        LogUtil.d("优惠券信息:" + wkYuanbaoTicket.getYbValue() + "," + wkYuanbaoTicket.getYbPrice());
        crvSign.setImages(new int[]{R.drawable.sign_banner,R.drawable.sign_banner,R.drawable.sign_banner});
        int[]prizesIcon={R.drawable.tmp_1,R.drawable.tmp_10,R.drawable.tmp_2,R.drawable.tmp_3,R.drawable.tmp_go,R.drawable.tmp_non,R.drawable.tmp_non,R.drawable.tmp_10rmb,R.drawable.tmp_50};
        nineDrawView.setPrizeImage(prizesIcon);
//        vp_sign_content.addOnPageChangeListener(this);
//
//        fragmentList   = new ArrayList<Fragment>();
//
//        fragmentList.add(new FragmentExechange_());
////        fragmentList.add(new FragmentSign_());
//        fragmentList.add(new FragmentAward_());
//
//        this.adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragmentList);
//        vp_sign_content.setAdapter(adapter);
//
//        selectPage(1);
    }

    @Click({R.id.toolbar_left_image,R.id.toolbar_right_image})
    void clickEvent(View view){
//        resetView();
//        int tmpPosition = 0;
        switch (view.getId()){
//            case R.id.ll_exchange:
//                tmpPosition = 0;
//                break;
//            case R.id.ll_sign:
//                tmpPosition = 1;
//                break;
//            case R.id.ll_award:
//                tmpPosition = 1;
//                break;
            case R.id.toolbar_left_image:
                MFGT.finish(this);
                return;
            case R.id.toolbar_right_image:
                showShare();
                break;
        }
//        selectPage(tmpPosition);
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
        oks.setText("我在用悟空返利这款APP,非常好用，你也来买买买吧！");
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
        oks.show(this);
    }

//    //重置界面
//    private void resetView() {
//        ((ImageView)list_sign_iv.get(0)).setImageResource(R.drawable.index_sign_btn_award_n);
////        ((ImageView)list_sign_iv.get(1)).setImageResource(R.drawable.index_sign_btn_task_n);
//        ((ImageView)list_sign_iv.get(1)).setImageResource(R.drawable.index_sign_btn_integral_n);
//        //
//        for (Object tv:list_sign_tv) {
//            ((TextView)tv).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
//        }
////        ((TextView)list_sign_tv.get(0)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
////        ((TextView)list_sign_tv.get(1)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
////        ((TextView)list_sign_tv.get(2)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
//    }

//    //设置选中页
//    private void selectPage(int position){
//        switch (position) {
//            case 0:
//                ((ImageView)list_sign_iv.get(0)).setImageResource(R.drawable.index_sign_reward_btn_expiry_s);
//                ((TextView)listTitleView.get(2)).setText("积分兑换");
//                vp_sign_content.setCurrentItem(0);
//                break;
////            case 1:
////                ((ImageView)list_sign_iv.get(1)).setImageResource(R.drawable.index_sign_reward_btn_task_s);
////                ((TextView)listTitleView.get(2)).setText("签到积分");
////                vp_sign_content.setCurrentItem(1);
////                break;
//            case 1:
//                ((ImageView)list_sign_iv.get(1)).setImageResource(R.drawable.index_sign_btn_integral_s);
//                ((TextView)listTitleView.get(2)).setText("悟空积分");
//                vp_sign_content.setCurrentItem(1);
//                break;
//               }
//        ((TextView)list_sign_tv.get(position)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        resetView();
//        //
//        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            vp_sign_content.setBackgroundColor(ContextCompat.getColor(this,R.color.category_bg));
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
