package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.NineSearchActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

//超级返，9块9搜索推荐适配器
public class SearchRecommendAdapter extends BaseAdapter {
    List<String> texts;
    Context context;
    public SearchRecommendAdapter(List<String> texts, Context context) {
        this.texts = texts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return texts.size();
    }

    @Override
    public Object getItem(int position) {
        return texts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchRecommendAdapter.Holder holder;
        if (convertView == null){
            holder = new SearchRecommendAdapter.Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_search_recommend_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_search_recommend);
            convertView.setTag(holder);
        }else{
            holder = (SearchRecommendAdapter.Holder) convertView.getTag();
        }
        holder.tv.setText(texts.get(position));
        return convertView;
    }

    class Holder{
        TextView tv;
    }
}
