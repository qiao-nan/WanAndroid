package com.qn.rascal.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Rascal on 2019/10/24.
 */

public abstract class SimpleActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createLayout());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initPresenter();
        initData();
    }

    protected abstract void initData();

    //初始化P层对象
    public void initPresenter() {

    }

    protected abstract void initView();

    //初始化布局
    protected abstract int createLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
