package com.shenrui.wukongrebate.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.UserBean;
import com.shenrui.wukongrebate.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.food_fragment_page)
public class FragmentFood extends BaseFragment {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    @AfterViews
    void init(){
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.index_btn_city_n);
        ((TextView)listTitleView.get(2)).setText("美食馆");
        listTitleView.get(3).setVisibility(View.GONE);
        LogUtil.i("FragmentFood created");
    }

    @Click(R.id.btn_user_login)
    void userLogin(){
        UserBean user = new UserBean();
        user.setName("张三");
        user.setPassword("abcd");
        user.setSex(1);
        String userJson = new Gson().toJson(user);
        LogUtil.d("user info: " + userJson);

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("userInfo",userJson)
                .build();

        Request requestLogin = new Request.Builder()
                .url("http://192.168.0.3:8080/WukongServer/book_login")
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(requestLogin);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("网络连接失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strResp = response.body().string();
                LogUtil.d("返回数据：" + strResp);

                Map<String,Object> map = new Gson().fromJson(strResp,new TypeToken<Map<String,Object>>(){}.getType());
                for (Map.Entry entry : map.entrySet()){
                    LogUtil.d("key: " + entry.getKey() + " value: " + entry.getValue());
                }
            }
        });
    }

}
