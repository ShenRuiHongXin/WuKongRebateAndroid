package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

//品牌页展示
@EActivity(R.layout.activity_brand)
public class BrandActivity extends Activity {
    @ViewById(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @ViewById(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @ViewById(R.id.backdrop)
    ImageView iv;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @AfterViews
    void init(){
        collapsingToolbarLayout.setTitle("品牌名称");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        floatingActionButton.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.nav_icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.finish(BrandActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
