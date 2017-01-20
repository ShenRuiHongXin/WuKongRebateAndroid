package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2016/12/29.
 */

public class MyGridAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = { "签到积分", "邀请奖励", "积分抽奖", "返利攻略" };
    public int[] imgs = {R.drawable.index_icon_sign_n,R.drawable.index_icon_money_n,R.drawable.index_icon_gift_n,R.drawable.index_icon_strategy_n};

    public MyGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.mygridview_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }
}
