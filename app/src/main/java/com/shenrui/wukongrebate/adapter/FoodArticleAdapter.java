package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;

/**
 * 美食周刊/视频 适配器
 */

public class FoodArticleAdapter extends RecyclerView.Adapter{
    Context context;
    private String dataType;
    public FoodArticleAdapter(Context context,String dataType) {
        this.context = context;
        this.dataType = dataType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = null;
        RecyclerView.ViewHolder holder = null;
        switch (dataType){
            case Constants.FOOD_ARTICLE:
                layout = inflater.inflate(R.layout.food_article_item, null);
                holder = new FoodArticleHolder(layout);
                break;
            case Constants.FOOD_VIDEO:
                layout = inflater.inflate(R.layout.food_video_item,null);
                holder = new FoodVideoHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (dataType){
            case Constants.FOOD_ARTICLE:
                FoodArticleHolder articleHolder = (FoodArticleHolder) holder;
                break;
            case Constants.FOOD_VIDEO:
                FoodVideoHolder videoHolder = (FoodVideoHolder) holder;
                /*videoHolder.vv.setMediaController(new MediaController(context));
                videoHolder.vv.setVideoPath("/system/media/video/gen30.mp4");*/
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private class FoodArticleHolder extends RecyclerView.ViewHolder{
        ImageView ivArticle;
        FoodArticleHolder(View itemView) {
            super(itemView);
            ivArticle = (ImageView) itemView.findViewById(R.id.iv_food_article_item);
        }
    }

    private class FoodVideoHolder extends RecyclerView.ViewHolder{
        VideoView vv;
        FoodVideoHolder(View itemView) {
            super(itemView);
            vv = (VideoView) itemView.findViewById(R.id.food_video_vv);
        }
    }
}
