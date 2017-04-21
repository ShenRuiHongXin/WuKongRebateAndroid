package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/4/17.
 */

@EActivity(R.layout.activity_duobao_recode)
public class DuobaoRecodeActivity extends BaseActivity {
    @ViewById(R.id.tabs_duobao_recode)
    TabLayout tabs;
    @ViewById(R.id.rv_db_recode_content)
    RecyclerView rvContent;

    private String[] title = {"全部","已参与","已中奖"};
    private DbRecodeAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<MyRecode> listAll;
    private List<MyRecode> listJoinNotOpen;
    private List<MyRecode> listJoinOpen;
    private List<MyRecode> listGoal;

    @AfterViews
    void init(){
        initView();
    }

    void initView(){
        for (String s : title){
            TabLayout.Tab tab = tabs.newTab();
            tab.setText(s);
            tabs.addTab(tab);
        }
        listAll = new ArrayList();
        listJoinNotOpen = new ArrayList();
        listJoinOpen = new ArrayList();
        listGoal = new ArrayList();

        listJoinNotOpen.add(new MyRecode(false,false));
        listJoinNotOpen.add(new MyRecode(false,false));
        listJoinNotOpen.add(new MyRecode(false,false));

        listJoinOpen.add(new MyRecode(false,true));
        listJoinOpen.add(new MyRecode(false,true));
        listJoinOpen.add(new MyRecode(false,true));

        listGoal.add(new MyRecode(true,true));
        listGoal.add(new MyRecode(true,true));
        listGoal.add(new MyRecode(true,true));

        listAll.addAll(listJoinNotOpen);
        listAll.addAll(listGoal);
        listAll.addAll(listJoinOpen);

        adapter = new DbRecodeAdapter(this,listAll);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvContent.setAdapter(adapter);
        rvContent.setLayoutManager(layoutManager);
        changeData();
    }

    void changeData(){
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        listAll.clear();
                        listAll.addAll(listJoinNotOpen);
                        listAll.addAll(listGoal);
                        listAll.addAll(listJoinOpen);
                        break;
                    case 1:
                        listAll.clear();
                        listAll.addAll(listJoinNotOpen);
                        listAll.addAll(listJoinOpen);
                        break;
                    case 2:
                        listAll.clear();
                        listAll.addAll(listGoal);
                        break;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Click({R.id.iv_duo_recode_back})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_duo_recode_back:
                MFGT.finish(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

    private class DbRecodeAdapter extends RecyclerView.Adapter{
        private Context context;
        private List<MyRecode> data;

        public DbRecodeAdapter(Context context,List<MyRecode> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyRecodeViewHolder(LayoutInflater.from(context).inflate(R.layout.my_duobao_recode_item,null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyRecodeViewHolder myRecodeViewHolder = (MyRecodeViewHolder) holder;
            myRecodeViewHolder.tvGoalNumber.setText(data.get(position).hasOpen ? "中奖号码:10039" : "中奖号码:即将揭晓");
            myRecodeViewHolder.ivHasGoal.setVisibility(data.get(position).hasGoal ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyRecodeViewHolder extends RecyclerView.ViewHolder{
            private ImageView ivGoodImg;
            private ImageView ivHasGoal;
            private TextView tvGoodName;
            private TextView tvGoalNumber;
            private TextView tvMyNumber;
            private TextView tvJoinTimes;

            public MyRecodeViewHolder(View itemView) {
                super(itemView);
                ivGoodImg = (ImageView) itemView.findViewById(R.id.iv_db_my_recode_good_img);
                ivHasGoal = (ImageView) itemView.findViewById(R.id.iv_db_my_recode_goal);
                tvGoodName = (TextView) itemView.findViewById(R.id.tv_db_my_recode_good_name);
                tvGoalNumber = (TextView) itemView.findViewById(R.id.tv_db_my_recode_goal_number);
                tvMyNumber = (TextView) itemView.findViewById(R.id.tv_db_my_recode_my_number);
                tvJoinTimes = (TextView) itemView.findViewById(R.id.tv_db_my_recode_join_times);
            }
        }
    }
    class MyRecode{
        private boolean hasGoal;
        private boolean hasOpen;

        public MyRecode(boolean hasGoal, boolean hasOpen) {
            this.hasGoal = hasGoal;
            this.hasOpen = hasOpen;
        }

        public boolean isHasGoal() {
            return hasGoal;
        }

        public void setHasGoal(boolean hasGoal) {
            this.hasGoal = hasGoal;
        }

        public boolean isHasOpen() {
            return hasOpen;
        }

        public void setHasOpen(boolean hasOpen) {
            this.hasOpen = hasOpen;
        }
    }
}
