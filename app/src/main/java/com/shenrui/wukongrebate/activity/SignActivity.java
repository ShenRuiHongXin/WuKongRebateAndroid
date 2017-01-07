package com.shenrui.wukongrebate.activity;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MainViewPagerAdapter;
import com.shenrui.wukongrebate.fragment.FragmentAward_;
import com.shenrui.wukongrebate.fragment.FragmentExechange_;
import com.shenrui.wukongrebate.fragment.FragmentSign_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分签到界面
 * Created by heikki on 2017/1/3.
 */

@EActivity(R.layout.activity_sign)
public class SignActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    @ViewById(R.id.vp_sign_content)
    ViewPager vp_sign_content;

    //底部
    @ViewsById({R.id.ll_exchange,R.id.ll_sign,R.id.ll_award})
    List list_sign_ll;

    @ViewsById({R.id.iv_exchange,R.id.iv_sign,R.id.iv_award})
    List list_sign_iv;

    @ViewsById({R.id.tv_exchange,R.id.tv_sign,R.id.tv_award})
    List list_sign_tv;

    private List<Fragment> fragmentList;
    // ViewPager适配器MainViewPagerAdapter
    private MainViewPagerAdapter adapter;

    @AfterViews
    void init(){
        //标题栏
        listTitleView.get(3).setVisibility(View.GONE);
        ((TextView)listTitleView.get(2)).setText("签到积分");
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.common_btn_back_n);
        listTitleView.get(0).setVisibility(View.GONE);

        vp_sign_content.addOnPageChangeListener(this);

        fragmentList   = new ArrayList<Fragment>();

        fragmentList.add(new FragmentExechange_());
        fragmentList.add(new FragmentSign_());
        fragmentList.add(new FragmentAward_());

        this.adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp_sign_content.setAdapter(adapter);
        selectPage(1);
    }

    @Click({R.id.ll_exchange,R.id.ll_sign,R.id.ll_award,R.id.toolbar_left_image})
    void clickEvent(View view){
        resetView();
        int tmpPosition = 0;
        switch (view.getId()){
            case R.id.ll_exchange:
                tmpPosition = 0;
                break;
            case R.id.ll_sign:
                tmpPosition = 1;
                break;
            case R.id.ll_award:
                tmpPosition = 2;
                break;
            case R.id.toolbar_left_image:
                finish();
                return;
        }
        selectPage(tmpPosition);
    }

    //重置界面
    private void resetView() {
        //
        ((ImageView)list_sign_iv.get(0)).setImageResource(R.drawable.index_sign_btn_award_n);
        ((ImageView)list_sign_iv.get(1)).setImageResource(R.drawable.index_sign_btn_task_n);
        ((ImageView)list_sign_iv.get(2)).setImageResource(R.drawable.index_sign_btn_integral_n);
        //
        for (Object tv:list_sign_tv) {
            ((TextView)tv).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
        }
//        ((TextView)list_sign_tv.get(0)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
//        ((TextView)list_sign_tv.get(1)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
//        ((TextView)list_sign_tv.get(2)).setTextColor(ContextCompat.getColor(this, R.color.mainGrey));
    }

    //设置选中页
    private void selectPage(int position){
        switch (position) {
            case 0:
                ((ImageView)list_sign_iv.get(0)).setImageResource(R.drawable.index_sign_reward_btn_expiry_s);
                ((TextView)listTitleView.get(2)).setText("积分兑换");
                vp_sign_content.setCurrentItem(0);
                break;
            case 1:
                ((ImageView)list_sign_iv.get(1)).setImageResource(R.drawable.index_sign_reward_btn_task_s);
                ((TextView)listTitleView.get(2)).setText("签到积分");
                vp_sign_content.setCurrentItem(1);
                break;
            case 2:
                ((ImageView)list_sign_iv.get(2)).setImageResource(R.drawable.index_sign_btn_integral_s);
                ((TextView)listTitleView.get(2)).setText("悟空积分");
                vp_sign_content.setCurrentItem(2);
                break;
               }
        ((TextView)list_sign_tv.get(position)).setTextColor(ContextCompat.getColor(this, R.color.mainRed));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetView();
        //
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
