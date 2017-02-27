package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.IntegralLuckDrawActivity_;
import com.shenrui.wukongrebate.activity.LoginActivity_;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2017/1/3.
 */

@EFragment(R.layout.fragment_award)
public class FragmentAward extends BaseFragment {
    @ViewById(R.id.iv_award_avatar)
    ImageView ivAvatar;
    @ViewById(R.id.tv_award_problem)
    TextView tvAwardProblem;
    @ViewById(R.id.tv_award_userName)
    TextView tvUserName;
    @ViewById(R.id.tv_award_integral)
    TextView tvIntegral;
    @ViewById(R.id.award_record)
    RelativeLayout awardRecord;
    @ViewById(R.id.award_details)
    RelativeLayout awardDetails;
    @ViewById(R.id.tv_award_days)
    TextView tvDays;
    @ViewById(R.id.award_gift)
    RelativeLayout awardGift;
    @ViewById(R.id.forPhoneBill)
    RelativeLayout forPhoneBill;

    Context context;
    UserInfo userInfo;
    @AfterViews
    void init(){
        context = getContext();
        initUserData();
    }

    boolean isLogin;
    private void initUserData() {
        userInfo = SharedPreferenceUtils.getInstance(context).getUserInfo();
        if(userInfo!=null){
            isLogin = true;
            tvUserName.setText(userInfo.getNick_name());
            tvIntegral.setText(String.valueOf(userInfo.getIntegral()));
            if(userInfo.getAvatar()!=null){
                Glide.with(context).load(Constants.HOST+userInfo.getAvatar()).into(ivAvatar);
            }else{
                ivAvatar.setImageResource(R.drawable.mine_avatar);
            }
        }else{
            isLogin = false;
            tvUserName.setText("登录/注册");
            tvIntegral.setText("xxxx");
            ivAvatar.setImageResource(R.drawable.mine_avatar);
        }
    }

    @Click({R.id.iv_award_avatar,R.id.tv_award_problem,R.id.tv_award_userName,
            R.id.award_record,R.id.award_details,R.id.award_gift,R.id.forPhoneBill})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_award_avatar:
            case R.id.tv_award_userName:
                if(!isLogin){
                    MFGT.startActivity(context,LoginActivity_.class);
                }
                break;
            case R.id.tv_award_problem://积分规则

                break;
            case R.id.award_record://兑换记录
                if(!isLogin){
                    MFGT.startActivity(context,LoginActivity_.class);
                }
                break;
            case R.id.award_details://积分明细
                if(!isLogin){
                    MFGT.startActivity(context,LoginActivity_.class);
                }
                break;
            case R.id.award_gift://积分抽奖
                if(!isLogin){
                    MFGT.startActivity(context,LoginActivity_.class);
                }else{
                    MFGT.startActivity(context, IntegralLuckDrawActivity_.class);
                }
                break;
            case R.id.forPhoneBill://兑话费
                if(!isLogin){
                    MFGT.startActivity(context,LoginActivity_.class);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserData();
    }
}
