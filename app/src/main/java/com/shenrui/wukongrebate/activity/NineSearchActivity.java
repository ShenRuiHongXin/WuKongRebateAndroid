package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

//九块九商品搜索界面
@EActivity(R.layout.activity_nine_search)
public class NineSearchActivity extends BaseActivity {
    @ViewById(R.id.iv_nine_search_back)
    ImageView ivBack;
    @ViewById(R.id.et_nine_search)
    EditText etSearch;
    @ViewById(R.id.tv_nine_search)
    TextView tvSearch;
    @ViewById(R.id.gridView_search_recommend)
    GridView gridView;
    @ViewById(R.id.gridView_search_history)
    GridView gridView_history;

    String[] texts_search_recommend;
    List<String> searchHistory;
    @AfterViews
    void init(){
        //禁止自动弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        texts_search_recommend = new String[]{"袜子","拖鞋","耳机","鼠标","羽绒服",
                                        "内裤","睡衣","手机壳","数据线"};
        gridView.setAdapter(new SearchRecommendAdapter(texts_search_recommend,this));
        initSearchHistory();
        setListener();
    }
    //初始化搜索历史模块
    private void initSearchHistory() {
        searchHistory = SharedPreferenceUtils.getInstance(this).getNineSearchHistory();
        if (searchHistory==null){
            gridView_history.setVisibility(View.GONE);
        }else{
            gridView_history.setVisibility(View.VISIBLE);
            gridView_history.setAdapter(new SearchHistoryAdapter(searchHistory,this));
        }
    }

    private void setListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将热搜关键词放入首选项
                SharedPreferenceUtils.getInstance(NineSearchActivity.this).putNineSearchHistory(texts_search_recommend[position]);
                //携带热搜关键词进入搜索结果界面
                gotoSearchResultActivity(texts_search_recommend[position]);
            }
        });
        gridView_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == searchHistory.size()){
                    gridView_history.setVisibility(View.GONE);
                    //删除首选项中历史搜索
                    SharedPreferenceUtils.getInstance(NineSearchActivity.this).clearNineSearchHistory();
                }else{
                    //携带历史搜索关键词进入搜索结果界面
                    gotoSearchResultActivity(searchHistory.get(position));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新搜索历史数据
        initSearchHistory();
    }

    @Click({R.id.iv_nine_search_back,R.id.tv_nine_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_nine_search_back:
                MFGT.finish(this);
                break;
            case R.id.tv_nine_search:
                String goods = etSearch.getText().toString().trim();
                if (goods.isEmpty()){
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }else{
                    //将搜索关键词放入首选项
                    SharedPreferenceUtils.getInstance(this).putNineSearchHistory(goods);

                    gotoSearchResultActivity(goods);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

    //进入搜索结果界面
    public void gotoSearchResultActivity(String goods){
        Intent intent = new Intent(NineSearchActivity.this,NineSearchResultActivity_.class);
        intent.putExtra("search_goods",goods);
        MFGT.startActivity(NineSearchActivity.this,intent);
    }

    //推荐搜索关键词适配器
    class SearchRecommendAdapter extends BaseAdapter{
        String[] texts;
        Context context;
        SearchRecommendAdapter(String[] texts, Context context) {
            this.texts = texts;
            this.context = context;
        }

        @Override
        public int getCount() {
            return texts.length;
        }

        @Override
        public Object getItem(int position) {
            return texts[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null){
                holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_search_recommend_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_search_recommend);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            holder.tv.setText(texts[position]);
            return convertView;
        }

        class Holder{
            TextView tv;
        }
    }

    //历史搜索适配器
    class SearchHistoryAdapter extends BaseAdapter{
        private static final int TYPE_HISTORT_ITEM = 0;
        private static final int TYPE_CLEAR_LAST = 1;//清除搜索历史的item
        List<String> texts;
        Context context;
        SearchHistoryAdapter(List<String> texts_history, Context context) {
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
            if (viewType == TYPE_HISTORT_ITEM){
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
                return TYPE_HISTORT_ITEM;
            }
        }

    }
}
