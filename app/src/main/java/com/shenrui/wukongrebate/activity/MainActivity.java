package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.shenrui.wukongrebate.R;

public class MainActivity extends BaseActivity{

    //git dev

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpDrawer();
    }

    private void setUpDrawer() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

}
