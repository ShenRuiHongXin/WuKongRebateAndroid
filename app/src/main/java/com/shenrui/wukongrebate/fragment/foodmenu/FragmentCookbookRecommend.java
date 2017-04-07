package com.shenrui.wukongrebate.fragment.foodmenu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.FoodAdapter;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.CookBookBean;
import com.shenrui.wukongrebate.entities.CookbookRecomResp;
import com.shenrui.wukongrebate.entities.FoodContentItem;
import com.shenrui.wukongrebate.utils.OkHttpUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/3/30.
 */

@EFragment(R.layout.fragment_cookbook_recommend)
public class FragmentCookbookRecommend extends Fragment {
    @ViewById(R.id.food_menu_rv)
    RecyclerView rv;
    @ViewById(R.id.pb_cookbook_recommend)
    ProgressBar pbRecommend;

    List<FoodContentItem> foodContentItems;
    int[] headerDatas;
    FoodAdapter adapter;
//    GridLayoutManager layoutManager;
    LinearLayoutManager layoutManager;
    private Context context;

    @AfterViews
    void init(){
        this.context = getActivity();
        rv.setVisibility(View.INVISIBLE);
        pbRecommend.setVisibility(View.VISIBLE);
        initData();
    }

    private void initData(){
        headerDatas = new int[]{R.drawable.banner,R.drawable.banner,R.drawable.banner};
        foodContentItems = new ArrayList<>();

        NetDao.getCookbookRecommend(context,new OkHttpUtils.OnCompleteListener<CookbookRecomResp>() {
            @Override
            public void onSuccess(CookbookRecomResp result) {
                foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"精选早餐"));
                for (int i=0; i<result.getBreakfast().getResult().getNum(); i++){
                    CookBookBean cookBookBean = result.getBreakfast().getResult().getList().get(i);
                    foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,cookBookBean));
                }
                foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"省时快炒好菜"));
                for (int i=0; i<result.getFriedDishes().getResult().getNum(); i++){
                    CookBookBean cookBookBean = result.getFriedDishes().getResult().getList().get(i);
                    foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,cookBookBean));
                }
                foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"滋补好汤"));
                for (int i=0; i<result.getSoup().getResult().getNum(); i++){
                    CookBookBean cookBookBean = result.getSoup().getResult().getList().get(i);
                    foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,cookBookBean));
                }
                foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK_CATS,"精致小食"));
                for (int i=0; i<result.getSnacks().getResult().getNum(); i++){
                    CookBookBean cookBookBean = result.getSnacks().getResult().getList().get(i);
                    foodContentItems.add(new FoodContentItem(FoodAdapter.TYPE_COOKBOOK,cookBookBean));
                }
                initView();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        adapter = new FoodAdapter(context,foodContentItems);
        adapter.setHeadDatas(headerDatas);
        adapter.setHasFooter(false);
        rv.setAdapter(adapter);
//        layoutManager = new GridLayoutManager(context, 3);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position == 0 || position == 1 || position%4 == 1) return 3;
//                return (position == adapter.getItemCount()-1) ? 3 : 1;
//            }
//        });
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setVisibility(View.VISIBLE);
        pbRecommend.setVisibility(View.GONE);
    }
}
