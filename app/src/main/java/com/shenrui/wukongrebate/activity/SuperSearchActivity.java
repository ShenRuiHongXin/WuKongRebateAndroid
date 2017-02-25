package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

@EActivity(R.layout.activity_super_search)
public class SuperSearchActivity extends Activity {
    @ViewById(R.id.et_super_search)
    EditText etSearch;
    @ViewById(R.id.gridView_super_search_recommend)
    GridView gridView;
    @ViewById(R.id.gridView_super_search_history)
    GridView gridView_history;

    Context context;
    String[] texts_search_recommend;
    List<String> searchHistory;
    @AfterViews
    void init(){
        context = this;
        //禁止自动弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        texts_search_recommend = new String[]{"女装","男鞋","vivo","电脑桌","羽绒服",
                "内衣","防霾面具","夹克","衬衫","休闲裤","T恤"};
        List<String> list = Arrays.asList(texts_search_recommend);
        gridView.setAdapter(new SearchRecommendAdapter(list,this));
        initSearchHistory();
        setListener();
    }
    //初始化搜索历史模块
    private void initSearchHistory() {
        searchHistory = SharedPreferenceUtils.getInstance(this).getSuperSearchHistory();
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
                SharedPreferenceUtils.getInstance(context).putSuperSearchHistory(texts_search_recommend[position]);
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
                    SharedPreferenceUtils.getInstance(context).clearSuperSearchHistory();
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

    @Click({R.id.iv_super_search_back,R.id.tv_super_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_super_search_back:
                MFGT.finish(this);
                break;
            case R.id.tv_super_search:
                String goods = etSearch.getText().toString().trim();
                if (goods.isEmpty()){
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }else{
                    //将搜索关键词放入首选项
                    SharedPreferenceUtils.getInstance(this).putSuperSearchHistory(goods);

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
        Intent intent = new Intent(context,SuperSearchResultActivity_.class);
        intent.putExtra("super_search_goods",goods);
        MFGT.startActivity(context,intent);
    }

}
