package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

public class NineContentAdapter extends RecyclerView.Adapter<NineContentAdapter.MyHolder> {
    Context context;
    RecyItemIndexData datas;
    SearchGoodsAdater adater;

    public NineContentAdapter(Context context, RecyItemIndexData data) {
        this.context = context;
        this.datas = data;
    }

    public void initData(List<TenGoodsData> contentList){
        if(contentList!=null){
            adater.initData(contentList);
        }
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_nine_holder, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.cycleRotationView.setUrls(datas.getCycleList());
        adater = new SearchGoodsAdater(context, datas.getTenList());
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(adater);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        CycleRotationView cycleRotationView;
        LinearLayout linearLayout;
        RecyclerView recyclerView;
        public MyHolder(View itemView) {
            super(itemView);
            cycleRotationView = (CycleRotationView) itemView.findViewById(R.id.nine_cycleRotationView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.nine_linearLayout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.nine_rvContent);
        }
    }
}
