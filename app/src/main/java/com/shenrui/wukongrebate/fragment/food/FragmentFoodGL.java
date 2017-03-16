package com.shenrui.wukongrebate.fragment.food;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.FoodArticleActivity_;
import com.shenrui.wukongrebate.activity.FoodMenuActivity_;
import com.shenrui.wukongrebate.activity.FoodVideoActivity;
import com.shenrui.wukongrebate.activity.FoodVideoActivity_;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.view.CycleRotationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Map;

/**
 * 美食攻略
 */
@EFragment(R.layout.fragment_fragment_food_gn)
public class FragmentFoodGL extends Fragment {
    @ViewById(R.id.food_gl_cycle)
    CycleRotationView crv;
    @ViewById(R.id.tv_caipu)
    TextView tvCaiPu;
    @ViewById(R.id.tv_shipin)
    TextView tvShiPin;
    @ViewById(R.id.tv_zhoukan)
    TextView tvZhouKan;

    Context context;

    @AfterViews
    void init(){
        context = getContext();
        initCycle();
    }

    @Click({R.id.tv_caipu,R.id.tv_shipin,R.id.tv_zhoukan})
    void clickEvent(View view){
        switch (view.getId()){
            case R.id.tv_caipu:
                MFGT.startActivity(context, FoodMenuActivity_.class);
                break;
            case R.id.tv_shipin:
                MFGT.startActivity(context, FoodVideoActivity_.class);
                break;
            case R.id.tv_zhoukan:
                MFGT.startActivity(context, FoodArticleActivity_.class);
                break;
        }
    }



    private void initCycle() {
        int[] images = {R.drawable.banner,R.drawable.banner};
        crv.setImages(images);
    }



}
