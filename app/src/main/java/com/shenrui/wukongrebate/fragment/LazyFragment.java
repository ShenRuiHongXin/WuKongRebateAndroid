package com.shenrui.wukongrebate.fragment;

import android.support.v4.app.Fragment;

/**
 * 取消预加载的Fragment
 * 当该Fragment可见时才加载网络数据
 */

public abstract class LazyFragment extends Fragment{
    protected boolean isVisible;//当前fragment是否可见

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInVisible();
        }
    }

    //不可见
    protected void onInVisible() {

    }

    //可见
    protected void onVisible() {
        lazyLoad();
    }

    //延迟加载，子类重写该方法，在里面加载数据
    protected abstract void lazyLoad();
}
