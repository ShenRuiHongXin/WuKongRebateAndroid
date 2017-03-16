package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter {
    private static final int TYPE_HISTORY_ITEM = 0;
    private static final int TYPE_CLEAR_LAST = 1;//清除搜索历史的item
    private List<String> texts;
    Context context;
    public SearchHistoryAdapter(List<String> texts_history, Context context) {
        this.texts = texts_history;
        this.context = context;
    }

    @Override
    public int getCount() {
        return texts.size()+1;
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
        int viewType = getItemViewType(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_HISTORY_ITEM){
            convertView = inflater.inflate(R.layout.layout_search_history_item,null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_search_history);
            tv.setText(texts.get(position));
            return convertView;
        }else{
            convertView = inflater.inflate(R.layout.layout_search_history_last_item,null);
            return convertView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount()-1){
            return TYPE_CLEAR_LAST;
        }else{
            return TYPE_HISTORY_ITEM;
        }
    }

}
