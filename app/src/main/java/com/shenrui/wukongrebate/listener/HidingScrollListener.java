package com.shenrui.wukongrebate.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenrui.wukongrebate.utils.Utils;

/**
 * Created by heikki on 2017/1/7.
 */

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private int mToolbarOffset = 0;
    private int mToolbarHeight;

    public HidingScrollListener(Context context, View view) {
//        mToolbarHeight = Utils.getToolbarHeight(context);
//        mToolbarHeight = Utils.getToolbarHeight(context,view);
        mToolbarHeight = 144;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        clipToolbarOffset();
        onMoved(mToolbarOffset);

        if((mToolbarOffset <mToolbarHeight && dy>0) || (mToolbarOffset >0 && dy<0)) {
            mToolbarOffset += dy;
        }
    }

    private void clipToolbarOffset() {
        if(mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
        } else if(mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }
    }

    public abstract void onMoved(int distance);
}