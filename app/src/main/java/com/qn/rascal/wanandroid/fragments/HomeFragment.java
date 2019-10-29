package com.qn.rascal.wanandroid.fragments;


import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qn.rascal.wanandroid.R;
import com.qn.rascal.wanandroid.adapters.Adapter_Recycler;
import com.qn.rascal.wanandroid.base.BaseFragment;
import com.qn.rascal.wanandroid.bean.Bean_banner;
import com.qn.rascal.wanandroid.bean.Bean_home;
import com.qn.rascal.wanandroid.contract.MainContract;
import com.qn.rascal.wanandroid.presenter.IMainPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<MainContract.MainView, IMainPresenter<MainContract.MainView>> implements MainContract.MainView {


    @BindView(R.id.recycler_home)
    RecyclerView mRecyclerHome;
    @BindView(R.id.smart_home)
    SmartRefreshLayout mSmartHome;
    private Adapter_Recycler mAdapter_recycler;
    private ArrayList<Bean_home.DataBean.DatasBean> mList = new ArrayList<>();
    private ArrayList<Bean_banner.DataBean> mBanner = new ArrayList<>();

    @Override
    protected int createLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //初始化RecyclerView
        mRecyclerHome.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerHome.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter_recycler = new Adapter_Recycler(mList, mBanner, getContext());
        mRecyclerHome.setAdapter(mAdapter_recycler);


    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }


    @Override
    public void getListData(Bean_home beans) {
        if (beans != null) {
            List<Bean_home.DataBean.DatasBean> datas = beans.getData().getDatas();
            mList.addAll(datas);
            mAdapter_recycler.notifyDataSetChanged();
        }
        Log.d("111", "getListData: " + beans.toString());
    }

    @Override
    public void getBannerData(Bean_banner bean_banners) {
        if (bean_banners != null) {
            List<Bean_banner.DataBean> data = bean_banners.getData();
            mBanner.addAll(data);
        }
        Log.d("111", "getBannerData: " + bean_banners.toString());
    }

    @Override
    public void onError(String error) {
        Log.e("111", "onError: HomeFragment中数据请求失败" + error);
    }

    @Override
    protected IMainPresenter<MainContract.MainView> createPresenter() {
        return new IMainPresenter<>();
    }


}
