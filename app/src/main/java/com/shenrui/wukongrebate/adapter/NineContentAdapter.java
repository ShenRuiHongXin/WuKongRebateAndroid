package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.RecyItemIndexData;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.view.CycleRotationView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */

public class NineContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CYCLE = 0;
    private static final int TYPE_LINEARLAYOUT = 1;
    private static final int TYPE_RECYCLERVIEW =2;
    Context context;
    RecyItemIndexData datas;
    SearchGoodsAdater searchGoodsAdater;

    public NineContentAdapter(Context context, RecyItemIndexData data) {
        this.context = context;
        this.datas = data;
        searchGoodsAdater = new SearchGoodsAdater(context, datas.getTenList());
    }

    public void initData(List<TenGoodsData> contentList){
        if(contentList!=null){
            searchGoodsAdater.initData(contentList);
        }
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_CYCLE:
                layout = inflater.inflate(R.layout.layout_nine_cyclerotationview, parent, false);
                holder = new CycleViewHolder(layout);
                break;
            case TYPE_LINEARLAYOUT:
                layout = inflater.inflate(R.layout.layout_nine_linearlayout, parent, false);
                holder = new LinearLayoutViewHolder(layout);
                break;
            case TYPE_RECYCLERVIEW:
                layout = inflater.inflate(R.layout.layout_nine_content, parent, false);
                holder = new RecyclerViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_CYCLE:
                CycleViewHolder cycleViewHolder = (CycleViewHolder) holder;
                cycleViewHolder.cycleRotationView.setUrls(datas.getCycleList());
                break;
            case TYPE_LINEARLAYOUT:
                break;
            case TYPE_RECYCLERVIEW:
                RecyclerViewHolder rvViewHolder = (RecyclerViewHolder) holder;
                rvViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context,2){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                rvViewHolder.recyclerView.setAdapter(searchGoodsAdater);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.getTenList()==null ? 2:2+datas.getTenList().size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_CYCLE;
        } else if (position == 1) {
            return TYPE_LINEARLAYOUT;
        }else{
            return TYPE_RECYCLERVIEW;
        }
    }

    class CycleViewHolder extends RecyclerView.ViewHolder{
        CycleRotationView cycleRotationView;
        public CycleViewHolder(View itemView) {
            super(itemView);
            cycleRotationView = (CycleRotationView) itemView.findViewById(R.id.nine_cycleRotationView);
        }
    }
    class LinearLayoutViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        public LinearLayoutViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.nine_linearLayout);
        }
    }
    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.nine_content_rv);
        }
    }
}
