package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.LoginActivity_;
import com.shenrui.wukongrebate.activity.PersonalInfoActivity_;
import com.shenrui.wukongrebate.activity.SettingsActivity_;
import com.shenrui.wukongrebate.adapter.MineGridAdapter;
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
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment_page, container, false);
        context = getContext();
        SharedPreferenceUtils.getInstance().removeUserName();
        init();
        setListener();
        getUser();
        return view;
    }

    private void getUser() {
        String userName = SharedPreferenceUtils.getInstance().getUserName();
        if(userName!=null){
            tv_user_name.setText(userName);
        }else{//当前无用户
            tv_user_name.setText("登录/注册");
            tv_user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, LoginActivity_.class));
                }
            });
        }
    }

    private void setListener() {
        iv_avatar.setOnClickListener(this);
        iv_sex.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        iv_toolbar_left.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);
        tv_all_order.setOnClickListener(this);
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

//        mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.mineRed));
        tv_toolbar_left.setVisibility(View.GONE);

        iv_toolbar_left.setImageResource(R.drawable.mine_set_n);
        iv_toolbar_right.setImageResource(R.drawable.mine_message);
        ((TextView)view.findViewById(R.id.toolbar_title)).setText("悟空");

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        MineGridAdapter adapter = new MineGridAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_image:
                startActivity(new Intent(context,SettingsActivity_.class));
                break;
            case R.id.iv_avatar:
            case R.id.tv_userName:
            case R.id.iv_sex:
                startActivity(new Intent(context, PersonalInfoActivity_.class));
                break;
            case R.id.tv_withdraw://提现

                break;
            case R.id.tv_all_order://查看全部订单
                startActivity(new Intent(context, AliSdkOrderActivity_.class));
                break;
        }
    }

}