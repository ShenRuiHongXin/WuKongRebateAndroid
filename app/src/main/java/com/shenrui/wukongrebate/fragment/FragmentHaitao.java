package com.shenrui.wukongrebate.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.haitao_fragment_page)
public class FragmentHaitao extends Fragment {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    @AfterViews
    void init(){
        listTitleView.get(0).setVisibility(View.GONE);
        listTitleView.get(1).setVisibility(View.GONE);
        ((TextView)listTitleView.get(2)).setText("海淘");
        listTitleView.get(3).setVisibility(View.GONE);
    }
}
