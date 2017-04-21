package com.shenrui.wukongrebate.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.LoginActivity_;
import com.shenrui.wukongrebate.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by heikki on 2017/4/14.
 */

public class TimeLimitRemindService extends Service {
    private Intent intent;
    private List<Integer> timeClock;
    private boolean hasNotifi = false;
    //2017年4月14日18:02:13 到此
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                if (!hasNotifi){
                    countingTime();
                    sendEmptyMessageDelayed(1,60*1000);
                }
            }
        }
    };

    public TimeLimitRemindService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("TimeLimitRemindService onCreated");
        handler.sendEmptyMessage(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timeClock == null){
            timeClock = new ArrayList<>();
        }
        int time = intent.getIntExtra("beginTime", 0);
        if (!timeClock.contains(time)){
            timeClock.add(time);
        }
        this.intent = intent;
        LogUtil.d("TimeLimitRemindService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.d("TimeLimitRemindService onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("TimeLimitRemindService onBind");
        return null;
    }

    private void countingTime() {
        //获取当前时间
        SimpleDateFormat formatHour = new SimpleDateFormat("HH");
        SimpleDateFormat formatMins = new SimpleDateFormat("mm");
        SimpleDateFormat formatSeco = new SimpleDateFormat("ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String hour = formatHour.format(curDate);
        String mins = formatMins.format(curDate);
        String seco = formatSeco.format(curDate);
        String list = "[";
        for (int i = 0; i<timeClock.size();i++){
            list = list+timeClock.get(i);
            if (i != timeClock.size()-1){
                list += ",";
            }
        }
        list += "]";
        LogUtil.d("timeClock:" + list+" , curTime:" + mins);
        //开场前10分钟提醒
        for (int i = 0;i<timeClock.size();i++){
            if (timeClock.get(i) == (Integer.parseInt(hour) - 1) && Integer.parseInt(mins) == 50){
//            if (Integer.parseInt(mins) == timeClock.get(i)){
                simpleNotify();
                if (timeClock.size() == 1){
                    hasNotifi = true;
                    stopService(intent);
                    handler.removeCallbacksAndMessages(null);
                }
                timeClock.remove(i);
                return;
            }
        }
    }

    private void simpleNotify() {
        NotificationManager manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        builder.setTicker("限时抢要开始啦!");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("悟空返利");
        //第二行内容 通常是通知正文
        builder.setContentText("限时抢提醒");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("您关注的限时抢商品马上就要开场啦，赶快去看看吧!");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.notifi_icon);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        Intent intent = new Intent(this, LoginActivity_.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manger.notify(1, notification);
    }
}
