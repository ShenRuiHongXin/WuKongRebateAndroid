package com.shenrui.wukongrebate.activity;

import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.FoodArticleAdapter;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_food_article)
public class FoodArticleActivity extends BaseActivity {
    @ViewById(R.id.et_food_article_search)
    EditText et;
    @ViewById(R.id.tv_food_article_search)
    TextView tvSearch;
    @ViewById(R.id.food_article_srl)
    SwipeRefreshLayout srl;
    @ViewById(R.id.food_article_rv)
    RecyclerView rv;

    FoodArticleAdapter adapter;
    LinearLayoutManager layoutManager;

    @AfterViews
    void init() {
        Window window = getWindow();
        window.setBackgroundDrawable(null);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        setListener();
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
            }
        });
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    if (et.getText().toString().trim().isEmpty()){
                        Toast.makeText(FoodArticleActivity.this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
                    }else{
                        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                                FoodArticleActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    }
                }
                return false;
            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        adapter = new FoodArticleAdapter(this, Constants.FOOD_ARTICLE);
        rv.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0,0,0,40);
            }
        });
    }

    @Click({R.id.iv_food_article_back,R.id.tv_food_article_search})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_food_article_back:
                MFGT.finish(this);
                break;
            case R.id.tv_food_article_search:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
