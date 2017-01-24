package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.shenrui.wukongrebate.R;

/**
 * Created by heikki on 2017/1/21.
 */

public class RpvLoopAdapter extends LoopPagerAdapter{
    Context context;
    String[] imgs = new String[0];

    public void setImgs(String[] imgs){
        this.imgs = imgs;
        notifyDataSetChanged();
    }


    public RpvLoopAdapter(Context context,RollPagerView viewPager,String[] imgs) {
        super(viewPager);
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        Log.i("RollViewPager","getView:"+imgs[position]);

        ImageView view = new ImageView(container.getContext());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RollViewPager","onClick");
            }
        });
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(context)
                .load(imgs[position])
                .placeholder(R.drawable.loading)
                .crossFade()
                .centerCrop()
                .into(view);
        return view;
    }

    @Override
    public int getRealCount() {
        return imgs.length;
    }
}
