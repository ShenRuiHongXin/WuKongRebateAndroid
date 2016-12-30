package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2016/12/29.
 */

public class ActivityRecyAdapter extends RecyclerView.Adapter {
    Context context;
    List<String> listTitle;
    List<String> listContent;
    List<Integer> listIcon;

    private List<Integer> heights;

    public ActivityRecyAdapter(Context context, List listTitle, List listContent, List listIcon) {
        this.context = context;
        this.listTitle = listTitle;
        this.listContent = listContent;
        this.listIcon = listIcon;
        getRandomHeight(this.listTitle);
    }

    //得到随机item的高度
    private void getRandomHeight(List<String> lists){
        heights = new ArrayList<>();
        heights.add(400);
        heights.add(200);
        heights.add(200);
        heights.add(200);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.active_recy_item_left, parent, false);
                holder = new MyViewHolderLeft(view);
                break;
            case 1:
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.active_recy_item_right_normal, parent, false);
                holder = new MyViewHolderRight(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //得到item的LayoutParams布局参数
        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();
        //把随机的高度赋予item布局
        params.height = heights.get(position);
        //把params设置给item布局
        holder.itemView.setLayoutParams(params);

        switch (getItemViewType(position)){
            case 0:
                MyViewHolderLeft myViewHolderLeft = (MyViewHolderLeft) holder;
                myViewHolderLeft.tv_title.setText(listTitle.get(position));
                myViewHolderLeft.tv_content.setText(listContent.get(position));
                myViewHolderLeft.iv_icon.setImageResource(listIcon.get(position));
                break;
            case 1:
                MyViewHolderRight myViewHolderRightTop = (MyViewHolderRight) holder;
                myViewHolderRightTop.tv_title.setText(listTitle.get(position));
                myViewHolderRightTop.tv_content.setText(listContent.get(position));
                myViewHolderRightTop.iv_icon.setImageResource(listIcon.get(position));
            case 2:
                MyViewHolderRight myViewHolderRightNormal = (MyViewHolderRight) holder;
                myViewHolderRightNormal.tv_title.setText(listTitle.get(position));
                myViewHolderRightNormal.iv_icon.setImageResource(listIcon.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else if (position == 1){
            return 1;
        }else{
            return 2;
        }
    }

    class MyViewHolderLeft extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_content;
        ImageView iv_icon;

        public MyViewHolderLeft(View view)
        {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_activity_title);
            tv_content = (TextView) view.findViewById(R.id.tv_activity_content);
            iv_icon = (ImageView) view.findViewById(R.id.iv_activity_icon);
        }
    }

    class MyViewHolderRight extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_content;
        ImageView iv_icon;

        public MyViewHolderRight(View view)
        {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_activity_title);
            tv_content = (TextView) view.findViewById(R.id.tv_activity_content);
            iv_icon = (ImageView) view.findViewById(R.id.iv_activity_icon);
        }
    }
}
