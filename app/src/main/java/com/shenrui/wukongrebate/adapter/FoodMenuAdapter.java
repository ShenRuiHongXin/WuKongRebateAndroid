package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.MenuFoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜谱适配器
 */

public class FoodMenuAdapter extends RecyclerView.Adapter{
    Context context;
    private List<String> categoryList;
    private List<List<MenuFoodItem>> foodsList;

    public FoodMenuAdapter(Context context, List<String> titleList, List<List<MenuFoodItem>> foods) {
        categoryList = new ArrayList<>();
        categoryList.addAll(titleList);
        foodsList = new ArrayList<>();
        foodsList.addAll(foods);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.menu_food_item, null);
        return new MenuFoodHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuFoodHolder foodHolder = (MenuFoodHolder) holder;
        foodHolder.tvCategory.setText(categoryList.get(position));
        List<MenuFoodItem> foodItems = foodsList.get(position);
//        foodHolder.ivFood1.setImageResource(foodItems.get(0).getImageId());
        Glide.with(context)
                .load("http://api.jisuapi.com/recipe/upload/20160720/113938_92715.jpg")
                .into(foodHolder.ivFood1);
        foodHolder.ivFood2.setImageResource(foodItems.get(1).getImageId());
        foodHolder.ivFood3.setImageResource(foodItems.get(2).getImageId());
        foodHolder.tvFoodTitle1.setText(foodItems.get(0).getTitle());
        foodHolder.tvFoodTitle2.setText(foodItems.get(1).getTitle());
        foodHolder.tvFoodTitle3.setText(foodItems.get(2).getTitle());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void initData(List<String> categorys,List<List<MenuFoodItem>> foods){

        if (categorys!=null && foods!=null){
            categoryList.addAll(categorys);
            foodsList.addAll(foods);
            notifyDataSetChanged();
        }
    }

    private class MenuFoodHolder extends RecyclerView.ViewHolder{
        TextView tvCategory,tvFoodTitle1,tvFoodTitle2,tvFoodTitle3;
        ImageView ivFood1,ivFood2,ivFood3;
        LinearLayout layoutFood;
        MenuFoodHolder(View itemView) {
            super(itemView);
            layoutFood = (LinearLayout) itemView.findViewById(R.id.layout_menu_food);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_menu_food_category);
            tvFoodTitle1 = (TextView) itemView.findViewById(R.id.tv_menu_food_title1);
            tvFoodTitle2 = (TextView) itemView.findViewById(R.id.tv_menu_food_title2);
            tvFoodTitle3 = (TextView) itemView.findViewById(R.id.tv_menu_food_title3);
            ivFood1 = (ImageView) itemView.findViewById(R.id.iv_menu_food1);
            ivFood2 = (ImageView) itemView.findViewById(R.id.iv_menu_food2);
            ivFood3 = (ImageView) itemView.findViewById(R.id.iv_menu_food3);
        }
    }
}
