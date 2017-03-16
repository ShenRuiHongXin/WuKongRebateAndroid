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
        getWindow().setBackgroundDrawable(null);
        //禁止自动弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        texts_search_recommend = new String[]{"袜子","拖鞋","耳机","鼠标","羽绒服",
                                        "内裤","睡衣","手机壳","数据线"};
        List<String> list = Arrays.asList(texts_search_recommend);
        gridView.setAdapter(new SearchRecommendAdapter(list,this));
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
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    String str = etSearch.getText().toString().trim();
                    if (str.isEmpty()){
                        Toast.makeText(NineSearchActivity.this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
                    }else{
                        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(NineSearchActivity.this.getCurrentFocus().getWindowToken()
                                ,InputMethodManager.HIDE_NOT_ALWAYS);
                        SharedPreferenceUtils.getInstance(NineSearchActivity.this).putNineSearchHistory(str);
                        gotoSearchResultActivity(str.trim());
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

    @Click({R.id.iv_nine_search_back,R.id.tv_nine_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_nine_search_back:
                MFGT.finish(this);
                break;
            case R.id.tv_nine_search:
                String goods = etSearch.getText().toString().trim();
                if (goods.isEmpty()){
                    Toast.makeText(this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
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

}
