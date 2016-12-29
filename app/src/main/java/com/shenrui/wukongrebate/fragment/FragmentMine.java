package com.shenrui.wukongrebate.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.MineGridAdapter;
import com.shenrui.wukongrebate.utils.PhotoPop;

import org.androidannotations.annotations.EFragment;

/**
 * Created by heikki on 2016/12/28.
 */

public class FragmentMine extends Fragment implements View.OnClickListener{
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

    PhotoPop photoPop;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment_page, container, false);
        init();
        setListener();
        return view;
    }

    private void setListener() {
        iv_avatar.setOnClickListener(this);
        iv_sex.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);
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
            case R.id.iv_avatar://修改头像
                photoPop = new PhotoPop(getActivity(),R.layout.activity_main);
                break;
            case R.id.tv_userName:

                break;
            case R.id.iv_sex:

                break;
            case R.id.tv_withdraw://提现

                break;
            case R.id.tv_all_order://查看全部订单

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoPop.setPhoto(requestCode,resultCode,data,iv_avatar);
    }
}
