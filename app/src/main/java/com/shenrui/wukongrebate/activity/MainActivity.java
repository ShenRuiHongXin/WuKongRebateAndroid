package com.shenrui.wukongrebate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.fragment.FragmentFood_;
import com.shenrui.wukongrebate.fragment.FragmentMine_;
import com.shenrui.wukongrebate.fragment.FragmentRebate_;
import com.shenrui.wukongrebate.fragment.FragmentZhi_;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity{
    Context context;

    @ViewById(R.id.rb_rebate)
    RadioButton rbRebate;
    @ViewById(R.id.rb_food)
    RadioButton rbFood;
    @ViewById(R.id.rb_zhi)
    RadioButton rbZhi;
    @ViewById(R.id.rb_wukong)
    RadioButton rbWuKong;

    private List<Fragment> fragmentList;

    MyReceiver receiver;
    int index = 0;
    @AfterViews
    void init() {
        context = this;
        //去除系统提供的默认背景
        getWindow().setBackgroundDrawable(null);
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentRebate_());
        fragmentList.add(new FragmentZhi_());
        fragmentList.add(new FragmentFood_());
        fragmentList.add(new FragmentMine_());

        //设置默认显示的Fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_content,fragmentList.get(0)).show(fragmentList.get(0)).commit();

        //注册广播接受者
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.GOTOZHI);
        receiver = new MyReceiver();
        registerReceiver(receiver,filter);
        checkVersion();
    }

    private void checkVersion(){
        LogUtil.d(Utils.getAppName(this)+" 当前版本号:" + Utils.getVersionCode(this) +",当前版本名:" + Utils.getVersionName(this));
    }

    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            index = 1;
            switchFragment();
        }
    }

    @Click({R.id.rb_rebate, R.id.rb_zhi, R.id.rb_food, R.id.rb_wukong})
    void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.rb_rebate:
                index = 0;
                break;
            case R.id.rb_zhi:
                index = 1;
                break;
            case R.id.rb_food:
                index = 2;
                break;
            case R.id.rb_wukong:
                index = 3;
                break;
            default:
                index = 0;
        }
        switchFragment();
    }

    int currentIndex;
    private void switchFragment() {
        initViewStatus();
        if(currentIndex == index){
            return;
        }
        Fragment fragment = fragmentList.get(index);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()){
            ft.add(R.id.layout_content,fragment);
        }
        ft.show(fragmentList.get(index)).hide(fragmentList.get(currentIndex)).commit();
        currentIndex = index;
    }

    private void initViewStatus(){
        RadioButton[] rbs = new RadioButton[4];
        rbs[0] = rbRebate;
        rbs[1] = rbZhi;
        rbs[2] = rbFood;
        rbs[3] = rbWuKong;
        for(int i = 0;i<rbs.length;i++){
            if (index == i){
                rbs[i].setChecked(true);
            }else{
                rbs[i].setChecked(false);
            }
        }
    }

    //创建APP通用数据库
    private void createCommonDatabase(){
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(Constants.DATABASE_PATH,null);
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 1000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
