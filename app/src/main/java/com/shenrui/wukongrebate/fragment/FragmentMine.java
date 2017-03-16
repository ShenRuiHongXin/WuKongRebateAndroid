package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.LoginActivity_;
import com.shenrui.wukongrebate.activity.PersonalInfoActivity_;
import com.shenrui.wukongrebate.activity.SettingsActivity_;
import com.shenrui.wukongrebate.activity.TestActivity_;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;
import com.taobao.api.AliSdkOrderActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.mine_fragment_page)
public class FragmentMine extends BaseFragment{
    @ViewById(R.id.mine_srl)
    SwipeRefreshLayout srl;
    @ViewById(R.id.iv_mine_avatar)
    ImageView iv_avatar;
    @ViewById(R.id.tv_mine_userName)
    TextView tv_user_name;
    @ViewById(R.id.tv_money_fanli)
    TextView tv_money_fanli;
    @ViewById(R.id.tv_money_yue)
    TextView tv_money_yue;

    Context context;
    boolean isLogined;
    UserInfo userInfo;
    UserAuths userAuths;

    @AfterViews
    void init(){
        context = getContext();
        initUserData();
        setListener();
    }

    private void initUserData() {
        userInfo = SharedPreferenceUtils.getInstance(context).getUserInfo();
        userAuths = SharedPreferenceUtils.getInstance(context).getUserAuths();
        if(userInfo!=null){
            isLogined = true;
            //用户已登录,设置用户昵称，性别，头像,可用余额
            tv_user_name.setText(userInfo.getNick_name());
            tv_money_yue.setText(String.valueOf(userInfo.getBalance()));
            if(userInfo.getAvatar()!=null){
                if (!userAuths.getIdentity_type().equals("phone")){
                    Glide.with(this).load(userInfo.getAvatar()).into(iv_avatar);
                }else{
                    Glide.with(this).load(Constants.HOST + userInfo.getAvatar()).into(iv_avatar);
                }
            }else{
                iv_avatar.setImageResource(R.drawable.home_img_user);
            }
        }else{//当前无用户
            isLogined = false;
            tv_user_name.setText("登录/注册");
            iv_avatar.setImageResource(R.drawable.mine_avatar);
            tv_money_yue.setText("xx");
        }
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
    }

    private void setListener() {
        //刷新用户信息
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLogined){
                    NetDao.login(context, userAuths.getIdentifier(), userAuths.getCredential(), new OkHttpUtils.OnCompleteListener<ResponseResult>() {
                        @Override
                        public void onSuccess(ResponseResult result) {
                            if(result.getResult()!=null && result.getResult().getCode() == Constants.CODE_SUCCESS){
                                SharedPreferenceUtils.getInstance(context).putUserInfo(result.getUserInfo());
                                initUserData();
                                srl.setRefreshing(false);
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }else{
                    srl.setRefreshing(false);
                }
            }
        });
    }

    @Click({R.id.iv_mine_news,R.id.iv_mine_set,R.id.tv_see,R.id.tv_all_order,R.id.tv_yuanbao,
            R.id.tv_huiyuantong,R.id.tv_youhuiquan,R.id.tv_yaoqingyoujiang,R.id.tv_kefu,R.id.tv_qita,
            R.id.iv_mine_avatar,R.id.tv_mine_userName})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.tv_mine_userName:
            case R.id.iv_mine_avatar:
                if(isLogined){
                    MFGT.startActivity(context,PersonalInfoActivity_.class);
                }else{
                    MFGT.startActivity(context,LoginActivity_.class);
                }
                break;
            case R.id.iv_mine_news:
                MFGT.startActivity(context, TestActivity_.class);
                break;
            case R.id.iv_mine_set:
                MFGT.startActivity(context,SettingsActivity_.class);
                break;
            case R.id.tv_see:

                break;
            case R.id.tv_all_order:
                MFGT.startActivity(context,AliSdkOrderActivity_.class);
                break;
            case R.id.tv_yuanbao:

                break;
            case R.id.tv_huiyuantong:

                break;
            case R.id.tv_youhuiquan:

                break;
            case R.id.tv_yaoqingyoujiang:

                break;
            case R.id.tv_kefu:

                break;
            case R.id.tv_qita:

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserData();
    }
}