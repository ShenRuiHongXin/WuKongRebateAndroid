package com.shenrui.wukongrebate.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;


/**
 * Created by heikki on 2016/12/28.
 */

public class FragmentFood extends Fragment {
    private TextView tv_toolbar_left;
    private ImageView iv_toolbar_right;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_fragment_page, container, false);
        init();
        return view;
    }

    void init(){
        tv_toolbar_left = (TextView) view.findViewById(R.id.toolbar_left_text);
        iv_toolbar_right = (ImageView) view.findViewById(R.id.toolbar_right_image);
        iv_toolbar_right.setVisibility(View.GONE);
        ((TextView)view.findViewById(R.id.toolbar_title)).setText("美食馆");
        ((ImageView)view.findViewById(R.id.toolbar_left_image)).setImageResource(R.drawable.index_btn_city_n);
    }
}
