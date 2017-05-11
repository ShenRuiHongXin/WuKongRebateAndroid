package com.shenrui.wukongrebate.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.DuoBaoAdapter;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.MyToast;
import com.shenrui.wukongrebate.utils.ScreenUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/4/15.
 */

@EActivity(R.layout.activity_duobao_search)
public class DuobaoSearchActivity extends BaseActivity {
    //搜索
    @ViewById(R.id.rl_db_search)
    RelativeLayout rlSearch;
    //搜索输入框
    @ViewById(R.id.et_db_search)
    EditText et;
    //标题
    @ViewById(R.id.tv_db_search_title)
    TextView tvTitle;
    //内容
    @ViewById(R.id.rv_db_search_content)
    RecyclerView rvContent;
    @ViewById(R.id.ll_db_search_hot)
    LinearLayout llSearchHot;
    //搜索历史
    @ViewById(R.id.rl_search_history_hint)
    RelativeLayout rlSearchHint;
    //搜索结果数量
    @ViewById(R.id.ll_db_search_result_count)
    LinearLayout llResultCount;
    @ViewById(R.id.tv_search_result_count)
    TextView tvResultCount;

    private int target;
    SQLiteDatabase db;
    private List<String> histories;
    private DuobaoSearchAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    List<DuoBaoAdapter.DuobaoData> datas;
    private DuoBaoAdapter DbAdapter;
    private SpaceItemDecoration itemDecoration;

    private String[] types = {"全部分类", "手机平板", "电脑办公", "数码影音"};

    @AfterViews
    void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setListener();
        Intent intent = getIntent();
        target = intent.getIntExtra("target", 0);
        LogUtil.d("跳转目标:" + target);
        if (target == DuobaoCatsActivity.SEARCH) {
            rlSearch.setVisibility(View.VISIBLE);
            initSearchHistory();
        } else {
            rlSearch.setVisibility(View.GONE);
            tvTitle.setText(types[target - 2]);
            initData();
        }
    }

    void initData(){
        if (datas == null){
            datas = new ArrayList<>();
        }else{
            datas.clear();
        }
//        for (int i = 0; i<10; i++){
//            if (i == 1){
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,100,50)));
//            }else if(i == 2){
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,100,100)));
//            }else if(i == 3){
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,100,0)));
//            }else{
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,(100+i),(50-i))));
//            }
//        }
        llSearchHot.setVisibility(View.GONE);
        llResultCount.setVisibility(View.VISIBLE);
        rlSearchHint.setVisibility(View.GONE);
        tvResultCount.setText(String.format(getResources().getString(R.string.search_count),datas.size()));
        DbAdapter = new DuoBaoAdapter(this,datas);
        DbAdapter.setLiner(true);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        if (itemDecoration != null){
            rvContent.removeItemDecoration(itemDecoration);
        }
        rvContent.setAdapter(DbAdapter);
        rvContent.setLayoutManager(linearLayoutManager);
    }

    @Click({R.id.iv_db_search_delete, R.id.iv_db_search_back,R.id.tv_clear_history,R.id.tv_db_search_hot_1,R.id.tv_db_search_hot_2,R.id.tv_db_search_hot_3,R.id.tv_db_search_hot_4})
    void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_db_search_back:
                MFGT.finish(this);
                break;
            case R.id.iv_db_search_delete:
                et.setText("");
                if(target == DuobaoCatsActivity.SEARCH){
                    if (DbAdapter != null){
                        datas.clear();
                        DbAdapter.notifyDataSetChanged();
                    }
                    initSearchHistory();
                }
                break;
            case R.id.tv_clear_history:
                //删除SQL语句
                String sql = "delete from duobao_search_history";
                //执行SQL语句
                db.execSQL(sql);
                histories.clear();
                initSearchHistory();
                break;
            case R.id.tv_db_search_hot_1:
            case R.id.tv_db_search_hot_2:
            case R.id.tv_db_search_hot_3:
            case R.id.tv_db_search_hot_4:
                addSearchHistory(((TextView)view).getText().toString());
                initData();
                break;
        }
    }

    //显示搜索历史
    void initSearchHistory() {
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.shenrui.wukongrebate/databases/wukong.db", null);
        //创建表SQL语句
        String duobao_search_history = "create table if not exists duobao_search_history(_id integer primary key autoincrement,search_content text)";
        //执行SQL语句
        db.execSQL(duobao_search_history);
        Cursor cursor = db.query("duobao_search_history", null, null, null, null, null, null);
        histories = new ArrayList<>();
        //判断游标是否为空
        while (cursor.moveToNext()){
                //获得搜索内容
                String search_content = cursor.getString(cursor.getColumnIndex("search_content"));
            histories.add(search_content);
        }
        llSearchHot.setVisibility(View.VISIBLE);
        llResultCount.setVisibility(View.GONE);
        rlSearchHint.setVisibility(histories.size() != 0 ? View.VISIBLE : View.GONE);
        adapter = new DuobaoSearchAdapter(this,histories);
        gridLayoutManager = new GridLayoutManager(this,3);
        rvContent.setAdapter(adapter);
        rvContent.setLayoutManager(gridLayoutManager);
        if (itemDecoration != null){
            rvContent.removeItemDecoration(itemDecoration);
        }else{
            itemDecoration = new SpaceItemDecoration(ScreenUtils.dp2px(this,10));
        }
        rvContent.addItemDecoration(itemDecoration);
    }

    //保存搜索历史
    void addSearchHistory(String history){
        Cursor cursor = db.query("duobao_search_history", null, null, null, null, null, null);
        //判断游标是否为空
        while (cursor.moveToNext()){
            //获得搜索内容
            String search_content = cursor.getString(cursor.getColumnIndex("search_content"));
            if (history.equals(search_content)){
                return;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("search_content", history);
        db.insert("duobao_search_history", null, contentValues);
    }
    //搜索框监听
    private void setListener() {
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (et.getText().toString().trim().isEmpty()) {
                        Toast.makeText(DuobaoSearchActivity.this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
                    } else {
                        //隐藏键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(DuobaoSearchActivity.this.getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);

                        addSearchHistory(et.getText().toString().trim());
                    }
                }
                return false;
            }
        });
    }

    private class DuobaoSearchAdapter extends RecyclerView.Adapter{
        private static final int HISTORY = 1;
        private static final int GOOD = 2;
        private Context context;
        private List data;

        public DuobaoSearchAdapter(Context context, List data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            RecyclerView.ViewHolder holder = null;
            View view;
            switch (viewType){
                case HISTORY:
                    view = inflater.inflate(R.layout.cat_son,parent,false);
                    holder = new SearchHistoryHolder(view);
                    break;
                case GOOD:
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)){
                case HISTORY:
                    SearchHistoryHolder searchHistoryHolder = (SearchHistoryHolder) holder;
                    searchHistoryHolder.tvHistory.setText((String)data.get(position));
                    searchHistoryHolder.setOnItemClick((String)data.get(position));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (data.get(0) instanceof String) ? HISTORY : GOOD;
        }

        class SearchHistoryHolder extends RecyclerView.ViewHolder{
            private TextView tvHistory;

            public SearchHistoryHolder(View itemView) {
                super(itemView);
                tvHistory = (TextView) itemView.findViewById(R.id.tv_cat_son_name);
            }
            public void setOnItemClick(final String history){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyToast.showToast(context,history);
                    }
                });
            }
        }

    }
    class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.top = space;
//            outRect.right = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
//            if (parent.getChildLayoutPosition(view) < 3){
//                outRect.top = space;
//            }
            if ((parent.getChildLayoutPosition(view)+1) %3==0) {
                outRect.right = space;
            }
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
