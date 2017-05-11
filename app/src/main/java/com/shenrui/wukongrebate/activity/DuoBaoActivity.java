package com.shenrui.wukongrebate.activity;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.DuoBaoAdapter;
import com.shenrui.wukongrebate.biz.NetDao;
import com.shenrui.wukongrebate.entities.DuobaoResp;
import com.shenrui.wukongrebate.utils.FullyGridLayoutManager;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.OkHttpUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_duo_bao)
public class DuoBaoActivity extends BaseActivity {
//    @ViewById(R.id.magic_indicator)
//    MagicIndicator magic;
//    @ViewById(R.id.crv_duobao)
//    CycleRotationView crvDuobao;
//    @ViewById(R.id.tabs_duobao)
//    TabLayout tabs;
//    @ViewById(R.id.duo_vp)
//    ViewPager vp;
    @ViewById(R.id.rv_duobao)
    RecyclerView rv;
    @ViewById(R.id.progressBar)
    ProgressBar pb;

    Context context;
    DuoBaoAdapter adapter;
    List<DuoBaoAdapter.DuobaoData> datas;
    FullyGridLayoutManager layoutManager;

    @AfterViews
    void init(){
        context = this;
//        initTabs();
        showProgressBar();
        initView();
    }

    void initView(){
        int[] images = {R.drawable.home_banner,R.drawable.home_banner,R.drawable.home_banner};
        datas = new ArrayList<>();
        NetDao.getDuobaoGoods(this, new OkHttpUtils.OnCompleteListener<DuobaoResp>() {
            @Override
            public void onSuccess(DuobaoResp result) {
                LogUtil.d("夺宝商品:"+result.getRespDate().length);
                for (int i=0; i<result.getRespDate().length; i++){
                    datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,(result.getRespDate())[i]));
                }
            }

            @Override
            public void onError(String error) {

            }
        });
        //临时数据
        datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.HEADMENU,images,null));
//        for (int i = 0; i<10; i++){
//            if (i == 1){
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,100,50)));
//            }else if(i == 2){
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,100,100)));
//            }else if(i == 3){
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,100,0)));
//            }else{
//                datas.add(new DuoBaoAdapter.DuobaoData(DuoBaoAdapter.GOOD,null,new DuobaoGood(i,"商品"+i,0,null,0,(100+i),(50-i))));
//            }
//        }
        adapter = new DuoBaoAdapter(context,datas);
        rv.setAdapter(adapter);
        layoutManager = new FullyGridLayoutManager(context,2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view) == 0 ){
                    return;
                }
                if (parent.getChildPosition(view)%2==0){
                    outRect.set(10,5,5,5);
                }else{
                    outRect.set(5,5,10,5);
                }
            }
        });
        hideProgressBar();
    }

//    private void initTabs() {
//        final String[] titles = {"人气","最新","最快","价格"};
//        int[] images = {R.drawable.home_banner,R.drawable.home_banner,R.drawable.home_banner};
//        crvDuobao.setImages(images);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new RenQiFragment_());
//        for(int i=1;i<titles.length;i++){
//            DuoItemFragment fragment = new DuoItemFragment_();
//            Bundle bundle = new Bundle();
//            bundle.putString("title",titles[i]);
//            fragment.setArguments(bundle);
//            fragments.add(fragment);
//            tabs.addTab(tabs.newTab().setText(titles[i]));
//        }
//        vp.setOffscreenPageLimit(3);
//        MyPageAdapter adapter = new MyPageAdapter(this.getSupportFragmentManager(), fragments, Arrays.asList(titles));
//        vp.setAdapter(adapter);
//        tabs.setupWithViewPager(vp);
//
////        CommonNavigator commonNavigator = new CommonNavigator(this);
////        commonNavigator.setAdjustMode(true);
////        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
////            @Override
////            public int getCount() {
////                return titles == null ? 0 : titles.length;
////            }
////            @Override
////            public IPagerTitleView getTitleView(Context context, final int index) {
////                ColorTransitionPagerTitleView titleView = new ColorTransitionPagerTitleView(context);
////                titleView.setNormalColor(Color.GRAY);
////                titleView.setSelectedColor(Color.RED);
////                titleView.setText(titles[index]);
////                titleView.setTextSize(15);
////                titleView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        vp.setCurrentItem(index);
////                    }
////                });
////                return titleView;
////            }
////
////            @Override
////            public IPagerIndicator getIndicator(Context context) {
////                LinePagerIndicator indicator = new LinePagerIndicator(context);
////                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
////                indicator.setLineWidth(45);
////                indicator.setLineHeight(5);
////                indicator.setRoundRadius(10);
////                indicator.setColors(Color.RED);
////                return indicator;
////            }
////        });
////        magic.setNavigator(commonNavigator);
////        ViewPagerHelper.bind(magic,vp);
//    }


    void showProgressBar(){
        rv.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);
    }
    void hideProgressBar(){
        rv.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
    }

    @Click(R.id.iv_duo_back)
    void clickEvent(View view){
        MFGT.finish(this);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
