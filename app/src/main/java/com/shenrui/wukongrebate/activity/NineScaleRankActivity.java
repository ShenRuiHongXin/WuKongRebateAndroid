package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.Utils;
import com.shenrui.wukongrebate.view.MyGridView;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

//九块九热销榜单
@EActivity(R.layout.activity_nine_scale_rank)
public class NineScaleRankActivity extends Activity {
    @ViewById(R.id.iv_nine_scale_rank_expand)
    ImageView ivExpand;
    @ViewById(R.id.tabs_nine_scale_rank)
    TabLayout tabs;
    @ViewById(R.id.rv_nine_rank)
    RecyclerView rv;

    Context context;
    String[] titles;//榜单分类
    PopupWindow pop;//分类pop窗
    boolean isExpand = false;
    List<TbkItem> nineRankGoods;
    NineRankAdapter nineRankAdapter;

    @AfterViews
    void init(){
        context = this;
        initTab();
        initView();
        initData(titles[0]);
        setListener();
    }

    private void setListener() {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initData(titles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        nineRankGoods = new ArrayList<>();
        nineRankAdapter = new NineRankAdapter(nineRankGoods, context);
        rv.setAdapter(nineRankAdapter);
        rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view)!=0){
                    outRect.set(20,0,20,15);
                }
            }
        });
    }

    @Background
    void initData(String q){
        if (Utils.isNetworkConnected(context)){
            List<TbkItem> goodsList = GetNetWorkDatas.getNineRankGoods(q);
            updateUi(goodsList);
        }else{
            updateUi(null);
        }
    }

    @UiThread
    void updateUi(List<TbkItem> goodsList) {
        if (goodsList!=null){
            nineRankAdapter.initData(goodsList);
        }else{
            Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();
            nineRankAdapter.initData(null);
        }
    }

    private void initTab() {
        titles = new String[]{"女装","美食","男装","居家", "数码配件","儿童玩具","鞋包","床上用品","内衣内裤"};
        for(String str:titles){
            tabs.addTab(tabs.newTab().setText(str));
        }
    }

    @Click({R.id.iv_nine_scale_rank_back,R.id.iv_nine_scale_rank_refresh,R.id.iv_nine_scale_rank_expand})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_nine_scale_rank_back:
                finish();
                break;
            case R.id.iv_nine_scale_rank_refresh://刷新
                tabs.getTabAt(0).select();
                break;
            case R.id.iv_nine_scale_rank_expand:
                if(pop==null){
                    initPop();
                }else{
                    if(!isExpand){
                        pop.showAsDropDown(tabs,0,0);
                    }else{
                        pop.dismiss();
                    }
                }
                isExpand = !isExpand;
                checkExpandStatus();
                break;
        }
    }

    private void checkExpandStatus() {
        if (isExpand){
            ivExpand.setImageResource(R.drawable.common_btn_back_n);
        }else{
            ivExpand.setImageResource(R.drawable.common_btn_down_n);
        }
    }

    private void initPop() {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_nine_rank_category, null);
        MyGridView gridView = (MyGridView) layout.findViewById(R.id.gridView_nine_rank_category);
        gridView.setAdapter(new NineRankCategoryAdapter(titles,context));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tabs.getTabAt(position).select();
                pop.dismiss();
                isExpand = !isExpand;
                checkExpandStatus();
            }
        });
        pop = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(false);
        //pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAsDropDown(tabs,0,0);
    }

    //分类窗口适配器
    class NineRankCategoryAdapter extends BaseAdapter{
        String[] texts;
        Context context;

        NineRankCategoryAdapter(String[] texts, Context context) {
            this.texts = texts;
            this.context = context;
        }

        @Override
        public int getCount() {
            return texts.length;
        }

        @Override
        public Object getItem(int position) {
            return texts[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CategoryItemHolder holder;
            if(convertView == null){
                holder = new CategoryItemHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_nine_rank_category_item,null);
                holder.tvItem = (TextView) convertView.findViewById(R.id.tv_nine_rank_category_item);
                convertView.setTag(holder);
            }else{
                holder = (CategoryItemHolder) convertView.getTag();
            }
            holder.tvItem.setText(texts[position]);
            return convertView;
        }

        class CategoryItemHolder{
            TextView tvItem;
        }
    }

    //榜单排名适配器
    class NineRankAdapter extends RecyclerView.Adapter{
        private static final int TYPE_TITLE = 0;
        private static final int TYPE_GOODS = 1;

        List<TbkItem> list;
        Context context;
        View.OnClickListener listener;
        NineRankAdapter(List<TbkItem> list, final Context context) {
            this.list = list;
            this.context = context;
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cid = (String) v.getTag();
                    //进入商品详情页
                    Intent intent = new Intent(context, AliSdkWebViewProxyActivity_.class);
                    intent.putExtra("num_iid",cid);
                    context.startActivity(intent);
                }
            };
        }

        public void initData(List<TbkItem> nineGoods){
            if (list.size()!=0){
                list.clear();
            }
            if (nineGoods!=null){
                list.addAll(nineGoods);
            }
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            RecyclerView.ViewHolder holder;
            View layout;
            if (viewType == TYPE_TITLE){
                layout = inflater.inflate(R.layout.layout_nine_rank_one, null);
                holder = new TitleHolder(layout);
            }else{
                layout = inflater.inflate(R.layout.layout_nine_rank_two,null);
                holder = new GoodsHolder(layout);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == TYPE_TITLE){
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.tvNineRankTitle.setText(titles[tabs.getSelectedTabPosition()]+"榜单");
            }else {
                GoodsHolder goodsHolder = (GoodsHolder) holder;
                TbkItem tbkItem = list.get(position-1);
                Glide.with(context).load(tbkItem.getPict_url()).into(goodsHolder.ivGoods);
                goodsHolder.tvGoodsTitle.setText(tbkItem.getTitle());
                goodsHolder.tvGoodsPrice.setText(tbkItem.getZk_final_price());
                goodsHolder.tvGoodsVolume.setText("热卖"+tbkItem.getVolume()+"件");
                if (position == 1){
                    goodsHolder.tvGoodsRank.setVisibility(View.VISIBLE);
                    goodsHolder.tvGoodsRank.setText("1");
                    goodsHolder.tvGoodsRank.setBackgroundResource(R.drawable.circle_number_one);
                } else if (position == 2){
                    goodsHolder.tvGoodsRank.setVisibility(View.VISIBLE);
                    goodsHolder.tvGoodsRank.setText("2");
                    goodsHolder.tvGoodsRank.setBackgroundResource(R.drawable.circle_number_two);
                } else if (position == 3){
                    goodsHolder.tvGoodsRank.setVisibility(View.VISIBLE);
                    goodsHolder.tvGoodsRank.setText("3");
                    goodsHolder.tvGoodsRank.setBackgroundResource(R.drawable.circle_number_three);
                } else{
                    goodsHolder.tvGoodsRank.setVisibility(View.GONE);
                }
                goodsHolder.layoutNineRankGoods.setTag(String.valueOf(tbkItem.getNum_iid()));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0){
                return TYPE_TITLE;
            }else{
                return TYPE_GOODS;
            }
        }

        @Override
        public int getItemCount() {
            return list.size()==0 ? 0:list.size()+1;
        }

        class TitleHolder extends RecyclerView.ViewHolder{
            TextView tvNineRankTitle;
            public TitleHolder(View itemView) {
                super(itemView);
                tvNineRankTitle = (TextView) itemView.findViewById(R.id.tv_nine_rank_title);
            }
        }

        class GoodsHolder extends RecyclerView.ViewHolder{
            ImageView ivGoods;
            TextView tvGoodsRank,tvGoodsTitle,tvGoodsPrice,tvGoodsVolume;
            LinearLayout layoutNineRankGoods;
            public GoodsHolder(View itemView) {
                super(itemView);
                layoutNineRankGoods = (LinearLayout) itemView.findViewById(R.id.layout_nine_rank_goods);
                ivGoods= (ImageView) itemView.findViewById(R.id.iv_nine_rank_goods);
                tvGoodsRank = (TextView) itemView.findViewById(R.id.tv_nine_goods_rank);
                tvGoodsTitle = (TextView) itemView.findViewById(R.id.tv_nine_rank_goods_title);
                tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_nine_rank_goods_price);
                tvGoodsVolume = (TextView) itemView.findViewById(R.id.tv_nine_rank_goods_volume);
                layoutNineRankGoods.setOnClickListener(listener);
            }
        }
    }
}
