package com.qn.rascal.wanandroid.model;

import android.util.Log;

import com.qn.rascal.wanandroid.bean.Bean_banner;
import com.qn.rascal.wanandroid.bean.Bean_home;
import com.qn.rascal.wanandroid.contract.MainContract;
import com.qn.rascal.wanandroid.http.ApiService;
import com.qn.rascal.wanandroid.http.BaseObserver;
import com.qn.rascal.wanandroid.http.BaseResponse;
import com.qn.rascal.wanandroid.http.HttpManager;
import com.qn.rascal.wanandroid.utils.RxUtils;

import java.util.List;

/**
 * Created by Rascal on 2019/10/25.
 */

public class IMainModle implements MainContract.MainModuel {
    @Override
    public void getData(final MainCallBack mainCallBack) {

        HttpManager.getInstance().getService(ApiService.BaseURL, ApiService.class)
                .getHomeJson()
                .compose(RxUtils.<BaseResponse<List<Bean_home>>>rxSchedulrThread())
                .compose(RxUtils.<List<Bean_home>>rxChangeResult())
                .subscribe(new BaseObserver<List<Bean_home>>() {
                    @Override
                    public void onSuccess(List<Bean_home> bean_homes) {
                        if (bean_homes != null) {
                            for (int i = 0; i < bean_homes.size(); i++) {
                                Bean_home bean_home = bean_homes.get(i);
                                mainCallBack.setListData(bean_home);
                            }
                        }
                        Log.d("111", "onSuccess: " + "列表数据请求成功" + bean_homes.toString());
                    }

                    @Override
                    public void onFail(String error) {
                        mainCallBack.onError(error);
                        Log.e("111", "onFail: " + "列表数据请求失败");
                    }
                });
        HttpManager.getInstance().getService(ApiService.BaseURL, ApiService.class)
                .getBannerJson()
                .compose(RxUtils.<BaseResponse<List<Bean_banner>>>rxSchedulrThread())
                .compose(RxUtils.<List<Bean_banner>>rxChangeResult())
                .subscribe(new BaseObserver<List<Bean_banner>>() {
                    @Override
                    public void onSuccess(List<Bean_banner> bean_banners) {
                        if(bean_banners!=null){
                            for (int i = 0; i < bean_banners.size(); i++) {
                                Bean_banner bean_banner = bean_banners.get(i);
                                mainCallBack.setBannerData(bean_banner);
                            }

                        }
                        Log.d("111", "onSuccess: " + "Banner数据请求成功" + bean_banners.toString());
                    }

                    @Override
                    public void onFail(String error) {
                        mainCallBack.onError(error);
                        Log.e("111", "onFail: " + "Banner数据请求失败");
                    }
                });

    }
}
