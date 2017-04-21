package com.shenrui.wukongrebate.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.service.TimeLimitRemindService;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.view.DividerGridItemDecoration;
import com.shenrui.wukongrebate.view.DividerItemDecoration;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by heikki on 2017/4/13.
 */

public class FragmentTimeLimit extends Fragment {
    private static final int LINEARLAYOUT = 1;
    private static final int GRIDLAYOUT = 2;
    private int currentLayout = 1;
    private static int curHour;

    private RecyclerView rvGoods;
    private TlGoodAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private DividerItemDecoration linerDecoration;
    private DividerGridItemDecoration gridDecoration;

    private AlertDialog.Builder mDialog;

    public static Fragment newInstance(int beginTime,String goodList) {
        Bundle args = new Bundle();
        args.putSerializable("goodList", goodList);
        args.putInt("beginTime",beginTime);
        FragmentTimeLimit fragment = new FragmentTimeLimit();
        fragment.setArguments(args);
        SimpleDateFormat formatHour = new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String hour = formatHour.format(curDate);
        curHour = Integer.parseInt(hour);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_limit, container, false);
        rvGoods = (RecyclerView) view.findViewById(R.id.rv_goods);
        init();
        return view;
    }

    void init(){
        currentLayout = LINEARLAYOUT;
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        linerDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);
        gridDecoration = new DividerGridItemDecoration(getActivity());

        List<TbkItem> listGood = getGoodsList();
        adapter = new TlGoodAdapter(getActivity(),listGood);
        rvGoods.setAdapter(adapter);
        rvGoods.setLayoutManager(linearLayoutManager);
        rvGoods.addItemDecoration(linerDecoration);
    }

    public void changeLayout(){
        if (currentLayout == LINEARLAYOUT){
            rvGoods.setLayoutManager(gridLayoutManager);
            rvGoods.removeItemDecoration(linerDecoration);
            rvGoods.addItemDecoration(gridDecoration);
            rvGoods.setAdapter(adapter);
            currentLayout = GRIDLAYOUT;
        }else{
            rvGoods.setLayoutManager(linearLayoutManager);
            rvGoods.removeItemDecoration(gridDecoration);
            rvGoods.addItemDecoration(linerDecoration);
            currentLayout = LINEARLAYOUT;
            rvGoods.setAdapter(adapter);
        }
//        adapter.notifyDataSetChanged();
    }

    public List<TbkItem> getGoodsList(){
        String json = (String) getArguments().getSerializable("goodList");
        Type type = new TypeToken<List<TbkItem>>(){}.getType();
        return new Gson().fromJson(json,type);
    }
    public int getBeginTime(){
        return getArguments().getInt("beginTime");
    }

    /**************************************************************************************/
    private class TlGoodAdapter extends RecyclerView.Adapter{
        private Context context;
        private List<TbkItem> listGood;

        public TlGoodAdapter(Context context, List<TbkItem> listGood) {
            this.context = context;
            this.listGood = listGood;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(currentLayout == 1 ? R.layout.time_limit_liner : R.layout.time_limit_grid,parent,false);
            return new GoodViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            GoodViewHolder goodViewHolder = (GoodViewHolder) holder;
            TbkItem good = listGood.get(position);
            if (currentLayout == GRIDLAYOUT){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) goodViewHolder.ivGoodImg.getLayoutParams();
                params.width = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context,5)) / 2;
                params.height = params.width;
                goodViewHolder.ivGoodImg.setLayoutParams(params);
            }
            Glide.with(context).load(good.getPict_url()).into(goodViewHolder.ivGoodImg);
            goodViewHolder.tvGoodName.setText(good.getTitle());
            goodViewHolder.tvGoodSoldCount.setText(String.format(getResources().getString(R.string.tl_good_sold_count),good.getVolume()));
            goodViewHolder.tvGoodZkPrice.setText(String.format(getResources().getString(R.string.good_price),Float.parseFloat(good.getZk_final_price())));
            goodViewHolder.tvGoodPrice.setText(String.format(getResources().getString(R.string.good_price),Float.parseFloat(good.getReserve_price())));
            goodViewHolder.setOnItemClick(good);
            goodViewHolder.setOnTvBuyClick(good);
            if(curHour > getBeginTime()){
                goodViewHolder.tvBuy.setBackground(getResources().getDrawable(R.drawable.radius_title_red));
                goodViewHolder.tvBuy.setText("马上抢");
            }else{
                goodViewHolder.tvBuy.setBackground(getResources().getDrawable(R.drawable.radius_title_blue));
                goodViewHolder.tvBuy.setText("提醒我");
            }
        }

        @Override
        public int getItemCount() {
            return listGood.size();
        }

        class GoodViewHolder extends RecyclerView.ViewHolder{
            private ImageView ivGoodImg;
            private TextView tvGoodName;
            private TextView tvGoodSoldCount;
            private TextView tvGoodZkPrice;
            private TextView tvGoodPrice;
            private TextView tvBuy;

            public GoodViewHolder(View itemView) {
                super(itemView);
                ivGoodImg = (ImageView) itemView.findViewById(R.id.iv_time_limit_good_img);
                tvGoodName = (TextView) itemView.findViewById(R.id.tv_time_limit_good_name);
                tvGoodSoldCount = (TextView) itemView.findViewById(R.id.tv_time_limit_good_sold_count);
                tvGoodZkPrice = (TextView) itemView.findViewById(R.id.tv_time_limit_good_zk_price);
                tvGoodPrice = (TextView) itemView.findViewById(R.id.tv_time_limit_good_price);
                tvGoodPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvGoodPrice.getPaint().setAntiAlias(true);
                tvBuy = (TextView) itemView.findViewById(R.id.tv_time_limit_buy);
            }
            public void setOnItemClick(final TbkItem good){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                        intent.putExtra("num_iid",good.getNum_iid()+"");
                        MFGT.startActivity(context,intent);
                    }
                });
            }

            public void setOnTvBuyClick(final TbkItem good){
                tvBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (curHour > getBeginTime()){
                            Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                            intent.putExtra("num_iid",good.getNum_iid()+"");
                            MFGT.startActivity(context,intent);
                        }else{
                            Intent serviceI = new Intent(getActivity(), TimeLimitRemindService.class);
                            serviceI.putExtra("beginTime",getBeginTime());
                            getActivity().startService(serviceI);
                            mDialog = new AlertDialog.Builder(context);
                            mDialog.setTitle("开抢提醒");
                            mDialog.setMessage("已设置开抢提醒，将在开场前10分钟通知您。");
                            mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setCancelable(true).create().show();
                        }
                    }
                });
            }
        }
    }
}
