package com.qn.rascal.wanandroid.fragments;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.qn.rascal.wanandroid.R;
import com.qn.rascal.wanandroid.base.BaseFragment;
import com.qn.rascal.wanandroid.base.BasePresenter;
import com.qn.rascal.wanandroid.bean.Bean;
import com.qn.rascal.wanandroid.http.ApiService;
import com.qn.rascal.wanandroid.http.BaseObserver;
import com.qn.rascal.wanandroid.http.BaseResponse;
import com.qn.rascal.wanandroid.http.HttpManager;
import com.qn.rascal.wanandroid.utils.RxUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends BaseFragment {


    @Override
    protected void initData() {
        /*HttpManager.getInstance().getService(ApiService.BaseURL, ApiService.class)
                .getHomeJson()
                .compose(RxUtils.<BaseResponse<List<Bean>>>rxSchedulrThread())
                .compose(RxUtils.<List<Bean>>rxChangeResult())
                .subscribe(new BaseObserver<List<Bean>>() {
                    @Override
                    public void onSuccess(List<Bean> beans) {
                        Log.e("111", "onSuccess: " + beans.toString());
                    }

                    @Override
                    public void onFail(String error) {
                        Log.e("111", "onFail: " + error);
                    }
                });*/
    }


    @Override
    public int createLayout() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
