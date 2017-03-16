package com.shenrui.wukongrebate.fragment.supers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.BrandActivity_;
import com.shenrui.wukongrebate.activity.SuperSearchActivity_;
import com.shenrui.wukongrebate.activity.SuperSearchResultActivity_;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.UatmTbkItem;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.MyGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EFragment(R.layout.fragment_super_item)
public class FragmentSuperItem extends Fragment {
    @ViewById(R.id.srl_super_item)
    SwipeRefreshLayout srl;
    @ViewById(R.id.rv_super_item)
    RecyclerView rv;

    String title;
    Context context;
    SuperItemAdapter adapter;
    LinearLayoutManager layoutManager;
    List<List<UatmTbkItem>> list;
    int[] favorites = null;

    int[] images_category;
    String[] texts_category;

    @AfterViews
    void init(){
        title = getArguments().getString("title");
        context = getContext();
        initData();
        initView();
        srl.setRefreshing(true);
        downloadBrands();
        setListener();
    }

    private void downloadBrands() {
        switch (title){
            case "女装":
                favorites = new int[]{3457856,3457752};
                break;
            case "男装":
                break;
            case "食品":
                break;
            case "美妆":
                break;
            case "居家":
                break;
            case "内衣":
                break;
            case "运动":
                break;
        }
        download(favorites);
    }

    @Background
    void download(int[] favorites){
        if (favorites!=null){
            //下载每个品牌的两个商品
            for(int i=0;i<favorites.length;i++){
                Map<String, Object> map = GetNetWorkDatas.getFavoritesGoods(favorites[i], 1, 2);
                List<UatmTbkItem> goodsList = (List<UatmTbkItem>) map.get(Constants.GOODS);
                list.add(goodsList);
            }
            updateUi(list);
        }
    }

    @UiThread
    void updateUi(List<List<UatmTbkItem>> list){
        adapter.initData(list);
        srl.setRefreshing(false);
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
            }
        });
    }

    private void initData(){
        switch (title){
            case "女装":
                texts_category = new String[]{"温暖冬装","外套","针织衫","打底衫","套装","裙装","裤子","鞋子"};
                images_category = new int[]{R.drawable.home_img_dayi,R.drawable.home_img_waitao,R.drawable.home_img_zhenzhishan,R.drawable.home_img_dadishan,
                        R.drawable.home_img_taozhuang,R.drawable.home_img_qunzhuang,R.drawable.home_img_kuzhuang,R.drawable.home_img_xiezi,};
                break;
            case "男装":
                texts_category = new String[]{"春装热卖","男士上装","卫衣T恤","衬衫","夹克","牛仔裤","休闲裤","男士套装"};
                images_category = new int[]{R.drawable.home_img_chunzhuangremai,R.drawable.home_img_nanshishangzhuang,R.drawable.home_img_weiyitxu,R.drawable.home_img_chenshan,
                        R.drawable.home_img_jiake,R.drawable.home_img_niuzaiku,R.drawable.home_img_xiuxainku,R.drawable.home_img_nanshitaozhuang,};
                break;
            case "食品":
                texts_category = new String[]{"休闲食品","瓜果干脯","营养生鲜","饮料冲调","粮油干货","茶叶","坚果","饼干糕点"};
                images_category = new int[]{R.drawable.home_img_xiuxianshipin,R.drawable.home_img_guaguoganpu,R.drawable.home_img_yingyangshengxain,R.drawable.home_img_yinliaochongtiao,
                        R.drawable.home_img_liangyouganhuo,R.drawable.home_img_chaye,R.drawable.home_img_jianguo,R.drawable.home_img_binggangaodian,};
                break;
            case "美妆":
                texts_category = new String[]{"基础护肤","口碑面膜","面部清洁","个人洗护","美容仪器","套装礼盒","彩妆工具","精品配饰"};
                images_category = new int[]{R.drawable.home_img_jichuhufu,R.drawable.home_img_koubeimianmo,R.drawable.home_img_mianbuqingjie,R.drawable.home_img_gerenxihu,
                        R.drawable.home_img_meirongyiqi,R.drawable.home_img_taozhaunglihe,R.drawable.home_img_caizhuanggongju,R.drawable.home_img_jingpinpeishi,};
                break;
            case "居家":
                texts_category = new String[]{"茶具水具","整理收纳","杂日百货","纸品护理","建材卫浴","大家具","灯具灯饰","车品装饰"};
                images_category = new int[]{R.drawable.home_img_cahjushuiju,R.drawable.home_img_zhenglishouna,R.drawable.home_img_baizarihuo,R.drawable.home_img_zhipinhuli,
                        R.drawable.home_img_jiancaiweiju,R.drawable.home_img_dajiaju,R.drawable.home_img_dengjudengshi,R.drawable.home_img_cehpindengshi,};
                break;
            case "内衣":
                texts_category = new String[]{"性感文胸","居家睡衣","美体塑身","丝袜打底","女士内裤","女士袜子","男士内裤","男士袜子"};
                images_category = new int[]{R.drawable.home_img_xingganwenxiong,R.drawable.home_img_jujiashuiyi,R.drawable.home_img_meitisushen,R.drawable.home_img_siwadadi,
                        R.drawable.home_img_nvshineiku,R.drawable.home_img_nvshiwazi,R.drawable.home_img_nanshineiku,R.drawable.home_img_nanshiwazi,};
                break;
            case "运动":
                texts_category = new String[]{"跑步鞋","运动服饰","户外鞋服","健身系列","户外装备","体育用品","钓鱼装备","游泳装备"};
                images_category = new int[]{R.drawable.home_img_paobuxie,R.drawable.home_img_yundongfushi,R.drawable.home_img_huwaixiefu,R.drawable.home_img_jianshenxilie,
                        R.drawable.home_img_huwaizhuangbei,R.drawable.home_img_tiyuyongpin,R.drawable.home_img_diaoyuzhuangbei,R.drawable.home_img_youyongzhuangbei,};
                break;
        }

    }

    private void initView() {
        srl.setColorSchemeColors(getResources().getColor(R.color.mainRed));
        list = new ArrayList<>();
        adapter = new SuperItemAdapter(list);
        rv.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
    }


    class SuperItemAdapter extends RecyclerView.Adapter{
        private static final int TYPE_ONE = 0;//分类栏
        private static final int TYPE_TWO = 1;//品牌及其部分商品
        CategoryAdapter categoryAdapter;
        View.OnClickListener listener;
        List<List<UatmTbkItem>> list;

        SuperItemAdapter(List<List<UatmTbkItem>> goodsList) {
            list = new ArrayList<>();
            list.addAll(goodsList);
            categoryAdapter = new CategoryAdapter(images_category, texts_category);
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Intent intent = new Intent(context, BrandActivity_.class);
                    intent.putExtra(Constants.FAVORITES_ID,favorites[position]);
                    MFGT.startActivity(context,intent);
                }
            };
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout;
            RecyclerView.ViewHolder holder = null;
            if (viewType == TYPE_ONE) {
                layout = inflater.inflate(R.layout.layout_selected_item_one,null);
                holder = new CategoryViewHolder(layout);
            } else {
                layout = inflater.inflate(R.layout.layout_super_brand_goods,null);
                holder = new GoodsViewHolder(layout);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if (viewType == TYPE_ONE){
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
                categoryViewHolder.myGridView.setAdapter(categoryAdapter);
            }else{
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
                List<UatmTbkItem> uatmTbkItemList = list.get(position - 1);
                Glide.with(context).load(uatmTbkItemList.get(0).getPict_url()).into(goodsViewHolder.ivGood1);
                goodsViewHolder.tvGood1Title.setText(uatmTbkItemList.get(0).getTitle());
                goodsViewHolder.tvGood1Price.setText(uatmTbkItemList.get(0).getZk_final_price());
                goodsViewHolder.tvGood1Rate.setText(uatmTbkItemList.get(0).getTk_rate());
                goodsViewHolder.tvGood1Volume.setText(String.valueOf(uatmTbkItemList.get(0).getVolume()));
                Glide.with(context).load(uatmTbkItemList.get(1).getPict_url()).into(goodsViewHolder.ivGood2);
                goodsViewHolder.tvGood2Title.setText(uatmTbkItemList.get(1).getTitle());
                goodsViewHolder.tvGood2Price.setText(uatmTbkItemList.get(1).getZk_final_price());
                goodsViewHolder.tvGood2Rate.setText(uatmTbkItemList.get(1).getTk_rate());
                goodsViewHolder.tvGood2Volume.setText(String.valueOf(uatmTbkItemList.get(1).getVolume()));
                goodsViewHolder.layoutBrand.setTag(position-1);
            }
        }

        @Override
        public int getItemCount() {
            return list.size()==0?1:1+list.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position==0){
                return TYPE_ONE;
            }else{
                return TYPE_TWO;
            }
        }

        public void initData(List<List<UatmTbkItem>> brands) {
            if (list.size()!=0){
                list.clear();
            }
            if (brands!=null){
                list.addAll(brands);
                notifyDataSetChanged();
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
            LinearLayout layoutBrand;
            ImageView ivBrand,ivGood1,ivGood2;
            TextView tvGood1Title,tvGood1Price,tvGood1Volume,tvGood1Rate,tvGood2Title,tvGood2Price,tvGood2Volume,tvGood2Rate;
            public GoodsViewHolder(View itemView) {
                super(itemView);
                layoutBrand = (LinearLayout) itemView.findViewById(R.id.layout_brand);
                ivBrand = (ImageView) itemView.findViewById(R.id.iv_brand);
                ivGood1 = (ImageView) itemView.findViewById(R.id.iv_brand_goods1);
                ivGood2 = (ImageView) itemView.findViewById(R.id.iv_brand_goods2);
                tvGood1Title = (TextView) itemView.findViewById(R.id.tv_brand_goods1_title);
                tvGood1Price = (TextView) itemView.findViewById(R.id.tv_brand_goods1_price);
                tvGood1Volume = (TextView) itemView.findViewById(R.id.tv_brand_goods1_volume);
                tvGood1Rate = (TextView) itemView.findViewById(R.id.tv_brand_goods1_rate);
                tvGood2Title = (TextView) itemView.findViewById(R.id.tv_brand_goods2_title);
                tvGood2Price = (TextView) itemView.findViewById(R.id.tv_brand_goods2_price);
                tvGood2Volume = (TextView) itemView.findViewById(R.id.tv_brand_goods2_volume);
                tvGood2Rate = (TextView) itemView.findViewById(R.id.tv_brand_goods2_rate);
                layoutBrand.setOnClickListener(listener);
            }
        }
    }

    //分类栏适配器
    class CategoryAdapter extends BaseAdapter {
        int[] images_category;
        String[] texts_category;
        View.OnClickListener listener;

        CategoryAdapter(int[] images_category, final String[] texts_category) {
            this.images_category = images_category;
            this.texts_category = texts_category;
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag(R.id.id_category);
                    Intent intent = new Intent(context, SuperSearchResultActivity_.class);
                    intent.putExtra("super_search_goods",title + texts_category[position]);
                    MFGT.startActivity(context,intent);
                }
            };
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
                holder.layoutCategory = (LinearLayout) convertView.findViewById(R.id.layout_category);
                convertView.setTag(holder);
            }else{
                holder = (CategoryHolder) convertView.getTag();
            }
            holder.ivCategory.setImageResource(images_category[position]);
            holder.tvCategory.setText(texts_category[position]);
            holder.layoutCategory.setTag(R.id.id_category,position);
            holder.layoutCategory.setOnClickListener(listener);
            return convertView;
        }
        class CategoryHolder{
            ImageView ivCategory;
            TextView tvCategory;
            LinearLayout layoutCategory;
        }
    }
}
