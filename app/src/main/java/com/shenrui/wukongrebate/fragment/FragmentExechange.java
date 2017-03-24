package com.shenrui.wukongrebate.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.SignContentRecyAdapter;
import com.shenrui.wukongrebate.entities.JfExchangeGoods;
import com.shenrui.wukongrebate.entities.SignRecyItemData;
import com.shenrui.wukongrebate.view.CycleRotationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heikki on 2017/1/3.
 */

@EFragment(R.layout.fragment_exchange)
public class FragmentExechange extends BaseFragment {
    //轮播图
    @ViewById(R.id.crv_jf_exchange)
    CycleRotationView crvJfExchange;
    //奖品
    @ViewById(R.id.rv_jf_exchange)
    RecyclerView rvJfExchange;

    @AfterViews
    void init(){
        //轮播图
        int[] imgs = {R.drawable.index_banner1,R.drawable.index_banner2};
        crvJfExchange.setImages(imgs);
        //奖品
        List exchangeList = new ArrayList();
        exchangeList.add(new SignRecyItemData(new JfExchangeGoods("商品名称",R.drawable.index_sign_reward_img1,1000),SignRecyItemData.JF_EXCHANG));
        exchangeList.add(new SignRecyItemData(new JfExchangeGoods("商品名称",R.drawable.index_sign_reward_img2,1100),SignRecyItemData.JF_EXCHANG));
        exchangeList.add(new SignRecyItemData(new JfExchangeGoods("商品名称",R.drawable.index_sign_reward_img3,1200),SignRecyItemData.JF_EXCHANG));
        rvJfExchange.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        rvJfExchange.setAdapter(new SignContentRecyAdapter(getActivity() ,exchangeList));
    }
}
