package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.AppInnerDownLoder;
import com.shenrui.wukongrebate.utils.DownLoadApk;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by heikki on 2017/4/11.
 */

@EActivity(R.layout.progress_activity)
public class UpdateDownloatActivity extends BaseActivity {
    @ViewById(R.id.download_btn)
    Button btnDownload;
    @ViewById(R.id.download_progress)
    ProgressBar pbDownload;
    @ViewById(R.id.download_message)
    TextView tvDownload;

    private AlertDialog.Builder mDialog;
    private String appName = "悟空返利";
    private String versionName = "1.0.1";
    private String downUrl = "http://192.168.0.3:8080/WKBackstageSys/Resources/apks/app-debug.apk";

    @Click({R.id.download_btn_i,R.id.download_btn_n})
    void clickEvent(View v) {
        if (v.getId() == R.id.download_btn_i) {
//            doDownload();
            forceUpdate(this,appName,versionName,downUrl,"又有新版本了");
        }else if (v.getId() == R.id.download_btn_n){
            normalUpdate(this,appName,versionName,downUrl,"又有新版本了");
        }
    }

    /**
     * 强制更新
     * @param context
     * @param appName
     * @param downUrl
     * @param updateinfo
     */
    private void forceUpdate(final Context context, final String appName,final String versionName, final String downUrl, final String updateinfo) {
        mDialog = new AlertDialog.Builder(context);
        mDialog.setTitle(appName+"又更新咯！");
        mDialog.setMessage(updateinfo);
        mDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!canDownloadState()) {
                    showDownloadSetting();
                    return;
                }
                //   DownLoadApk.download(MainActivity.this,downUrl,updateinfo,appName);
                AppInnerDownLoder.downLoadApk(context,downUrl,appName,versionName);
            }
        }).setCancelable(false).create().show();
    }

    /**
     * 正常更新
     * @param context
     * @param appName
     * @param downUrl
     * @param updateinfo
     */
    private void normalUpdate(Context context, final String appName,final String versionName, final String downUrl, final String updateinfo) {
        mDialog = new AlertDialog.Builder(context);
        mDialog.setTitle(appName+"又更新咯！");
        mDialog.setMessage(updateinfo);
        mDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!canDownloadState()) {
                    showDownloadSetting();
                    return;
                }
                // AppInnerDownLoder.downLoadApk(MainActivity.this,downUrl,appName);
                DownLoadApk.download(UpdateDownloatActivity.this,downUrl,updateinfo,appName,versionName);
            }
        }).setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).create().show();
    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            startActivity(intent);
        }
    }

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean canDownloadState() {
        try {
            int state = this.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//
//    /**
//     * 使用Handler更新UI界面信息
//     */
//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            pbDownload.setProgress(msg.getData().getInt("size"));
//
//            float temp = (float) pbDownload.getProgress() / (float) pbDownload.getMax();
//
//            int progress = (int) (temp * 100);
//            if (progress == 100) {
//                Toast.makeText(UpdateDownloatActivity.this, "下载完成！", Toast.LENGTH_LONG).show();
//            }
//            tvDownload.setText("下载进度:" + progress + " %");
//
//        }
//    };
//
//    /**
//     * 下载准备工作，获取SD卡路径、开启线程
//     */
//    private void doDownload() {
//        // 获取SD卡路径
//        String path = Environment.getExternalStorageDirectory() + "/wkDownload/";
//        File file = new File(path);
//        // 如果SD卡目录不存在创建
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        // 设置progressBar初始化
//        pbDownload.setProgress(0);
//
//        // 简单起见，我先把URL和文件名称写死，其实这些都可以通过HttpHeader获取到
//        String downloadUrl = "http://192.168.0.3:8080/WKBackstageSys/Resources/apks/app-debug.apk";
//        String fileName = "app-debug.apk";
//        int threadNum = 5;
//        String filepath = path + fileName;
//        LogUtil.d("download file  path:" + filepath);
//        downloadTask task = new downloadTask(downloadUrl, threadNum, filepath);
//        task.start();
//    }
//
//    /**
//     * 多线程文件下载
//     *
//     * @author yangxiaolong
//     * @2014-8-7
//     */
//    class downloadTask extends Thread {
//        private String downloadUrl;// 下载链接地址
//        private int threadNum;// 开启的线程数
//        private String filePath;// 保存文件路径地址
//        private int blockSize;// 每一个线程的下载量
//
//        public downloadTask(String downloadUrl, int threadNum, String fileptah) {
//            this.downloadUrl = downloadUrl;
//            this.threadNum = threadNum;
//            this.filePath = fileptah;
//        }
//
//        @Override
//        public void run() {
//
//            FileDownloadThread[] threads = new FileDownloadThread[threadNum];
//            try {
//                URL url = new URL(downloadUrl);
//                LogUtil.d("download file http path:" + downloadUrl);
//                URLConnection conn = url.openConnection();
//                // 读取下载文件总大小
//                int fileSize = conn.getContentLength();
//                if (fileSize <= 0) {
//                    System.out.println("读取文件失败");
//                    return;
//                }
//                // 设置ProgressBar最大的长度为文件Size
//                pbDownload.setMax(fileSize);
//
//                // 计算每条线程下载的数据长度
//                blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum : fileSize / threadNum + 1;
//
//                LogUtil.d("fileSize:" + fileSize + "  blockSize:"+blockSize);
//
//                File file = new File(filePath);
//                for (int i = 0; i < threads.length; i++) {
//                    // 启动线程，分别下载每个线程需要下载的部分
//                    threads[i] = new FileDownloadThread(url, file, blockSize,(i + 1));
//                    threads[i].setName("Thread:" + i);
//                    threads[i].start();
//                }
//
//                boolean isfinished = false;
//                int downloadedAllSize = 0;
//                while (!isfinished) {
//                    isfinished = true;
//                    // 当前所有线程下载总量
//                    downloadedAllSize = 0;
//                    for (int i = 0; i < threads.length; i++) {
//                        downloadedAllSize += threads[i].getDownloadLength();
//                        if (!threads[i].isCompleted()) {
//                            isfinished = false;
//                        }
//                    }
//                    // 通知handler去更新视图组件
//                    Message msg = new Message();
//                    msg.getData().putInt("size", downloadedAllSize);
//                    mHandler.sendMessage(msg);
//                    // Log.d(TAG, "current downloadSize:" + downloadedAllSize);
//                    Thread.sleep(1000);// 休息1秒后再读取下载进度
//                }
//                LogUtil.d(" all of downloadSize:" + downloadedAllSize);
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
}
