package com.shenrui.wukongrebate.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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

@EFragment(R.layout.mine_fragment_page)
public class FragmentMine extends Fragment {
    //标题栏
    @ViewsById({R.id.toolbar_left_text,R.id.toolbar_left_image,R.id.toolbar_title,R.id.toolbar_right_image})
    List<View> listTitleView;

    @AfterViews
    void init(){
        listTitleView.get(0).setVisibility(View.GONE);
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.mine_set_n);
        ((TextView)listTitleView.get(2)).setText("悟空");
        ((ImageView)listTitleView.get(3)).setImageResource(R.drawable.mine_message);
    }
}
