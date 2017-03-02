package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.LoginActivity_;
import com.shenrui.wukongrebate.activity.PersonalInfoActivity_;
import com.shenrui.wukongrebate.activity.SettingsActivity_;
import com.shenrui.wukongrebate.adapter.MineGridAdapter;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;
import com.taobao.api.AliSdkOrderActivity_;

/**
 * Created by heikki on 2016/12/28.
 */

public class FragmentMine extends BaseFragment implements View.OnClickListener{
    TextView tv_toolbar_left;
    ImageView iv_toolbar_left;
    ImageView iv_toolbar_right;
    Toolbar mToolbar;
    View view;
    RecyclerView recyclerView;
    ImageView iv_avatar;
    ImageView iv_sex;
    TextView tv_user_name;
    TextView tv_money;
    TextView tv_withdraw;
    TextView tv_all_order;
    SwipeRefreshLayout srl;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment_page, container, false);
        context = getContext();
        init();
        setListener();
        initUserData();
        return view;
    }
    boolean isLogined;
    UserInfo userInfo;
    UserAuths userAuths;
    MineGridAdapter adapter;
    private void initUserData() {
        adapter.updateData();
        userInfo = SharedPreferenceUtils.getInstance(context).getUserInfo();
        userAuths = SharedPreferenceUtils.getInstance(context).getUserAuths();
        if(userInfo!=null){
            isLogined = true;
            //用户已登录,设置用户昵称，性别，头像,可用余额
            tv_user_name.setText(userInfo.getNick_name());
            tv_money.setText(userInfo.getBalance()+"元");
            iv_sex.setImageResource(userInfo.getSex() == 2?R.drawable.mine_sex_woman:R.drawable.mine_sex_man);
            if(userInfo.getAvatar()!=null){
                if (!userAuths.getIdentity_type().equals("phone")){
                    Glide.with(this).load(userInfo.getAvatar()).into(iv_avatar);
                }else{
                    Glide.with(this).load(Constants.HOST + userInfo.getAvatar()).into(iv_avatar);
                }
            }else{
                iv_avatar.setImageResource(R.drawable.mine_avatar);
            }
        }else{//当前无用户
            isLogined = false;
            tv_user_name.setText("登录/注册");
            iv_avatar.setImageResource(R.drawable.mine_avatar);
            iv_sex.setImageResource(R.drawable.mine_sex_man);
            tv_money.setText("xx元");
        }
    }

    private void setListener() {
        iv_avatar.setOnClickListener(this);
        iv_sex.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        iv_toolbar_left.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);
        tv_all_order.setOnClickListener(this);

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

    void init(){
        tv_toolbar_left = (TextView) view.findViewById(R.id.toolbar_left_text);
        iv_toolbar_left = (ImageView) view.findViewById(R.id.toolbar_left_image);
        iv_toolbar_right = (ImageView) view.findViewById(R.id.toolbar_right_image);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        iv_sex = (ImageView) view.findViewById(R.id.iv_sex);
        tv_user_name = (TextView) view.findViewById(R.id.tv_userName);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_withdraw = (TextView) view.findViewById(R.id.tv_withdraw);
        tv_all_order = (TextView) view.findViewById(R.id.tv_all_order);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.mine_srl);

        tv_toolbar_left.setVisibility(View.GONE);
        iv_toolbar_left.setImageResource(R.drawable.mine_set_n);
        iv_toolbar_right.setImageResource(R.drawable.mine_message);
        ((TextView)view.findViewById(R.id.toolbar_title)).setText("悟空");

        userInfo = SharedPreferenceUtils.getInstance(context).getUserInfo();
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        adapter = new MineGridAdapter(getContext(),userInfo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_image:
                MFGT.startActivity(context,SettingsActivity_.class);
                break;
            case R.id.iv_avatar:
            case R.id.tv_userName:
            case R.id.iv_sex:
                if(isLogined){
                    MFGT.startActivity(context,PersonalInfoActivity_.class);
                }else{
                    MFGT.startActivity(context,LoginActivity_.class);
                }
                break;
            case R.id.tv_withdraw://提现

                break;
            case R.id.tv_all_order://查看全部订单
                //startActivity(new Intent(context, AliSdkOrderActivity_.class));
                MFGT.startActivity(context,AliSdkOrderActivity_.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserData();
    }
}