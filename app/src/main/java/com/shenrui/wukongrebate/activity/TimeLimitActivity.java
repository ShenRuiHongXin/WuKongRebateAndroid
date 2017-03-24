package com.shenrui.wukongrebate.activity;

import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.adapter.BaseGoodsAdapter;
import com.shenrui.wukongrebate.biz.GetNetWorkDatas;
import com.shenrui.wukongrebate.entities.TbkItem;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.MFGT;
import com.shenrui.wukongrebate.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_time_limit)
public class TimeLimitActivity extends BaseActivity {
    @ViewById(R.id.tabs_time_limit)
    TabLayout tabs;
    @ViewById(R.id.vp_time_one_limit)
    ViewPager vpo;
    @ViewById(R.id.layout_vp_group)
    RelativeLayout layoutVpGroup;

    @ViewById(R.id.iv_add_cart)
        ImageView ivAddCar;

    @ViewById(R.id.tv_time_goods_title)
            TextView goodsTitle;
    @ViewById(R.id.tv_time_price_old)
            TextView goodsPriceOld;
    @ViewById(R.id.tv_time_price_new)
    TextView goodsPriceNew;

    @ViewById(R.id.rv_time_limit_like)
    RecyclerView rcvLick;

    private MyPagerAdapter myPagerAdapter;
    private List<TbkItem> likeGoods;

    @AfterViews
    void init(){
        getWindow().setBackgroundDrawable(null);
        initData();
        intVp();
        initTabs();
        setListener();
        getLikeGoodsData();
    }

    List list1 = new ArrayList();
    List list2 = new ArrayList();
    List list3 = new ArrayList();
    List<List> listAll = new ArrayList();
    private void initData(){
        list1.add(new TimeLimitGood("VS沙宣洗发水 顺滑发丝男女士清盈顺柔洗发露750ml"
                ,"https://img.alicdn.com/bao/uploaded/i2/TB14uGfLVXXXXX1XXXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg"
                ,115,98.90));
        list1.add(new TimeLimitGood("MAC口红3g正品代购魅可子弹头时尚哑光豆沙色显色唇膏chili姨妈红"
                ,"https://gd1.alicdn.com/imgextra/i1/123700490/TB2IEflchRDOuFjSZFzXXcIipXa_!!123700490.jpg_400x400.jpg"
                ,158,129));
        list1.add(new TimeLimitGood("沐鱼包包2016夏季新款小方包卡通手提包单肩包印花甜美可爱斜挎包"
                ,"https://img.alicdn.com/imgextra/i2/TB2hAFjpXXXXXXhXFXXXXXXXXXX_!!166818669.jpg_430x430q90.jpg"
                ,218,179));

        list2.add(new TimeLimitGood("苹果ipad mini2保护套全包边真皮6平板电脑air1超薄3迷你4休眠5壳"
                ,"https://gd2.alicdn.com/imgextra/i2/112890393/TB2ytPxsFXXXXbCXXXXXXXXXXXX_!!112890393.jpg_400x400.jpg"
                ,158,54));
        list2.add(new TimeLimitGood("非凡正品发廊专业吹风机理发店大功率电吹风家用负离子冷热吹风筒"
                ,"https://gd4.alicdn.com/imgextra/i3/677942112/TB2ohoujdRopuFjSZFtXXcanpXa_!!677942112.jpg_400x400.jpg"
                ,170,68));
        list2.add(new TimeLimitGood("机械牧马人3代游戏鼠标有线无声静音CFLOL电竞守望先锋宏鼠标加重"
                ,"https://gd4.alicdn.com/imgextra/i3/1636357614/TB23UsHXgFkpuFjSspnXXb4qFXa_!!1636357614.jpg_400x400.jpg"
                ,189,45));

        list3.add(new TimeLimitGood("春季字母宽松中长款套头卫衣女装韩版春装上衣加绒学生大码外套潮"
                ,"https://img.alicdn.com/bao/uploaded/i4/TB1v5PuOXXXXXXhXFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg"
                ,120,99));
        list3.add(new TimeLimitGood("华姿仪赏 花神 原创设计花朝节主题汉服女装大袖衫礼服搭配襦裙"
                ,"https://gd4.alicdn.com/imgextra/i1/TB1lGOJPpXXXXaXXFXXYXGcGpXX_M2.SS2_400x400.jpg"
                ,398,350.24));
        list3.add(new TimeLimitGood("1娉洛-b834新款2017春韩版女装V领条纹收腰显瘦拼网纱连衣裙C-06"
                ,"https://gd2.alicdn.com/imgextra/i2/56651352/TB2bkS4cdhvOuFjSZFBXXcZgFXa_!!56651352.jpg_400x400.jpg"
                ,150,75));

        listAll.add(list1);
        listAll.add(list2);
        listAll.add(list3);
    }

    @Click(R.id.iv_time_limit_back)
    void clickEvent(View view){
        MFGT.finish(this);
    }

    //TabLayout监听
    private void setListener() {
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv_time)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) tab.getCustomView().findViewById(R.id.tv_status)).setTextColor(getResources().getColor(R.color.white));
//                vpo.setAdapter(new MyPagerAdapter(listAll.get((int)tab.getTag())));
                myPagerAdapter = new MyPagerAdapter();
                myPagerAdapter.setTimeLimitGoodData(listAll.get((int)tab.getTag()));
                vpo.setAdapter(myPagerAdapter);
                if((int)tab.getTag() == 0){
                    if(!state1){
                        ivAddCar.setImageResource(R.drawable.home_btn_addtoshopingcart_no);
                    }else{
                        ivAddCar.setImageResource(R.drawable.home_btn_addtoshopingcart);
                    }
                }
                if((int)tab.getTag() == 1){
                    if(!state2){
                        ivAddCar.setImageResource(R.drawable.home_btn_addtoshopingcart_no);
                    }else{
                        ivAddCar.setImageResource(R.drawable.home_btn_addtoshopingcart);
                    }
                }
                if((int)tab.getTag() == 2){
                    if(!state3){
                        ivAddCar.setImageResource(R.drawable.home_btn_addtoshopingcart_no);
                    }else{
                        ivAddCar.setImageResource(R.drawable.home_btn_addtoshopingcart);
                    }
                }
                changeGoodInfo(0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv_time)).setTextColor(getResources().getColor(R.color.textcolor_primary));
                ((TextView) tab.getCustomView().findViewById(R.id.tv_status)).setTextColor(getResources().getColor(R.color.textcolor_primary));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //猜你喜欢
    @Background
    void getLikeGoodsData(){
        Map<String, Object> result = GetNetWorkDatas.getSearchGoods("口红", 1,10);
        likeGoods = (List<TbkItem>) result.get("goodsList");
        initLikeGoodsView();
    }
    @UiThread
    void initLikeGoodsView(){
        GridLayoutManager layoutManager;
        layoutManager = new GridLayoutManager(TimeLimitActivity.this, 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        rcvLick.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view)!=0){
                    if (parent.getChildPosition(view)%2!=0){
                        outRect.set(4,0,3,4);
                    }else{
                        outRect.set(3,0,4,4);
                    }
                }
            }
        });
        BaseGoodsAdapter adapter = new BaseGoodsAdapter(TimeLimitActivity.this,likeGoods);
        rcvLick.setAdapter(adapter);
        rcvLick.setLayoutManager(layoutManager);
    }

    int curP = -1;
    int curHour = -1;
    boolean state1 = false;
    boolean state2 = false;
    boolean state3 = false;
    private void intVp(){
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。
        // 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        vpo.setClipChildren(false);
        //父容器一定要设置这个，否则看不出效果
        layoutVpGroup.setClipChildren(false);
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat    ("HH");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        curHour = Integer.parseInt(str);
        List data = null;
        if(curHour < 10){
            curP = 0;
        }else if(curHour > 10 && curHour < 14){
            curP = 1;
        }else{
            curP = 2;
        }

        myPagerAdapter = new MyPagerAdapter();
        myPagerAdapter.setTimeLimitGoodData(listAll.get(curP));
        vpo.setAdapter(myPagerAdapter);
        //设置ViewPager切换效果，即实现画廊效果
        vpo.setPageTransformer(true,new ZoomOutPageTransformer());
        //设置预加载数量
        vpo.setOffscreenPageLimit(2);
        //设置每页之间的左右间隔
        vpo.setPageMargin(100);
        vpo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d("onPageSelected position: " + position);
                changeGoodInfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtil.d("onPageScrollStateChanged state: " + state);
            }
        });
        changeGoodInfo(0);
    }
    private void changeGoodInfo(int position){
        TimeLimitGood tlg = (TimeLimitGood) myPagerAdapter.getTimeLimitGoodData().get(position);
        goodsTitle.setText(tlg.getGoodName());
        goodsPriceOld.setText(Utils.transformDouble(tlg.getGoodPrice()));
        goodsPriceNew.setText(Utils.transformDouble(tlg.getGoodPriceZk()));
    }

    private void initTabs() {
        String[] timeStages = new String[]{"07:00-10:00","11:00-14:00","15:00-18:00"};
        for(int i=0;i<timeStages.length;i++){
            TabLayout.Tab tab = tabs.newTab();
            //TabLayout.Tab tab = tabs.getTabAt(i);
            tab.setCustomView(R.layout.layout_tab_time_item);
            tab.setTag(i);
            tabs.addTab(tab);
            TextView tvTime = (TextView) tab.getCustomView().findViewById(R.id.tv_time);
            TextView tvStatus = (TextView) tab.getCustomView().findViewById(R.id.tv_status);
            if(curHour <7){
                tvStatus.setText("即将开场");
            }else if(curHour < 10){
                if(i == 0){
                    tvStatus.setText("抢购进行中");
                    state1 = true;
                }else{
                    tvStatus.setText("即将开场");
                }
            }else if (curHour < 11){
                if(i == 0){
                    tvStatus.setText("已抢完");
                }else {
                    tvStatus.setText("即将开场");
                }
            }else if( curHour < 14){
                if(i == 0){
                    tvStatus.setText("已抢完");
                }else if(i == 1){
                    tvStatus.setText("抢购进行中");
                    state2 = true;
                }else{
                    tvStatus.setText("即将开场");
                }
            }else if(curHour < 15){
                if(i == 2){
                    tvStatus.setText("即将开场");
                }else{
                    tvStatus.setText("已抢完");
                }
            }else if(curHour < 18){
                if(i == 2){
                    tvStatus.setText("抢购进行中");
                    state3 = true;
                }else{
                    tvStatus.setText("已抢完");
                }
            }else{
                tvStatus.setText("已抢完");
            }
            if (i == curP) {
                //设置第一个tab的TextView是被选择的样式
                tvTime.setTextColor(getResources().getColor(R.color.white));
                tvStatus.setTextColor(getResources().getColor(R.color.white));
                tab.select();
            }
            tvTime.setText(timeStages[i]);//设置tab上的文字
        }
    }

    /**********************************************************************/
    /**
     * 限时抢商品
     */
    private class TimeLimitGood{
        private String goodName;
        private String goodImg;
        private double goodPrice;
        private double goodPriceZk;

        public TimeLimitGood(String goodName, String goodImg, double goodPrice, double goodPriceZk) {
            this.goodName = goodName;
            this.goodImg = goodImg;
            this.goodPrice = goodPrice;
            this.goodPriceZk = goodPriceZk;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getGoodImg() {
            return goodImg;
        }

        public void setGoodImg(String goodImg) {
            this.goodImg = goodImg;
        }

        public double getGoodPrice() {
            return goodPrice;
        }

        public void setGoodPrice(double goodPrice) {
            this.goodPrice = goodPrice;
        }

        public double getGoodPriceZk() {
            return goodPriceZk;
        }

        public void setGoodPriceZk(double goodPriceZk) {
            this.goodPriceZk = goodPriceZk;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        private List timeLimitGoodData;

        public MyPagerAdapter() {
        }

        public MyPagerAdapter(List data) {
            timeLimitGoodData = data;
        }

        public List getTimeLimitGoodData() {
            return timeLimitGoodData;
        }

        public void setTimeLimitGoodData(List timeLimitGoodData) {
            this.timeLimitGoodData = timeLimitGoodData;
        }

        @Override
        public int getCount() {
            //return Integer.MAX_VALUE;
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(TimeLimitActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setImageResource(images[position%images.length]);
            Glide.with(TimeLimitActivity.this)
                    .load(((TimeLimitGood)(timeLimitGoodData.get(position))).getGoodImg())
                    .into(imageView);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView)object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    /**
     * 实现的原理是，在当前显示页面放大至原来的MAX_SCALE
     * 其他页面才是正常的的大小MIN_SCALE
     */
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.5f;
        private static final float MIN_SCALE = 1.0f;//0.85f

        @Override
        public void transformPage(View view, float position) {
            //setScaleY只支持api11以上
            if (position < -1){
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                float scaleFactor =  MIN_SCALE+(1-Math.abs(position))*(MAX_SCALE-MIN_SCALE);
                view.setScaleX(scaleFactor);
                //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
                if(position>0){
                    view.setTranslationX(-scaleFactor*2);
                }else if(position<0){
                    view.setTranslationX(scaleFactor*2);
                }
                view.setScaleY(scaleFactor);

            } else
            { // (1,+Infinity]
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
