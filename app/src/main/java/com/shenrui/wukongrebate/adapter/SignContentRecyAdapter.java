package com.shenrui.wukongrebate.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.SignActivity_;
import com.shenrui.wukongrebate.entities.RecyItemIndexData;
import com.shenrui.wukongrebate.entities.SignRecyItemData;
import com.shenrui.wukongrebate.entities.SplitlineItem;
import com.shenrui.wukongrebate.entities.TaskItem;
import com.shenrui.wukongrebate.entities.TenGoodsData;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.view.ActivityView;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.MyGridView;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import java.util.List;

import static com.shenrui.wukongrebate.entities.SignRecyItemData.CATS;
import static com.shenrui.wukongrebate.entities.SignRecyItemData.CATS_GOODS_ITEM;
import static com.shenrui.wukongrebate.entities.SignRecyItemData.ITEM_SPLITLINE;
import static com.shenrui.wukongrebate.entities.SignRecyItemData.ITEM_TASK;
import static com.shenrui.wukongrebate.entities.SignRecyItemData.ITEM_USER;
import static com.shenrui.wukongrebate.entities.SignRecyItemData.MAIN_CONTENT;
import static com.shenrui.wukongrebate.entities.SignRecyItemData.MAIN_ITEM;

/**
 * Created by heikki on 2017/01/03.
 */

public class SignContentRecyAdapter extends RecyclerView.Adapter{

    Context context;
    List<SignRecyItemData>  listDatas;
    TabLayout.OnTabSelectedListener onTabSelectedListener;

    public SignContentRecyAdapter(Context context, List<SignRecyItemData> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }

    public SignContentRecyAdapter(Context context, List listDatas, TabLayout.OnTabSelectedListener onTabSelectedListener) {
        this.context = context;
        this.listDatas = listDatas;
        this.onTabSelectedListener = onTabSelectedListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        switch (viewType){
            case CATS:
                break;
            case MAIN_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.main_recy_item_index, parent, false);
                holder = new MyViewHolderMainRecyIndex(view);
                break;
            case CATS_GOODS_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.main_recy_item_cats_content, parent, false);
                holder = new MyViewHolderMainRecyCatsGoods(view);
                break;
            case MAIN_CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.main_recy_item_index, parent, false);
                holder = new MyViewHolderMainRecyIndex(view);
                break;
            case ITEM_USER:
            case ITEM_TASK:
                view = LayoutInflater.from(context).inflate(R.layout.sign_content_recy_user_task_item, parent, false);
                holder = new MyViewHolderUserTask(view);
                break;
            case ITEM_SPLITLINE:
                view = LayoutInflater.from(context).inflate(R.layout.sign_content_recy_splitline, parent, false);
                holder = new MyViewHolderSplitline(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case CATS:
                break;
            case MAIN_ITEM:
                MyViewHolderMainRecyIndex myViewHolderMainRecyIndex = (MyViewHolderMainRecyIndex) holder;
                RecyItemIndexData recyItemIndexData = (RecyItemIndexData) listDatas.get(position).getT();
                myViewHolderMainRecyIndex.cyclerotationview.setUrls(recyItemIndexData.getCycleList());

                myViewHolderMainRecyIndex.mgv_sign.setAdapter(new MyGridAdapter(context));
                myViewHolderMainRecyIndex.mgv_sign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                Intent intent = new Intent(context, SignActivity_.class);
                                if(Build.VERSION.SDK_INT >= 21){
                                    //动画效果
                                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, view, "sharedView").toBundle());
                                }else{
                                context.startActivity(intent);
                                }
//                                context.startActivity(new Intent(context,SignActivity_.class));
                                break;
                        }
                    }
                });


                myViewHolderMainRecyIndex.ten_new_goods_recy.addItemDecoration(new DividerGridItemDecoration(context));
                int spanCount = 2;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                myViewHolderMainRecyIndex.ten_new_goods_recy.setLayoutManager(gridLayoutManager);

                final List listTen = recyItemIndexData.getTenList();
                RecyTenNewGoodsAdapter recyTenNewGoodsAdapter = new RecyTenNewGoodsAdapter(context,listTen);
                recyTenNewGoodsAdapter.setOnItemClickLitener(new RecyTenNewGoodsAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        LogUtil.d(((TenGoodsData)listTen.get(position)).getNum_iid());
                        Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                        intent.putExtra("cid",((TenGoodsData)listTen.get(position)).getNum_iid());
                        context.startActivity(intent);
                    }
                });
                myViewHolderMainRecyIndex.ten_new_goods_recy.setAdapter(recyTenNewGoodsAdapter);

                break;
            case MAIN_CONTENT:
                break;
            case CATS_GOODS_ITEM:
                break;
            case ITEM_USER:
                MyViewHolderUserTask myViewHolderUser = (MyViewHolderUserTask) holder;

                myViewHolderUser.iv_item_icon.setImageResource(R.drawable.index_sign_img1);

                myViewHolderUser.tv_item_title.setText("xxxx积分\n今日可领xx积分");
                myViewHolderUser.tv_line.setVisibility(View.GONE);
                myViewHolderUser.tv_shop_name.setVisibility(View.GONE);

                myViewHolderUser.tv_item_content.setVisibility(View.GONE);

                myViewHolderUser.ll_jiangli.setVisibility(View.GONE);

                myViewHolderUser.tv_to_compl.setText("签到，赚积分");
                break;
            case ITEM_SPLITLINE:
                MyViewHolderSplitline myViewHolderSplitline = (MyViewHolderSplitline) holder;
                if(((SplitlineItem)listDatas.get(position).getT()).getLineType() == SplitlineItem.OFFICIAL_TASK_LINE){
                    myViewHolderSplitline.tv_sign_task_nomore.setVisibility(View.GONE);
                    myViewHolderSplitline.tv_splitline_type.setVisibility(View.VISIBLE);
                    myViewHolderSplitline.tv_splitline_type.setText("官方任务");
                }else if(((SplitlineItem)listDatas.get(position).getT()).getLineType() == SplitlineItem.SHOP_TASK_LINE){
                    myViewHolderSplitline.tv_sign_task_nomore.setVisibility(View.GONE);
                    myViewHolderSplitline.tv_splitline_type.setText("商家任务");
                }else if (((SplitlineItem)listDatas.get(position).getT()).getLineType() == SplitlineItem.CENTER_LINE){
                    myViewHolderSplitline.tv_splitline_type.setVisibility(View.GONE);
                    myViewHolderSplitline.tv_sign_task_nomore.setVisibility(View.GONE);
                }else if (((SplitlineItem)listDatas.get(position).getT()).getLineType() == SplitlineItem.END_LINE){
                    myViewHolderSplitline.tv_splitline_type.setVisibility(View.GONE);
                    myViewHolderSplitline.tv_sign_task_nomore.setVisibility(View.VISIBLE);
                }

                break;
            case ITEM_TASK:
                MyViewHolderUserTask myViewHolderTask = (MyViewHolderUserTask) holder;

                myViewHolderTask.iv_item_icon.setImageResource(R.drawable.index_sign_img5);

                if(((TaskItem)listDatas.get(position).getT()).getTaskType() == TaskItem.OFFICIAL_TASK){
                    myViewHolderTask.tv_item_title.setText("首页签到");
                    myViewHolderTask.tv_item_content.setText("点击签到按钮，领取每日福利");
                    myViewHolderTask.tv_line.setVisibility(View.GONE);
                    myViewHolderTask.tv_shop_name.setVisibility(View.GONE);
                }else if(((TaskItem)listDatas.get(position).getT()).getTaskType() == TaskItem.SHOP_TASK){
                    myViewHolderTask.tv_item_title.setText("店铺签到");
                    myViewHolderTask.tv_item_content.setText("xxxx人已完成");
                    myViewHolderTask.tv_shop_name.setText("xxxx店铺");
                }
                myViewHolderTask.tv_count.setText("99");
                myViewHolderTask.tv_to_compl.setText("去完成");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (listDatas.get(position).getDataType()){
            case ITEM_USER:
                return ITEM_USER;
            case ITEM_SPLITLINE:
                return ITEM_SPLITLINE;
            case ITEM_TASK:
                return ITEM_TASK;
            case MAIN_ITEM:
                return MAIN_ITEM;
            case CATS_GOODS_ITEM:
                return CATS_GOODS_ITEM;
            case CATS:
                return CATS;
            case MAIN_CONTENT:
                return MAIN_CONTENT;
            default:
                return -1;
        }
    }

    /**
     * 签到积分，任务item ViewHolder
     */
    class MyViewHolderUserTask extends RecyclerView.ViewHolder {

        //图标
        ImageView iv_item_icon;

        //任务名称
        TextView tv_item_title;
        //分割竖线
        TextView tv_line;
        //店铺名称
        TextView tv_shop_name;

        //任务详情
        TextView tv_item_content;

        //积分奖励
        LinearLayout ll_jiangli;
        //奖励数量
        TextView tv_count;

        //去完成按钮
        TextView tv_to_compl;


        public MyViewHolderUserTask(View view) {
            super(view);
            iv_item_icon = (ImageView) view.findViewById(R.id.iv_sign_recy_item);
            tv_item_title = (TextView) view.findViewById(R.id.tv_sign_recy_item_title);
            tv_line = (TextView) view.findViewById(R.id.tv_sign_line);
            tv_shop_name = (TextView) view.findViewById(R.id.tv_sign_recy_item_shop_name);
            tv_item_content = (TextView) view.findViewById(R.id.tv_sign_recy_item_content);
            ll_jiangli = (LinearLayout) view.findViewById(R.id.ll_sign_jiangli);
            tv_count = (TextView) view.findViewById(R.id.tv_sign_recy_item_count);
            tv_to_compl = (TextView) view.findViewById(R.id.tv_sign_recy_to_compl);
        }
    }

    /**
     * 签到积分，分隔线ViewHolder
     */
    class MyViewHolderSplitline extends RecyclerView.ViewHolder {

        TextView tv_splitline_type;
        TextView tv_sign_task_nomore;

        public MyViewHolderSplitline(View view) {
            super(view);
            tv_splitline_type = (TextView) view.findViewById(R.id.tv_sign_recy_slitline_type);
            tv_sign_task_nomore = (TextView) view.findViewById(R.id.tv_sign_task_nomore);
        }
    }

    /**
     * 悟空返利，APP首页分类ViewHolder
     */
    class MyViewHolderMainRecyCats extends RecyclerView.ViewHolder{
        TabLayout tl_goods_category;
        public MyViewHolderMainRecyCats(View itemView) {
            super(itemView);
            tl_goods_category = (TabLayout) itemView.findViewById(R.id.tl_goods_category);
        }
    }

    /**
     * 悟空返利, APP首页ViewHolder
     */
    class MyViewHolderMainRecyIndex extends RecyclerView.ViewHolder{
//        TabLayout tl_goods_category;
        CycleRotationView cyclerotationview;
        LinearLayout ll_activity;
        MyGridView mgv_sign;
        ActivityView activity_view;
        RecyclerView ten_new_goods_recy;

        public MyViewHolderMainRecyIndex(View view) {
            super(view);
//            tl_goods_category = (TabLayout) view.findViewById(R.id.tl_goods_category);
            cyclerotationview = (CycleRotationView) view.findViewById(R.id.cyclerotationview);
            ll_activity = (LinearLayout) view.findViewById(R.id.ll_activity);
            mgv_sign = (MyGridView) view.findViewById(R.id.mgv_sign);
            activity_view = (ActivityView) view.findViewById(R.id.activity_view);
            ten_new_goods_recy = (RecyclerView) view.findViewById(R.id.ten_new_goods_recy);
        }
    }

    /**
     * 悟空返利，APP分类商品ViewHolder
     */
    class MyViewHolderMainRecyCatsGoods extends RecyclerView.ViewHolder{
        private final int ONE_LINE_SHOW_NUMBER = 2;
//        TabLayout tl_goods_category;
//        TextView tv_cats_test;
        RecyclerView recy_cats_goods_lists;

        public MyViewHolderMainRecyCatsGoods(View view) {
            super(view);
//            tl_goods_category = (TabLayout) view.findViewById(R.id.tl_goods_category);
//            tv_cats_test = (TextView) view.findViewById(R.id.textView);
            recy_cats_goods_lists = (RecyclerView) view.findViewById(R.id.recy_cats_goods_lists);

        }


    }
}
