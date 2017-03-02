package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.model.User;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.SignActivity_;
import com.shenrui.wukongrebate.activity.TestActivity;
import com.shenrui.wukongrebate.activity.TestActivity_;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;
import com.taobao.api.AliSdkMyCartActivity;
import com.taobao.api.AliSdkMyCartActivity_;
import com.taobao.api.AliSdkOrderActivity;
import com.taobao.api.AliSdkOrderActivity_;

/**
 * Created by Administrator on 2016/12/29.
 */

public class MineGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_ONE = 1;
    private static final int TYPE_ITEM_TWO = 2;
    Context context;
    Integer[] images = {0,R.drawable.mine_icon1,2,
                            R.drawable.mine_icon2,R.drawable.mine_icon3,R.drawable.mine_icon4,
                            R.drawable.mine_icon5,R.drawable.mine_icon6,R.drawable.mine_icon7,
                            R.drawable.mine_icon8,R.drawable.mine_icon9,R.drawable.mine_icon10};
    String[] texts = {"赠送余额","悟空券","悟空积分","余额提现","资金明细","购物车",
                                    "悟空抽奖","会员通","邀请有奖","0元礼物","客服","悟空团购"};
    View.OnClickListener onClickListener;
    UserInfo userInfo;
    public MineGridAdapter(final Context context,UserInfo userInfo) {
        this.context = context;
        this.userInfo = userInfo;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                switch (position){
                    case 0:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        MFGT.startActivity(context, TestActivity_.class);
                        break;
                    case 1:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    //悟空积分
                    case 2:
                        //context.startActivity(new Intent(context, SignActivity_.class).putExtra("fromMine",1));
                        MFGT.startActivity(context,SignActivity_.class);
                        break;
                    case 3:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    //打开购物车界面
                    case 5:
                        //context.startActivity(new Intent(context, AliSdkMyCartActivity_.class));
                        MFGT.startActivity(context,AliSdkMyCartActivity_.class);
                        break;
                    case 6:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_ITEM_ONE:
                View layout = inflater.inflate(R.layout.mine_item_layout, null, false);
                holder = new MyHolder(layout);
                break;
            case TYPE_ITEM_TWO:
                View layout2 = inflater.inflate(R.layout.mine_item2_layout, null, false);
                holder = new TwoHolder(layout2);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM_ONE){
            MyHolder myHolder = (MyHolder) holder;
            myHolder.imageView.setImageResource(images[position]);
            myHolder.textView.setText(texts[position]);
            myHolder.itemLayout.setTag(position);
        }else{
            TwoHolder twoHolder = (TwoHolder) holder;
            if(position == 0){
                double balance = userInfo==null?0:userInfo.getBalance();
                twoHolder.tvShow.setText(balance+"元");
            }
            if(position == 2){
                int integral = userInfo==null?0:userInfo.getIntegral();
                twoHolder.tvShow.setText(integral+"个");
            }
            twoHolder.textView.setText(texts[position]);
            twoHolder.item2Layout.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        return texts.length;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || position == 2){
            return TYPE_ITEM_TWO;
        }else{
            return TYPE_ITEM_ONE;
        }
    }

    public void updateData() {
        userInfo = SharedPreferenceUtils.getInstance(context).getUserInfo();
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        LinearLayout itemLayout;
        public MyHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_mine_item);
            textView = (TextView) itemView.findViewById(R.id.tv_mine_item);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);

            itemLayout.setOnClickListener(onClickListener);
        }
    }

    class TwoHolder extends RecyclerView.ViewHolder{
        TextView tvShow;
        TextView textView;
        LinearLayout item2Layout;
        public TwoHolder(View itemView) {
            super(itemView);
            tvShow = (TextView) itemView.findViewById(R.id.tv_show);
            textView = (TextView) itemView.findViewById(R.id.tv_mine_item2);
            item2Layout = (LinearLayout) itemView.findViewById(R.id.item2_layout);

            item2Layout.setOnClickListener(onClickListener);
        }
    }
}
