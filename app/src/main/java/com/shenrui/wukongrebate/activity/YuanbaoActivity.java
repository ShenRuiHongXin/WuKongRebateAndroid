package com.shenrui.wukongrebate.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.MyToast;
import com.shenrui.wukongrebate.utils.OrderInfoUtil2_0;
import com.shenrui.wukongrebate.utils.PayResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by heikki on 2017/4/12.
 */

@EActivity(R.layout.activity_yuanbao)
public class YuanbaoActivity extends BaseActivity {
    @ViewById(R.id.tv_yb_yue_title)
    TextView tvTitle;
    @ViewById(R.id.tv_yuanbao_1)
    TextView tvYb1;
    @ViewById(R.id.tv_yuanbao_2)
    TextView tvYb2;
    @ViewById(R.id.tv_yue_hint)
    TextView tvYue1;
    @ViewById(R.id.tv_cz_tx)
    TextView tvCzTx;

    public static final int YUANBAO = 1;
    public static final int YUE = 2;
    private int target;

    @AfterViews
    void init(){
        Intent intent = getIntent();
        target = intent.getIntExtra("target",0);
        switch (target){
            case YUANBAO:
                tvTitle.setText("元宝");
                tvCzTx.setText("提现");
                tvYue1.setVisibility(View.GONE);
                break;
            case YUE:
                tvTitle.setText("余额");
                tvCzTx.setText("充值");
                tvYb1.setVisibility(View.GONE);
                tvYb2.setVisibility(View.GONE);
                break;
        }
    }

    @Click({R.id.iv_yb_back,R.id.rl_cz_tx})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_yb_back:
                MFGT.finish(this);
                break;
            case R.id.rl_cz_tx:
                if(target == YUE){
                    Uri uri = Uri.parse("alipays://platformapi/startApp");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    ComponentName componentName = intent.resolveActivity(getPackageManager());

                    if (!(componentName != null)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示")
                                .setMessage("您的手机未安装支付宝，是否前去安装?")
                                .setPositiveButton("去安装", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri uriMarket = Uri.parse("market://details?id=" + "com.eg.android.AlipayGphone");
                                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uriMarket);
                                        startActivity(goToMarket);
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("暂时不", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }else{
                        showCZDialog();
                    }
                }
                break;
        }
    }

    /*********************************************充值业务************************************************/
    private void showCZDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.vip_chongzhi_dialog,(ViewGroup) findViewById(R.id.dialog));
        //充值选项
        GridView gridView = (GridView) dialog.findViewById(R.id.gv_chongzhi_items);
        final List<CzValueAdapter.CzValue> czValueList = new ArrayList<>();
        czValueList.add(new CzValueAdapter.CzValue(10,0));
        czValueList.add(new CzValueAdapter.CzValue(20,0));
        czValueList.add(new CzValueAdapter.CzValue(50,5));
        czValueList.add(new CzValueAdapter.CzValue(100,10));
        czValueList.add(new CzValueAdapter.CzValue(500,50));
        CzValueAdapter adapter = new CzValueAdapter(czValueList,YuanbaoActivity.this);
        gridView.setAdapter(adapter);
        //确定取消按钮
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_dialog_cancel);
        TextView tvConfirm = (TextView) dialog.findViewById(R.id.tv_dialog_confirm);
        //输入充值金额
        final EditText etInput = (EditText) dialog.findViewById(R.id.et_chongzhi_input);
        //支付方式
        RadioGroup rgPayWays = (RadioGroup) dialog.findViewById(R.id.rg_pay_ways);
        rgPayWays.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_alipay:
                        LogUtil.d("支付宝支付");
                        break;
                    //预留微信支付
//                    case R.id.rb_wxpay:
//                        LogUtil.d("微信支付");
//                        break;
                }
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        final AlertDialog dialog1 = builder.create();
        android.view.Window window = dialog1.getWindow();
        //2017年4月28日18:03:01 到此，需设置padding
        window.setBackgroundDrawableResource(R.drawable.circle_dialog_bg);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyToast.showToast(YuanbaoActivity.this,"充值:"+czValueList.get(position).getRecharge());
                pay(czValueList.get(position).getRecharge());
                dialog1.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etInput.getText().toString().trim().equals("")){
                    MyToast.showToast(YuanbaoActivity.this,"请输入充值金额");
                }else if(Integer.parseInt(etInput.getText().toString().trim()) == 0){
                    MyToast.showToast(YuanbaoActivity.this,"充值金额须大于0");
                }else{
                    MyToast.showToast(YuanbaoActivity.this,"充值:"+etInput.getText().toString().trim());
                    pay(Integer.parseInt(etInput.getText().toString().trim()));
                    dialog1.dismiss();
                }
            }
        });
    }

    /*******************************************支付宝**********************************************/
    private static final String RSA2_PRIVATE = Constants.ALIPAY_PRIVATE_KEY;
    private static final String RSA_PRIVATE = Constants.ALIPAY_PRIVATE_KEY;
    private static final String APPID = Constants.ALIPAY_APP_ID;
    private static final String PID = Constants.ALIPAY_PID;
    public static final String TARGET_ID = "srhx";
    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    LogUtil.d("支付宝支付结果:" +resultStatus + ":"+ resultInfo);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(YuanbaoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(YuanbaoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    /**
     * 支付宝支付业务
     */
    public void pay(double recharge) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,recharge);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        LogUtil.d("支付请求参数:" + orderInfo);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(YuanbaoActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtil.d("msp:"+result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /***************************************************************************/
    private static class CzValueAdapter extends BaseAdapter{
        private List<CzValue> data;
        private Context context;

        public CzValueAdapter(List<CzValue> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.vip_chongzhi_value_item,null);
            TextView tvRecharge = (TextView) view.findViewById(R.id.tv_chongzhi_recharge);
            TextView tvPresenter = (TextView) view.findViewById(R.id.tv_chongzhi_presenter);

            tvRecharge.setText(data.get(position).getRecharge()+"元");
            tvPresenter.setText("可获得:"+(data.get(position).getRecharge()+data.get(position).getPresenter()));
            return view;
        }

        public static class CzValue{
            private int recharge;
            private int presenter;

            public CzValue(int recharge, int presenter) {
                this.recharge = recharge;
                this.presenter = presenter;
            }

            public int getRecharge() {
                return recharge;
            }

            public void setRecharge(int recharge) {
                this.recharge = recharge;
            }

            public int getPresenter() {
                return presenter;
            }

            public void setPresenter(int presenter) {
                this.presenter = presenter;
            }
        }
    }


    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
