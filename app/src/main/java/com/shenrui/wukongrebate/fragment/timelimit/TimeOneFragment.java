package com.shenrui.wukongrebate.fragment.timelimit;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_time_one)
public class TimeOneFragment extends Fragment {
    @ViewById(R.id.vp_time_one_limit)
    ViewPager vp;
    @ViewById(R.id.tv_time_goods_title)
    TextView tvGoodsTitle;
    @ViewById(R.id.tv_time_price_new)
    TextView tvNewPrice;
    @ViewById(R.id.tv_time_price_old)
    TextView tvOldPrice;
    @ViewById(R.id.iv_add_cart)
    ImageView ivAddCart;
    @ViewById(R.id.layout_vp_group)
    RelativeLayout layoutVpGroup;

    Context context;
    int[] images = new int[]{R.drawable.nav_img_left,R.drawable.nav_img_middle,R.drawable.nav_img_right};

    @AfterViews
    void init(){
        context = getContext();
        initVp();
    }

    private void initVp() {
        /*DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();

        int width = dm.widthPixels * 5 / 10;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        vp.setLayoutParams(params);*/
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。
        // 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        vp.setClipChildren(false);
        //父容器一定要设置这个，否则看不出效果
        layoutVpGroup.setClipChildren(false);
        vp.setAdapter(new MyPagerAdapter());
        //设置ViewPager切换效果，即实现画廊效果
        vp.setPageTransformer(true,new ZoomOutPageTransformer());
        //设置预加载数量
        vp.setOffscreenPageLimit(2);
        //设置每页之间的左右间隔
        vp.setPageMargin(50);

    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[position%images.length]);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView)object);
        }
    }

    /**
     * 实现的原理是，在当前显示页面放大至原来的MAX_SCALE
     * 其他页面才是正常的的大小MIN_SCALE
     */
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.2f;
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
}
