package com.qn.rascal.wanandroid.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Rascal on 2019/10/24.
 */

public abstract class BaseFragment <V,P extends BasePresenter<V>> extends SimpleFragment{
    public P mPresenter;
    @Override
    protected void initPresenter() {
        mPresenter=createPresenter();
        if(mPresenter!=null){
            mPresenter.attachView((V) this);
        }
    }

    protected abstract P createPresenter();

    public static Fragment newInstance(String param1,Class<?> fragmentClass){
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle args = new Bundle();
            args.putString("", param1);
            fragment.setArguments(args);
        } catch (java.lang.InstantiationException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.destoryView();
            mPresenter=null;
        }
    }
}
