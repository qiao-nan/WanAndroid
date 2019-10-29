package com.qn.rascal.wanandroid.base;

import java.lang.ref.WeakReference;

/**
 * Created by Rascal on 2019/10/24.
 */

public class BasePresenter<V> {

    private WeakReference<V> mWeakReference;
    public V mView;

    public void attachView(V v){
        mWeakReference=new WeakReference<V>(v);
        mView=mWeakReference.get();
    }

    public void destoryView(){
        if(mWeakReference!=null){
            mWeakReference.clear();
            mWeakReference=null;
        }
    }
}
