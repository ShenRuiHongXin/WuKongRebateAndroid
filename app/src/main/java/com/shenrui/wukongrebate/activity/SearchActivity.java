package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.SearchHistoryAdapter;
import com.shenrui.wukongrebate.adapter.SearchRecommendAdapter;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.SharedPreferenceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;


@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    @ViewById(R.id.iv_back)
    ImageView ivBack;
    @ViewById(R.id.et_search)
    EditText etSearch;
    @ViewById(R.id.tv_search)
    TextView tvSearch;
    @ViewById(R.id.gridView_index_search_recommend)
    GridView gridView;
    @ViewById(R.id.gridView_index_search_history)
    GridView gridView_history;

    String[] texts_search_recommend;
    List<String> searchHistory;
    @AfterViews
    void init(){
        getWindow().setBackgroundDrawable(null);
        //禁止自动弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        texts_search_recommend = new String[]{"女装","男装","夹克","牛仔","羽绒服",
                "睡衣","内衣","运动鞋","高跟鞋"};
        List<String> list = Arrays.asList(texts_search_recommend);
        gridView.setAdapter(new SearchRecommendAdapter(list,this));
        initSearchHistory();
        setListener();
    }
    //初始化搜索历史模块
    private void initSearchHistory() {
        searchHistory = SharedPreferenceUtils.getInstance(this).getSearthHistory();
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
                SharedPreferenceUtils.getInstance(SearchActivity.this).putSearthHistory(texts_search_recommend[position]);
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
                    SharedPreferenceUtils.getInstance(SearchActivity.this).clearAllHistory();
                }else{
                    //携带历史搜索关键词进入搜索结果界面
                    gotoSearchResultActivity(searchHistory.get(position));
                }
            }
        });
        //设置键盘监听
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){//修改回车键功能
                    String str = etSearch.getText().toString().trim();
                    if (str.isEmpty()){
                        Toast.makeText(SearchActivity.this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
                    }else{
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(
                                        SearchActivity.this.getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        SharedPreferenceUtils.getInstance(SearchActivity.this).putSearthHistory(str);
                        gotoSearchResultActivity(str);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新搜索历史数据
        initSearchHistory();
    }

    @Click({R.id.iv_back,R.id.tv_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_back:
                MFGT.finish(this);
                break;
            case R.id.tv_search:
                String goods = etSearch.getText().toString().trim();
                if (goods.isEmpty()){
                    Toast.makeText(this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
                }else{
                    //将搜索关键词放入首选项
                    SharedPreferenceUtils.getInstance(this).putSearthHistory(goods);

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
        Intent intent = new Intent(SearchActivity.this,SearchResultActivity_.class);
        intent.putExtra("search_goods",goods);
        MFGT.startActivity(SearchActivity.this,intent);
    }
}
