package com.shenrui.wukongrebate.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.List;


/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.food_fragment_page)
public class FragmentFood extends BaseFragment {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    @AfterViews
    void init(){
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.index_btn_city_n);
        ((TextView)listTitleView.get(2)).setText("美食馆");
        listTitleView.get(3).setVisibility(View.GONE);
        LogUtil.i("FragmentFood created");

    }

}
