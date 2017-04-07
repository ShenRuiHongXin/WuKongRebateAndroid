package com.shenrui.wukongrebate.fragment.foodmenu;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.CookbookCats;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MyToast;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.view.MyRecycleView;
import com.shenrui.wukongrebate.view.MyVerticalViewPager;

import java.util.List;

/**
 * Created by heikki on 2017/4/1.
 */

public class FragmentCatSon extends Fragment {
    private MyRecycleView rvCatSon;
    private CatSonAdapter catSonAdapter;
    private GridLayoutManager layoutManager;
    private MyVerticalViewPager verticalViewPager;
//    private GridView gvCatSon;
    private RelativeLayout layout;

    public FragmentCatSon() {
    }

    public static Fragment newInstance(CookbookCats cats, MyVerticalViewPager viewPager) {
        Bundle args = new Bundle();
        args.putSerializable("cats", cats);
        args.putSerializable("viewPager",viewPager);
        FragmentCatSon fragment = new FragmentCatSon();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d("FragmentCatSon onCreateView");
        View view = inflater.inflate(R.layout.fragment_cat_son, container, false);
        rvCatSon = (MyRecycleView) view.findViewById(R.id.rv_cat_son);
//        gvCatSon = (GridView) view.findViewById(R.id.gv_cat_son);
//        linearLayout = (LinearLayout) view.findViewById(R.id.ll_cat_son);
        layout = (RelativeLayout) view.findViewById(R.id.ll_cat_son);
        init();
        return view;
    }

    void init(){
        LogUtil.d("大类:"+getCats().getName());
        catSonAdapter = new CatSonAdapter(getActivity(),getCats().getList());
        layoutManager = new GridLayoutManager(getActivity(),3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        gvCatSon.setAdapter(new GridViewAdapter(getActivity(),getCats().getList()));
//        gvCatSon.setOverScrollMode(View.OVER_SCROLL_NEVER);

//        LinearLayout ll = null;
//        for (int i=0;i<36;i++){
//            Button btn = new Button(getActivity());
//            btn.setText(getCats().getList().get(i).getName());
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MyToast.showToast(getActivity(),((Button)v).getText().toString());
//                }
//            });
//            if (i==0 || (i+1)%3 == 0){
//                ll = new LinearLayout(getActivity());
//                ll.setOrientation(LinearLayout.HORIZONTAL);
//            }
//            ll.addView(btn);
//            linearLayout.removeView(ll);
//            linearLayout.addView(ll);
//        }
        rvCatSon.setLayoutManager(layoutManager);
        rvCatSon.setAdapter(catSonAdapter);
        rvCatSon.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(getActivity(),5)));
//        rvCatSon.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LogUtil.d("dx:"+dx+" dy:"+dy);
//                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
//                int last = layoutManager.findLastCompletelyVisibleItemPosition();
//                int first = layoutManager.findFirstCompletelyVisibleItemPosition();
//                if (first == 0){
//                    getViewPager().setFirstVis(true);
//                }else{
//                    getViewPager().setFirstVis(false);
//                }
//
////                LogUtil.d("最后一个可见项:"+last);
////                LogUtil.d("总数:" + catSonAdapter.getItemCount());
//                if (last == catSonAdapter.getItemCount()-1){
//                    LogUtil.d("是否到底:"+ (last == catSonAdapter.getItemCount()-1));
////                    rvCatSon.setDispatch(false);
//                    getViewPager().setIsRvEnd(true);
//                }else{
//                    getViewPager().setIsRvEnd(false);
//                }
//            }
//        });
//        int size = getCats().getList().size() > 36 ? 36 : getCats().getList().size();
//        for (int i = 0; i < size; i++){
//            Button btn = new Button(getActivity());
//            btn.setText(getCats().getList().get(i).getName());
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MyToast.showToast(getActivity(),((Button)v).getText().toString());
//                }
//            });
//            btn.setId(i);
//            //定义一个RelativeLayout组件
//            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            if (i % 3 == 0){
//                if (i == 0){
//                    //与父组件顶部对齐
//                    lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                }else{
//                    lp1.addRule(RelativeLayout.BELOW,layout.getChildAt(i-3).getId());
//                }
//                lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            }else{
//                lp1.addRule(RelativeLayout.RIGHT_OF,layout.getChildAt(i-1).getId());
//                lp1.addRule(RelativeLayout.ALIGN_BOTTOM,layout.getChildAt(i-1).getId());
//            }
//            layout.addView(btn,lp1);
//        }
    }

    public CookbookCats getCats(){
        return (CookbookCats) getArguments().getSerializable("cats");
    }

    private class CatSonAdapter extends RecyclerView.Adapter{
        private Context context;
        private List<CookbookCats> catsList;

        public CatSonAdapter(Context context, List<CookbookCats> catsList) {
            this.context = context;
            this.catsList = catsList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.cat_son,null);

            return new CatSonViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CatSonViewHolder catSonViewHolder = (CatSonViewHolder) holder;
            catSonViewHolder.tvCatSon.setText(catsList.get(position).getName());
            catSonViewHolder.setOnItemClick(catsList.get(position).getClassid());
        }

        @Override
        public int getItemCount() {
            return catsList.size() > 36 ? 36 : catsList.size();
        }

        class CatSonViewHolder extends RecyclerView.ViewHolder{
            private TextView tvCatSon;
            public CatSonViewHolder(View itemView) {
                super(itemView);
                tvCatSon = (TextView) itemView.findViewById(R.id.tv_cat_son_name);
            }
            public void setOnItemClick(final int classid){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyToast.showToast(context,classid+"");
//                        Intent intent = new Intent(context, FoodMenuSearchActivity_.class);
//                        intent.putExtra("classid",String.valueOf(classid));
//                        MFGT.startActivity(context,intent);
                    }
                });
            }
        }
    }
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) %3==0) {
                outRect.left = 0;
            }
        }

    }

    class GridViewAdapter extends BaseAdapter {
        private Context context=null;
        private List<CookbookCats> catsList;

        private class Holder{

            TextView item_tex;

            public TextView getItem_tex() {
                return item_tex;
            }

            public void setItem_tex(TextView item_tex) {
                this.item_tex = item_tex;
            }
        }
        //构造方法
        public GridViewAdapter(Context context, List<CookbookCats> catsList) {
            this.context = context;
            this.catsList = catsList;
        }

        @Override
        public int getCount() {
            return catsList.size() > 36 ? 36 : catsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Holder holder;
            if(view==null){
                LayoutInflater inflater = LayoutInflater.from(context);
                view=inflater.inflate(R.layout.cat_son,null);
                holder=new Holder();
                holder.item_tex=(TextView)view.findViewById(R.id.tv_cat_son_name);
                view.setTag(holder);
            }else{
                holder=(Holder) view.getTag();
            }
            holder.item_tex.setText(catsList.get(position).getName());

            return view;
        }
    }


}
