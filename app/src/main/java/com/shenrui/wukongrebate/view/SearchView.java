package com.shenrui.wukongrebate.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shenrui.wukongrebate.R;

/**
 * Android自定义SearchView 
 * Created by yuandl  
 */

public class SearchView extends LinearLayout implements TextWatcher {
    /**
     * 输入框 
     */
    private EditText et_search;
    /**
     * 输入框后面的那个搜索按钮
     */
    private Button bt_find;


    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.pub_searchview, this, true);
        /***找出控件*/
        et_search = (EditText) findViewById(R.id.et_search);
        bt_find = (Button) findViewById(R.id.bt_find);
//        bt_clear.setVisibility(GONE);
        et_search.addTextChangedListener(this);
        //bt_find.setOnClickListener(this);
    }

    public void setEditTextOnlickListener(OnClickListener onlickListener){
        et_search.setFocusable(false);
        et_search.setClickable(true);
        et_search.setOnClickListener(onlickListener);
    }
    public void setFindOnClickListener(OnClickListener onlickListener){
        bt_find.setOnClickListener(onlickListener);
    }
    public String getEditText(){
        return et_search.getText().toString().trim();
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**** 
     * 对用户输入文字的监听 
     *
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {
        /**获取输入文字**/
        String input = et_search.getText().toString().trim();
//        if (input.isEmpty()) {
//            bt_clear.setVisibility(GONE);
//        } else {
//            bt_clear.setVisibility(VISIBLE);
//        }
    }

}