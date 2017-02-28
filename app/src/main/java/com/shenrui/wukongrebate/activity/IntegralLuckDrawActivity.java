package com.shenrui.wukongrebate.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.NineDrawView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by heikki on 2017/1/23.
 */

@EActivity(R.layout.activity_integral_luck_draw)
public class IntegralLuckDrawActivity extends BaseActivity {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    //轮播图
    @ViewById(R.id.crv_luckdraw)
    CycleRotationView crvLuckDraw;

    //抽奖
    @ViewById(R.id.ndv_luck_draw)
    NineDrawView nineDrawView;


    @AfterViews
    void init(){
        //标题栏
        listTitleView.get(3).setVisibility(View.GONE);
        ((TextView)listTitleView.get(2)).setText("积分抽奖");
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.nav_icon_back);
        listTitleView.get(0).setVisibility(View.GONE);

        //轮播图
        int[] imgs = {R.drawable.tmp_jifen_choujiang_1,R.drawable.tmp_jifen_choujiang_2,R.drawable.tmp_jifen_choujiang_3,R.drawable.tmp_jifen_choujiang_4,R.drawable.tmp_jifen_choujiang_5};

        crvLuckDraw.setImages(imgs);

        int[]prizesIcon={R.drawable.tmp_1,R.drawable.tmp_10,R.drawable.tmp_2,R.drawable.tmp_3,R.drawable.tmp_go,R.drawable.tmp_non,R.drawable.tmp_non,R.drawable.tmp_10rmb,R.drawable.tmp_50};
        nineDrawView.setPrizeImage(prizesIcon);
    }

    @Click(R.id.toolbar_left_image)
    void click(){
        MFGT.finish(this);
    }
}
