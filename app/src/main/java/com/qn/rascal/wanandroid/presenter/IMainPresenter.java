package com.qn.rascal.wanandroid.presenter;

import com.qn.rascal.wanandroid.base.BasePresenter;
import com.qn.rascal.wanandroid.bean.Bean_banner;
import com.qn.rascal.wanandroid.bean.Bean_home;
import com.qn.rascal.wanandroid.contract.MainContract;
import com.qn.rascal.wanandroid.model.IMainModle;

import java.util.List;

/**
 * Created by Rascal on 2019/10/25.
 */

public class IMainPresenter <V extends MainContract.MainView> extends BasePresenter <V> implements MainContract.MainPresenter,MainContract.MainModuel.MainCallBack {
    MainContract.MainModuel mMainModuel = new IMainModle();

    @Override
    public void setListData(Bean_home beans) {
        if(mView!=null){
            mView.getListData(beans);
        }
    }

    @Override
    public void setBannerData(Bean_banner bean_banners) {
        if(mView!=null){
            mView.getBannerData(bean_banners);
        }
    }

    @Override
    public void onError(String error) {
        if(mView!=null){
            mView.onError(error);
        }
    }

    @Override
    public void getData() {
        if(mView!=null){
            mMainModuel.getData(this);
        }
    }
}
