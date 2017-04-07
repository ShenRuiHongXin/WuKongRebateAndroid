package com.shenrui.wukongrebate.fragment.foodmenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.FoodMenuSearchActivity_;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.CookbookCatRespBox;
import com.shenrui.wukongrebate.entities.CookbookCats;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.MyToast;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.ScreenUtils;
import com.shenrui.wukongrebate.view.TopLayoutManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/3/30.
 */

@EFragment(R.layout.fragment_cookbook_cats)
public class FragmentCookbookCats extends Fragment {
    //左侧大类  RecycleView
    @ViewById(R.id.rv_cookbook_cats_father)
    RecyclerView rvCatsFather;
    //右侧小类  RecycleView
    @ViewById(R.id.rv_cookbook_cats_son)
    RecyclerView rvCatsSon;
    //头部黏性类目 TextView
    @ViewById(R.id.tv_header_name)
    TextView tvHeadName;

    private Context context;
    //大类
    private List<CookbookCats> catFathers;
    private LinearLayoutManager layoutManager;
    private CookbookCatAdapter adapterFather;
    //小类
    private List<CookbookCats> catSons;
    private TopLayoutManager gridLayoutManager;
    private CookbookCatAdapter adapterSon;
    //黏性类目的位置
    private List<Integer> headerPosi = new ArrayList<>();

    @AfterViews
    void init(){
        this.context = getActivity();
        initData();
    }

    void initData(){
        catFathers = new ArrayList<>();
        catFathers.add(new CookbookCats(561,R.drawable.cat_icon_changjing,R.drawable.cat_select_icon_changjing,"场景"));
        catFathers.add(new CookbookCats(223,R.drawable.cat_icon_caixi,R.drawable.cat_select_icon_caixi,"菜系"));
        catFathers.add(new CookbookCats(301,R.drawable.cat_icon_caipin,R.drawable.cat_select_icon_caipin,"菜品"));
        catFathers.add(new CookbookCats(390,R.drawable.cat_icon_kouwei,R.drawable.cat_select_icon_kouwei,"口味"));
        catFathers.add(new CookbookCats(269,R.drawable.cat_icon_xiaochi,R.drawable.cat_select_icon_xiaochi,"小吃"));
        catFathers.add(new CookbookCats(1,R.drawable.cat_icon_gongxiao,R.drawable.cat_select_icon_gongxiao,"功效"));
        catFathers.add(new CookbookCats(113,R.drawable.cat_icon_renqun,R.drawable.cat_select_icon_renqun,"人群"));
        catFathers.add(new CookbookCats(213,R.drawable.cat_icon_tizhi,R.drawable.cat_select_icon_tizhi,"体质"));
        catFathers.add(new CookbookCats(453,R.drawable.cat_icon_jiagonggongyi,R.drawable.cat_select_icon_jiagonggongyi,"加工工艺"));
        catFathers.add(new CookbookCats(524,R.drawable.cat_icon_chufangyongju,R.drawable.cat_select_icon_chufangyongju,"厨房用具"));

        //获取所有分类
        NetDao.getCookbookAllCats(context, new OkHttpUtils.OnCompleteListener<CookbookCatRespBox>() {
            @Override
            public void onSuccess(CookbookCatRespBox result) {
                LogUtil.d("cats num:"+result.getRespData().getResult().get(0).getName());
                catSons = new ArrayList<CookbookCats>();
                for (int i=0; i<catFathers.size(); i++){
                    CookbookCats cF = catFathers.get(i);
                    for (int j=0; j<result.getRespData().getResult().size(); j++){
                        CookbookCats cFT = result.getRespData().getResult().get(j);
                        if (cFT.getClassid() == cF.getClassid()){
                            catSons.addAll(cFT.getList());
                            if (i < catFathers.size()-1){
                                catSons.add(new CookbookCats(catFathers.get(i+1).getName()));
                                headerPosi.add(catSons.size()-1);
                            }
                        }
                    }
                }
                initView();
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    void initView(){
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapterFather = new CookbookCatAdapter(1);
        rvCatsFather.setLayoutManager(layoutManager);
        rvCatsFather.setAdapter(adapterFather);

        adapterSon = new CookbookCatAdapter(2);
        gridLayoutManager = new TopLayoutManager(context,3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return headerPosi.contains(position) ? 3 : 1;
            }
        });
        rvCatsSon.setLayoutManager(gridLayoutManager);
        rvCatsSon.setAdapter(adapterSon);
        rvCatsSon.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(getActivity(),5)));
        rvCatsSon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //根据当前显示的小类显示响应的黏性头部
                int first = gridLayoutManager.findFirstVisibleItemPosition();
                for (int i = 0; i<headerPosi.size(); i++){
                    if (first < headerPosi.get(i)){
                        adapterFather.getCatFatherViewHolder().setSelectItem(i);
                        tvHeadName.setText(catFathers.get(i).getName());
                        return;
                    }else{
                        adapterFather.getCatFatherViewHolder().setSelectItem(i+1);
                        tvHeadName.setText(catFathers.get(catFathers.size()-1).getName());
                    }
                }
            }
        });
        rvCatsSon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int first = gridLayoutManager.findFirstVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_SETTLING){
                }
                //滑动停止时，移动当前位置到黏性头部
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int expectPos = adapterFather.getCurSelectPos() == 0 ? 0 : headerPosi.get(adapterFather.getCurSelectPos()-1);
                    if (first == expectPos){
                        rvCatsSon.smoothScrollToPosition(expectPos+1);
                    }
                }
            }
        });
    }

    private class CookbookCatAdapter extends RecyclerView.Adapter{
        public static final int TYPE_FATHER = 1;
        public static final int TYPE_SON    = 2;
        private int dataType = -1;
        private int curSelectPos = 0;
        private CatFatherViewHolder catFatherViewHolder = null;

        public CatFatherViewHolder getCatFatherViewHolder() {
            return catFatherViewHolder;
        }

        public CookbookCatAdapter(int dataType) {
            this.dataType = dataType;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view;
            RecyclerView.ViewHolder holder = null;
            switch (dataType){
                case TYPE_FATHER:
                    view = inflater.inflate(R.layout.cookbook_cats_father,parent,false);
                    holder = new CatFatherViewHolder(view);
                    this.catFatherViewHolder = (CatFatherViewHolder) holder;
                    break;
                case TYPE_SON:
                    view = inflater.inflate(R.layout.cat_son,null);
                    holder = new CatSonViewHolder(view);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (dataType){
                case TYPE_FATHER:
                    CatFatherViewHolder catFatherViewHolder = (CatFatherViewHolder) holder;
                    if (position == 0){
                        catFatherViewHolder.ivCatFatherIcon.setImageResource(catFathers.get(position).getIcon_select());
                        catFatherViewHolder.tvCatFatherName.setTextColor(context.getResources().getColor(R.color.f54949));
                    }else{
                        catFatherViewHolder.ivCatFatherIcon.setImageResource(catFathers.get(position).getIcon());
                        catFatherViewHolder.tvCatFatherName.setTextColor(context.getResources().getColor(R.color.textcolor_primary));
                    }
                    catFatherViewHolder.tvCatFatherName.setText(catFathers.get(position).getName());
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) catFatherViewHolder.ivCatFatherIcon.getLayoutParams();
                    params.width = 45;
                    params.height = 45;
                    catFatherViewHolder.ivCatFatherIcon.setLayoutParams(params);
                    catFatherViewHolder.setOnItemClick(position);
                    break;
                case TYPE_SON:
                    CatSonViewHolder catSonViewHolder = (CatSonViewHolder) holder;
                    catSonViewHolder.tvCatSonName.setText(catSons.get(position).getName());
                    if (headerPosi.contains(position)){
                        catSonViewHolder.tvCatSonName.setBackgroundColor(context.getResources().getColor(R.color.cats_bg));
                        catSonViewHolder.tvCatSonName.setTextColor(context.getResources().getColor(R.color.white));
                    }else{
                        catSonViewHolder.tvCatSonName.setBackground(context.getResources().getDrawable(R.drawable.red_edit));
                        catSonViewHolder.tvCatSonName.setTextColor(context.getResources().getColor(R.color.textcolor_primary));
                        catSonViewHolder.setOnItemClick(catSons.get(position));
                    }
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return dataType == TYPE_FATHER ? catFathers.size(): catSons.size();
        }
        public int getCurSelectPos() {
            return curSelectPos;
        }

        class CatFatherViewHolder extends RecyclerView.ViewHolder{
            private ImageView ivCatFatherIcon;
            private TextView tvCatFatherName;

            public CatFatherViewHolder(View itemView) {
                super(itemView);
                ivCatFatherIcon = (ImageView) itemView.findViewById(R.id.iv_cookbook_cat_father_icon);
                tvCatFatherName = (TextView) itemView.findViewById(R.id.tv_cookbook_cat_father_name);
            }
            public void setOnItemClick(final int position){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        tvCatsSon.setText(catFathers.get(position).getName());
                        setSelectItem(position);
                        //2017年4月6日18:01:29 到此
                        rvCatsSon.smoothScrollToPosition(position == 0 ? 0 : (headerPosi.get(position-1)+1));
//                        vvpCatSon.setCurrentItem(position);
                    }
                });
            }
            public void setSelectItem(int position){
                if(curSelectPos != position){
                    View preView = rvCatsFather.getChildAt(curSelectPos);
                    if (null != rvCatsFather.getChildViewHolder(preView)){
                        CatFatherViewHolder catFatherViewHolder = (CatFatherViewHolder) rvCatsFather.getChildViewHolder(preView);
                        catFatherViewHolder.tvCatFatherName.setTextColor(context.getResources().getColor(R.color.textcolor_primary));
                        catFatherViewHolder.ivCatFatherIcon.setImageResource(catFathers.get(curSelectPos).getIcon());
                    }
                }

                View view = rvCatsFather.getChildAt(position);
                if (null != rvCatsFather.getChildViewHolder(view)){
                    curSelectPos = position;
                    CatFatherViewHolder catFatherViewHolder = (CatFatherViewHolder) rvCatsFather.getChildViewHolder(view);
                    catFatherViewHolder.tvCatFatherName.setTextColor(context.getResources().getColor(R.color.f54949));
                    catFatherViewHolder.ivCatFatherIcon.setImageResource(catFathers.get(curSelectPos).getIcon_select());
                }
            }
        }
        class CatSonViewHolder extends RecyclerView.ViewHolder{
            private TextView tvCatSonName;

            public CatSonViewHolder(View itemView) {
                super(itemView);
                tvCatSonName = (TextView) itemView.findViewById(R.id.tv_cat_son_name);
            }
            public void setOnItemClick(final CookbookCats cats){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyToast.showToast(context,cats.getName());
                        Intent intent = new Intent(context, FoodMenuSearchActivity_.class);
                        intent.putExtra("classid",String.valueOf(cats.getClassid()));
                        MFGT.startActivity(context,intent);
                    }
                });
            }
        }
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.top = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) < 3){
                outRect.top = space;
            }
//            if (parent.getChildLayoutPosition(view) %3==0) {
//                outRect.left = 0;
//            }
            if (headerPosi.contains(parent.getChildLayoutPosition(view))){
//                outRect.left = 0;
            }
        }

    }
}
