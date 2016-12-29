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

import org.androidannotations.annotations.EFragment;

/**
 * Created by heikki on 2016/12/28.
 */

public class FragmentMine extends Fragment {
    TextView tv_toolbar_left;
    ImageView iv_toolbar_left;
    ImageView iv_toolbar_right;
    Toolbar mToolbar;
     View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment_page, container, false);
        init();
        return view;
    }

    void init(){
        tv_toolbar_left = (TextView) view.findViewById(R.id.toolbar_left_text);
        iv_toolbar_left = (ImageView) view.findViewById(R.id.toolbar_left_image);
        iv_toolbar_right = (ImageView) view.findViewById(R.id.toolbar_right_image);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

//        mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.mineRed));
        tv_toolbar_left.setVisibility(View.GONE);

        iv_toolbar_left.setImageResource(R.drawable.mine_set_n);
        iv_toolbar_right.setImageResource(R.drawable.mine_message);
        ((TextView)view.findViewById(R.id.toolbar_title)).setText("悟空");
    }
}
