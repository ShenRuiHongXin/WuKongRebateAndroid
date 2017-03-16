package com.shenrui.wukongrebate.fragment.duo;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.DuoBaoActivity;
import com.shenrui.wukongrebate.adapter.DuoBaoAdapter;
import com.shenrui.wukongrebate.utils.FullyGridLayoutManager;
import com.shenrui.wukongrebate.view.CycleRotationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_ren_qi)
public class RenQiFragment extends Fragment {
    @ViewById(R.id.duo_crv)
    CycleRotationView crv;
    @ViewById(R.id.duo_rq_rv)
    RecyclerView rv;

    Context context;
    DuoBaoAdapter adapter;
    FullyGridLayoutManager layoutManager;

    @AfterViews
    void init(){
        context = getContext();
        initCycle();
        initView();
    }

    private void initView() {
        adapter = new DuoBaoAdapter(context);
        rv.setAdapter(adapter);
        layoutManager = new FullyGridLayoutManager(context,2);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view)%2==0){
                    outRect.set(10,5,5,5);
                }else{
                    outRect.set(5,5,10,5);
                }
            }
        });
    }

    private void initCycle() {
        int[] images = {R.drawable.home_banner,R.drawable.home_banner,R.drawable.home_banner};
        crv.setImages(images);
    }

}
