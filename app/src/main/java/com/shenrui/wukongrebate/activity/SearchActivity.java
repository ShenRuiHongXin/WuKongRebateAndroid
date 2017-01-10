package com.shenrui.wukongrebate.activity;

import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * Created by heikki on 2017/1/7.
 */

@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {
    @AfterViews
    void getUser(){
        Session session = AlibcLogin.getInstance().getSession();
        LogUtil.d(session.toString());
    }

    @Click(R.id.btn_taobao_out)
    void logout(){
        AlibcLogin.getInstance().logout(this, new LogoutCallback(){
            @Override
            public void onFailure(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }
}
