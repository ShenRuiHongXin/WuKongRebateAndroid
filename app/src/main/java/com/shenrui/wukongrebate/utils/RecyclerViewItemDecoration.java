package com.shenrui.wukongrebate.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/1/24.
 */

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public RecyclerViewItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(space/2,0,space/2,space);
    }
}
