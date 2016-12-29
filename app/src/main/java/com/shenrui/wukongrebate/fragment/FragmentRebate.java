package com.shenrui.wukongrebate.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

import static android.R.attr.button;

/**
 * Created by heikki on 2016/12/28.
 */

@EFragment(R.layout.rebate_fragment_page)
public class FragmentRebate extends Fragment {
     TextView tv_toolbar_left;
     ImageView iv_toolbar_left;
     View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rebate_fragment_page, container, false);
        init();
        return view;
    }

    void init(){
        tv_toolbar_left = (TextView) view.findViewById(R.id.toolbar_left_text);
        iv_toolbar_left = (ImageView) view.findViewById(R.id.toolbar_left_image);
        ((TextView)view.findViewById(R.id.toolbar_title)).setText("悟空返利");
        iv_toolbar_left.setImageResource(R.drawable.index_btn_city_n);
    }
}
