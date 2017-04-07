package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.view.MyPopWindow;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/3/21.
 */

@EActivity(R.layout.activity_food_j_common)
public class FoodJCommonActivity extends BaseActivity{
    //刷新组件
    @ViewById(R.id.food_j_common_srl)
    SwipeRefreshLayout srl;
    //内容
    @ViewById(R.id.food_j_common_rv)
    RecyclerView rv;
    @ViewById(R.id.et_food_j_common_search)
    EditText et;
    LinearLayoutManager layoutManager;
    GridLayoutManager gridLayoutManager = null;
    FoodAdapter adapter;
    List shopDatas;
    int mainDataType = -1;

    //下拉列表1
    @ViewById(R.id.rl_role)
    RelativeLayout rlRole;
    MyPopWindow popRole;
    @ViewById(R.id.tv_condition_1)
    TextView tvCondition1;
    String[] roles;
    //下拉列表2
    @ViewById(R.id.rl_mancount)
    RelativeLayout rlManCount;
    MyPopWindow popManCount;
    @ViewById(R.id.tv_condition_2)
    TextView tvCondition2;
    String[] manCounts = {"单人","双人","3~4人","5~10人","10人以上"};
    //下拉列表3
    @ViewById(R.id.rl_select)
    RelativeLayout rlSelect;
    MyPopWindow popSelect;
    @ViewById(R.id.tv_condition_3)
    TextView tvCondition3;
    String[] selects = {"好评最高","人均最低","人均最高","",""};

    @AfterViews
    void init(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Bundle bundle = getIntent().getExtras();
        String dataType = bundle.getString("dataType");
        LogUtil.d("dataType:" + dataType);
        if ("聚会".equals(dataType)){
            mainDataType = 1;
            tvCondition1.setText("角色");
            roles = new String[]{"朋友","恋人","同事","家人","其他"};
            gridLayoutManager = new GridLayoutManager(this, 2);
        }else if ("刁角美食".equals(dataType)){
            mainDataType = 2;
            tvCondition1.setText("种类");
            roles = new String[]{"甜品","小吃","酒水饮料","其他",""};
            gridLayoutManager = new GridLayoutManager(this, 2);
        }else if ("精品美食".equals(dataType)){
            mainDataType = 3;
            tvCondition1.setText("环境");
            roles = new String[]{"酒店","餐厅","庭院","其他",""};
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        }
        initData(mainDataType);
        if (gridLayoutManager != null){
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (position == adapter.getItemCount()-1) ? 2 : 1;
                }
            });
        }
        rv.setLayoutManager((gridLayoutManager == null) ? layoutManager : gridLayoutManager);
        adapter = new FoodAdapter(this,shopDatas);
        setLoadMoreListener();
        rv.setAdapter(adapter);
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        setListener();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FoodAdapter.LOAD_STATE_DEFAULT:
                    if (adapter.getFooterHolder() != null){
                        adapter.setLoadDefaultUI();
                        adapter.setLoadState(FoodAdapter.LOAD_STATE_DEFAULT);
                    }
                    break;
                case FoodAdapter.LOAD_STATE_FAILURE:
                    adapter.setLoadFailUI();
                    adapter.setLoadState(FoodAdapter.LOAD_STATE_FAILURE);
                    break;
                case FoodAdapter.LOAD_STATE_FINISH:
                    adapter.setLoadFinishUI();
                    adapter.setLoadState(FoodAdapter.LOAD_STATE_FINISH);
                    break;
            }
        }
    };
    private void setLoadMoreListener(){
        adapter.setLoadMoreListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (adapter.getLoadState()){
                    case FoodAdapter.LOAD_STATE_DEFAULT:
                        adapter.setloadingUI();
                        adapter.setLoadState(FoodAdapter.LOAD_STATE_LOADING);
                        handler.sendEmptyMessageDelayed(FoodAdapter.LOAD_STATE_FAILURE,3000);
                        break;
                    case FoodAdapter.LOAD_STATE_FAILURE:
                        adapter.setloadingUI();
                        adapter.setLoadState(FoodAdapter.LOAD_STATE_LOADING);
                        handler.sendEmptyMessageDelayed(FoodAdapter.LOAD_STATE_FINISH,3000);
                        break;
                    case FoodAdapter.LOAD_STATE_FINISH:
                        adapter.setloadingUI();
                        adapter.setLoadState(FoodAdapter.LOAD_STATE_LOADING);
                        handler.sendEmptyMessageDelayed(FoodAdapter.LOAD_STATE_FINISH,3000);
                        break;
                }
            }
        });
    }
    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
                handler.sendEmptyMessageDelayed(FoodAdapter.LOAD_STATE_DEFAULT,0);
            }
        });
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    dealSearchEvent();
                }
                return false;
            }
        });
    }

    void initData(int mainDataType){
        shopDatas = new ArrayList();
        switch (mainDataType){
            case 1:
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.juhui_home_img_one,"Soulmate coffee","客运港/江滩·咖啡厅",67));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.juhui_home_img_two,"18号酒馆","客运港/江滩·酒吧",83));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.juhui_home_img_three,"味藏日本料理(百瑞景店)","丁字桥·日本料理",124));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.juhui_home_img_four,"朱莉可可","硚口路·西餐",0));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.juhui_home_img_three,"味藏日本料理(百瑞景店)","丁字桥·日本料理",124));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.juhui_home_img_four,"朱莉可可","硚口路·西餐",0));
                break;
            case 2:
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.diaojiao_home_img_one,"Soulmate coffee","客运港/江滩·咖啡厅",67));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.diaojiao_home_img_two,"18号酒馆","客运港/江滩·酒吧",83));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.diaojiao_home_img_three,"味藏日本料理(百瑞景店)","丁字桥·日本料理",124));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.diaojiao_home_img_four,"朱莉可可","硚口路·西餐",0));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.diaojiao_home_img_five,"味藏日本料理(百瑞景店)","丁字桥·日本料理",124));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_GRID,R.drawable.diaojiao_home_img_six,"朱莉可可","硚口路·西餐",0));
                break;
            case 3:
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_JP,R.drawable.jingping_home_img_one,"艺江南庭院餐厅(汉阳造创意园)","钟家村·江浙菜",95,true,false,false));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_JP,R.drawable.jingping_home_img_two,"紫缘酒店-合缘宴","南湖花园·川菜",60,false,true,true));
                shopDatas.add(new FoodContentItem(FoodAdapter.TYPE_SHOP_JP,R.drawable.jingping_home_img_three,"紫缘酒店-合缘宴","南湖花园·川菜",60,false,true,true));
                break;
        }
    }

    @Click({R.id.iv_food_j_common_back,R.id.rl_role,R.id.rl_mancount,R.id.rl_select,R.id.tv_food_j_common_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_food_j_common_back:
                MFGT.finish(this);
                break;
            case R.id.rl_role:
                togglePop(1,popRole,rlRole,roles);
                break;
            case R.id.rl_mancount:
                togglePop(2,popManCount,rlManCount,manCounts);
                break;
            case R.id.rl_select:
                togglePop(3,popSelect,rlSelect,selects);
                break;
            case R.id.tv_food_j_common_search:
                dealSearchEvent();
                break;
        }
    }

    private void togglePop(int tag,MyPopWindow pop,View toggle,String[] datas){
        if (pop==null){
            initPopRole(tag,toggle,datas);
        }else{
            if (pop.isExpand()){
                pop.dismiss();
            }else{
                pop.showAsDropDown(toggle,0,ScreenUtils.dp2px(this,1));
            }
            pop.setExpand(!pop.isExpand());
        }
    }

    private void dealSearchEvent(){
        if (et.getText().toString().trim().isEmpty()){
            Toast.makeText(FoodJCommonActivity.this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
        }else{
            //隐藏键盘
            ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(FoodJCommonActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    void initPopRole(final int tag, View toggle, final String[] datas){
        //final String[] roles = {"朋友","恋人","同事","家人","其他"};
        View layout = LayoutInflater.from(this).inflate(R.layout.pop_select_layout, null);
        ListView listView = (ListView) layout.findViewById(R.id.listView_category);
        listView.setAdapter(new PopListAdapter(datas));

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        toggle.measure(w, h);
        int width =toggle.getMeasuredWidth();
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) listView.getLayoutParams();
        layoutParams.width = ScreenUtils.dp2px(this,width);
        listView.setLayoutParams(layoutParams);

                switch (tag){
                    case 1:
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(FoodJCommonActivity.this, datas[position], Toast.LENGTH_SHORT).show();
                                tvCondition1.setText(datas[position]);
                                popRole.dismiss();
                                popRole.setExpand(!popRole.isExpand());
                            }
                        });
                        popRole = new MyPopWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                        popRole.setOutsideTouchable(false);
                        popRole.showAsDropDown(toggle,0,ScreenUtils.dp2px(this,1));
                        popRole.setExpand(!popRole.isExpand());
                        break;
                    case 2:
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(FoodJCommonActivity.this, datas[position], Toast.LENGTH_SHORT).show();
                                tvCondition2.setText(datas[position]);
                                popManCount.dismiss();
                                popManCount.setExpand(!popManCount.isExpand());
                            }
                        });
                        popManCount = new MyPopWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                        popManCount.setOutsideTouchable(false);
                        popManCount.showAsDropDown(toggle,0,ScreenUtils.dp2px(this,1));
                        popManCount.setExpand(!popManCount.isExpand());
                        break;
                    case 3:
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(FoodJCommonActivity.this, datas[position], Toast.LENGTH_SHORT).show();
                                tvCondition3.setText(datas[position]);
                                popSelect.dismiss();
                                popSelect.setExpand(!popSelect.isExpand());
                            }
                        });
                        popSelect = new MyPopWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                        popSelect.setOutsideTouchable(false);
                        popSelect.showAsDropDown(toggle,0,ScreenUtils.dp2px(this,1));
                        popSelect.setExpand(!popSelect.isExpand());
                        break;
                }
    }

    private class PopListAdapter extends BaseAdapter{
        private String[] popContent;

        public PopListAdapter(String[] popContent) {
            this.popContent = popContent;
        }

        @Override
        public int getCount() {
            return popContent.length;
        }

        @Override
        public Object getItem(int position) {
            return popContent[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PopListHolder holder;
            if (convertView==null){
                convertView = LayoutInflater.from(FoodJCommonActivity.this).inflate(R.layout.pop_select_item, null);
                holder = new PopListHolder();
                holder.tv = (TextView) convertView.findViewById(R.id.tv_item);
                convertView.setTag(holder);
            }else{
                holder = (PopListHolder) convertView.getTag();
            }
            holder.tv.setText(popContent[position]);
            return convertView;
        }

        class PopListHolder{
            TextView tv;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
