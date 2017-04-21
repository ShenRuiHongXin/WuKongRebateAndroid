package com.shenrui.wukongrebate.activity;

import android.content.Intent;
import android.view.View;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * Created by heikki on 2017/4/15.
 */

@EActivity(R.layout.activity_duobao_cats)
public class DuobaoCatsActivity extends BaseActivity {
    public static final int SEARCH = 1;
    public static final int ALL = 2;
    public static final int PHONE = 3;
    public static final int PC = 4;
    public static final int C3 = 5;

    @Click({R.id.iv_duo_cats_back,R.id.rl_db_cat_search,R.id.rl_db_cat_all,R.id.rl_db_cat_phone,R.id.rl_db_cat_pc,R.id.rl_db_cat_3c})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_duo_cats_back:
                MFGT.finish(this);
                break;
            case R.id.rl_db_cat_search:
            case R.id.rl_db_cat_all:
            case R.id.rl_db_cat_phone:
            case R.id.rl_db_cat_pc:
            case R.id.rl_db_cat_3c:
                Intent intent = new Intent(this,DuobaoSearchActivity_.class);
                if (view.getId() == R.id.rl_db_cat_search){
                    intent.putExtra("target",SEARCH);
                }else if(view.getId() == R.id.rl_db_cat_all){
                    intent.putExtra("target",ALL);
                }else if(view.getId() == R.id.rl_db_cat_phone){
                    intent.putExtra("target",PHONE);
                }else if(view.getId() == R.id.rl_db_cat_pc){
                    intent.putExtra("target",PC);
                }else if(view.getId() == R.id.rl_db_cat_3c){
                    intent.putExtra("target",C3);
                }else{
                    intent.putExtra("target",0);
                }
                MFGT.startActivity(this,intent);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
