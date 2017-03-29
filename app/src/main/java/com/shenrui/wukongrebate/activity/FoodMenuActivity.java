package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_food_menu)
public class FoodMenuActivity extends Activity {
    @ViewById(R.id.food_menu_rv)
    RecyclerView rv;
    @ViewById(R.id.food_menu_iv_select)
    ImageView ivSelect;
    @ViewById(R.id.food_menu_et_search)
    EditText et;

    List<FoodContentItem> foodContentItems;
    int[] headerDatas;
    FoodAdapter adapter;
    GridLayoutManager layoutManager;
    PopupWindow pop;
    boolean isExpand = false;
    String[] category_texts;
    int[] category_images;

    @AfterViews
    void init(){
        initData();
        initView();
        setListener();
    }

    private void setListener() {
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    if (et.getText().toString().trim().isEmpty()){
                        Toast.makeText(FoodMenuActivity.this, getString(R.string.word_empty_search), Toast.LENGTH_SHORT).show();
                    }else{
                        //隐藏键盘
                        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(FoodMenuActivity.this.getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
    }

    private void initData(){
        headerDatas = new int[]{R.drawable.banner,R.drawable.banner,R.drawable.banner};

        foodContentItems = new ArrayList<>();
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"精选早餐"));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"果蔬沙拉",R.drawable.img_one));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"简易早餐包",R.drawable.img_two));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"鸡蛋糯米卷",R.drawable.img_three));

        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"省时快炒好菜"));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"老醋菠菜",R.drawable.img_four));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"锅包鱼片",R.drawable.img_five));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"农家小炒肉",R.drawable.img_six));

        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"滋补好汤"));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"红枣银耳汤",R.drawable.img_seven));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"萝卜排骨汤",R.drawable.img_eight));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"鱼汤",R.drawable.img_nine));

        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"精致小食"));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"草莓香草蛋糕",R.drawable.img_ten));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"香芋芒果塔",R.drawable.img_eleven));
        foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_MENU,"松软熙凤蛋糕",R.drawable.img_tweleve));
    }

    private void initView() {
        adapter = new FoodAdapter(this,foodContentItems);
        adapter.setHeadDatas(headerDatas);
        adapter.setHasFooter(false);
        rv.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 1 || position%4 == 1) return 3;
                return (position == adapter.getItemCount()-1) ? 3 : 1;
            }
        });
        rv.setLayoutManager(layoutManager);
    }

    @Click({R.id.food_menu_iv_back,R.id.food_menu_et_search,R.id.food_menu_iv_select})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.food_menu_iv_back:
                MFGT.finish(this);
                break;
            case R.id.food_menu_et_search:

                break;
            case R.id.food_menu_iv_select:
                if (pop==null){
                    initPop();
                }else{
                    if (isExpand){
                        pop.dismiss();
                    }else{
                        pop.showAsDropDown(ivSelect,0,0);
                    }
                }
                isExpand = !isExpand;
                break;
        }
    }

    //初始化筛选框
    private void initPop() {
        category_texts = new String[]{"时令食材","热门","口味","蔬菜","肉类","水产","主食"};
        category_images = new int[]{R.drawable.icon_shilingshicai,R.drawable.icon_remen,R.drawable.icon_kouwei,
                R.drawable.icon_shucai,R.drawable.icon_roulei,R.drawable.icon_shuichan,R.drawable.icon_zhushi};
        View layout = LayoutInflater.from(this).inflate(R.layout.menu_food_category, null);
        ListView listView = (ListView) layout.findViewById(R.id.listView_category);
        listView.setAdapter(new SelectAdapter(category_texts,category_images));
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FoodMenuActivity.this, category_texts[position], Toast.LENGTH_SHORT).show();
                pop.dismiss();
                isExpand = !isExpand;
            }
        });
        pop = new PopupWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(false);
        pop.showAsDropDown(ivSelect,0,0);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

    //筛选框适配器
    class SelectAdapter extends BaseAdapter {
        String[] strs;
        int[] images;

        SelectAdapter(String[] category_texts,int[] category_images) {
            this.strs = category_texts;
            this.images = category_images;
        }

        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public Object getItem(int position) {
            return strs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CategoryHolder holder;
            if (convertView==null){
                convertView = LayoutInflater.from(FoodMenuActivity.this).inflate(R.layout.menu_food_category_item, null);
                holder = new CategoryHolder();
                holder.ivCategory = (ImageView) convertView.findViewById(R.id.iv_category);
                holder.tvCategory = (TextView) convertView.findViewById(R.id.tv_category);
                convertView.setTag(holder);
            }else{
                holder = (CategoryHolder) convertView.getTag();
            }
            holder.ivCategory.setImageResource(images[position]);
            holder.tvCategory.setText(strs[position]);
            return convertView;
        }

        class CategoryHolder{
            ImageView ivCategory;
            TextView tvCategory;
        }
    }
}
