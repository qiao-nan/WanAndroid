package com.qn.rascal.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Rascal on 2019/10/24.
 */

public abstract class BaseLazyFragment <V,P extends BasePresenter<V>> extends BaseFragment {

    private boolean isPrepared;
    private boolean islazyed;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared=true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    public void lazyLoad(){
        if(getUserVisibleHint()&&isPrepared&&!islazyed){
            islazyed=true;
            onLazyLoad();
        }
    }

    protected abstract void onLazyLoad();
}
