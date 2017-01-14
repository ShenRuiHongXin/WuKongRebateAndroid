package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.google.gson.Gson;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.PaidInfoBean;
import com.shenrui.wukongrebate.entities.Result;
import com.shenrui.wukongrebate.entities.TaoBaoUser;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.OkHttpUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.haitao_fragment_page)
public class FragmentHaitao extends BaseFragment {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;
    @AfterViews
    void init(){
        listTitleView.get(0).setVisibility(View.GONE);
        listTitleView.get(1).setVisibility(View.GONE);
        ((TextView)listTitleView.get(2)).setText("海淘");
        listTitleView.get(3).setVisibility(View.GONE);
        LogUtil.i("FragmentHaitao created");
    }

    @Click(R.id.tv_test)
    void test(View view){
        Session session = AlibcLogin.getInstance().getSession();
        Log.e("test",session.toString());
        String str = session.toString();
        String openId = str.substring(str.indexOf("openId")+7, str.indexOf("openSid") - 2);
        Log.e("test openId",openId);

        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);

        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();

        PaidInfoBean bean = new PaidInfoBean(111,openId,"111111111",time,Build.MODEL);
        Log.e("test",bean.toString());
        String value = bean.toString();
        //http://192.168.0.4:8080/WukongServer/paidInfo_add
        /*OkHttpUtils<Result> utils = new OkHttpUtils<>(getContext());
        utils.url("http://192.168.0.4:8080/WukongServer/paidInfo_add")
                .addParam("paidInfo",value)
                .targetClass(Result.class)
                .execute(new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });*/
    }
}
