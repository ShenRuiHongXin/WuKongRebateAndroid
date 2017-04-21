package com.shenrui.wukongrebate.fragment.duo;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.DuoBaoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_duo_item)
public class DuoItemFragment extends Fragment {
    @ViewById(R.id.duo_item_rv)
    RecyclerView rv;
    Context context;
    DuoBaoAdapter adapter;
    GridLayoutManager layoutManager;
    String title;

    @AfterViews
    void init(){
        context = getContext();
        title = getArguments().getString("title");
//        initView();
    }

//    private void initView() {
//        adapter = new DuoBaoAdapter(context);
//        rv.setAdapter(adapter);
//        layoutManager = new GridLayoutManager(context,2);
//        rv.setLayoutManager(layoutManager);
//        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                if (parent.getChildPosition(view)%2==0){
//                    outRect.set(10,5,5,5);
//                }else{
//                    outRect.set(5,5,10,5);
//                }
//            }
//        });
//    }
}
