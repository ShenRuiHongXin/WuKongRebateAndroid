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

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.SignActivity_;
import com.taobao.api.AliSdkMyCartActivity;
import com.taobao.api.AliSdkMyCartActivity_;
import com.taobao.api.AliSdkOrderActivity;
import com.taobao.api.AliSdkOrderActivity_;

/**
 * Created by Administrator on 2016/12/29.
 */

public class MineGridAdapter extends RecyclerView.Adapter<MineGridAdapter.MyHolder> {
    Context context;
    Integer[] images = {R.drawable.mine_icon1,R.drawable.mine_icon1,R.drawable.mine_icon1,
                            R.drawable.mine_icon2,R.drawable.mine_icon3,R.drawable.mine_icon4,
                            R.drawable.mine_icon5,R.drawable.mine_icon6,R.drawable.mine_icon7,
                            R.drawable.mine_icon8,R.drawable.mine_icon9,R.drawable.mine_icon10};
    String[] texts = {"赠送余额","悟空券","悟空积分","余额提现","资金明细","购物车",
                                    "悟空抽奖","会员通","邀请有奖","0元礼物","客服","悟空团购"};
    View.OnClickListener onClickListener;

    public MineGridAdapter(final Context context) {
        this.context = context;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                switch (position){
                    case 0:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        context.startActivity(new Intent(context, SignActivity_.class).putExtra("fromMine",1));
                        break;
                    case 3:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context, texts[position], Toast.LENGTH_SHORT).show();
                        break;
                    //打开购物车界面
                    case 5:
                        context.startActivity(new Intent(context, AliSdkMyCartActivity_.class));
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
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_item_layout, null, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.textView.setText(texts[position]);
        holder.itemLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
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
}
