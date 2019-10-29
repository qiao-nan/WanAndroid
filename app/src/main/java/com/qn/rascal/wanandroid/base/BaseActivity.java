package com.qn.rascal.wanandroid.base;

/**
 * Created by Rascal on 2019/10/24.
 */

public abstract class BaseActivity<V,P extends BasePresenter<V>> extends SimpleActivity {

    private P mPresenter;

    @Override
    public void initPresenter() {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    //子类去实现
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.destoryView();
            mPresenter=null;
        }
    }
}
