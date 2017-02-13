package com.shenrui.wukongrebate.fragment.haitao;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.view.CycleRotationView;
import com.shenrui.wukongrebate.view.MyGridView;

public class FragmentSelected extends Fragment {
    RecyclerView rv;
    Context context;
    String title;

    public FragmentSelected() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected, container, false);
        initView(view);
        initData();
        rv.setAdapter(new SelectedAdapter());
        rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0,5,0,5);
            }
        });
        return view;
    }

    private void initView(View view) {
        context = getContext();
        title = getArguments().getString("title", null);
        rv = (RecyclerView) view.findViewById(R.id.rv_selected);
    }

    int[] images_category,images_brand;
    int[][] images_goods;
    String[] texts_category;
    private void initData(){
        switch (title){
            case "母婴":
                images_category = new int[]{R.drawable.oversea_baby_iconimg1,R.drawable.oversea_baby_iconimg2,
                                            R.drawable.oversea_baby_iconimg3,R.drawable.oversea_baby_iconimg4,
                                            R.drawable.oversea_baby_iconimg5,R.drawable.oversea_baby_iconimg6,
                                            R.drawable.oversea_baby_iconimg7,R.drawable.oversea_baby_iconimg8};
                texts_category = new String[]{"奶粉","纸尿裤","营养辅食","洗护喂养",
                                            "孕妈专区","玩具图书","家居出行","童装童鞋"};
                break;
            case "美妆":
            case "箱包":
            case "美食保健":
            case "饰品":
            case "家电家居":
            case "直邮数码":
                images_category = new int[]{R.drawable.oversea_makeup_iconimg1,R.drawable.oversea_makeup_iconimg2,
                        R.drawable.oversea_makeup_iconimg3,R.drawable.oversea_makeup_iconimg4,
                        R.drawable.oversea_makeup_iconimg5,R.drawable.oversea_makeup_iconimg6,
                        R.drawable.oversea_makeup_iconimg7,R.drawable.oversea_makeup_iconimg8};
                texts_category = new String[]{"基础护肤","明星面膜","面部清洁","秋冬护理",
                        "美容工具","礼盒套装","心机彩妆","迷人香氛"};
                break;
        }

        images_brand = new int[]{R.drawable.oversea_img1,R.drawable.oversea_img5};
        images_goods = new int[][]{
                {R.drawable.oversea_img6,R.drawable.oversea_img7,R.drawable.oversea_img8},
                {R.drawable.oversea_img2,R.drawable.oversea_img3,R.drawable.oversea_img4}
        };
    }

    class SelectedAdapter extends RecyclerView.Adapter{
        private static final int TYPE_ONE = 0;//分类栏
        private static final int TYPE_TWO = 1;//品牌及其部分商品

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout;
            RecyclerView.ViewHolder holder = null;
            if (viewType == TYPE_ONE) {
                layout = inflater.inflate(R.layout.layout_selected_item_one,null);
                holder = new CategoryViewHolder(layout);
            } else {
                layout = inflater.inflate(R.layout.layout_selected_item_two,null);
                holder = new GoodsViewHolder(layout);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == TYPE_ONE){
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
                categoryViewHolder.myGridView.setAdapter(new CategoryAdapter(images_category,texts_category));
            }else{
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
                goodsViewHolder.ivBrand.setImageResource(images_brand[position-1]);
                goodsViewHolder.rvBrandGoods.setAdapter(new BrandGoodsAdapter(images_goods[position-1]));
                goodsViewHolder.rvBrandGoods.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                goodsViewHolder.rvBrandGoods.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        outRect.set(5,5,5,0);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            if (position==0){
                return TYPE_ONE;
            }else{
                return TYPE_TWO;
            }
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder{
            MyGridView myGridView;
            public CategoryViewHolder(View itemView) {
                super(itemView);
                myGridView = (MyGridView) itemView.findViewById(R.id.gridView_selected);
            }
        }
        class GoodsViewHolder extends RecyclerView.ViewHolder{
            ImageView ivBrand;
            RecyclerView rvBrandGoods;
            public GoodsViewHolder(View itemView) {
                super(itemView);
                ivBrand = (ImageView) itemView.findViewById(R.id.iv_selected_brand);
                rvBrandGoods = (RecyclerView) itemView.findViewById(R.id.rv_selected_goods);
            }
        }
    }

    //分类栏适配器
    class CategoryAdapter extends BaseAdapter{
        int[] images_category;
        String[] texts_category;

        public CategoryAdapter(int[] images_category, String[] texts_category) {
            this.images_category = images_category;
            this.texts_category = texts_category;
        }

        @Override
        public int getCount() {
            return images_category.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CategoryHolder holder;
            if(convertView == null){
                holder = new CategoryHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_selected_category_item,null);
                holder.ivCategory = (ImageView) convertView.findViewById(R.id.iv_category);
                holder.tvCategory = (TextView) convertView.findViewById(R.id.tv_category);
                convertView.setTag(holder);
            }else{
                holder = (CategoryHolder) convertView.getTag();
            }
            holder.ivCategory.setImageResource(images_category[position]);
            holder.tvCategory.setText(texts_category[position]);
            return convertView;
        }
        class CategoryHolder{
            ImageView ivCategory;
            TextView tvCategory;
        }
    }

    //展示水平品牌部分商品的适配器
    class BrandGoodsAdapter extends RecyclerView.Adapter<BrandGoodsAdapter.BrandGoodViewHolder>{
        int[] images;

        public BrandGoodsAdapter(int[] images_goods) {
            this.images = images_goods;
        }

        @Override
        public BrandGoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(context).inflate(R.layout.layout_recommend_activity_item, null);
            return new BrandGoodViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(BrandGoodViewHolder holder, int position) {
            holder.ivGood.setImageResource(images[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        class BrandGoodViewHolder extends RecyclerView.ViewHolder{
            ImageView ivGood;
            TextView tvGoodTitle,tvGoodPrice;
            public BrandGoodViewHolder(View itemView) {
                super(itemView);
                ivGood = (ImageView) itemView.findViewById(R.id.iv_activity_recommend);
                tvGoodTitle = (TextView) itemView.findViewById(R.id.tv_activity_prof);
                tvGoodPrice = (TextView) itemView.findViewById(R.id.tv_activity_theme);
            }
        }
    }
}
