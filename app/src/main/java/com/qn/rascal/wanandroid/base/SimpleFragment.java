package com.qn.rascal.wanandroid.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Rascal on 2019/10/24.
 */

public abstract class SimpleFragment extends Fragment{

    private Activity mActivity;
    private Context mContext;
    private View mRootView;
    private Unbinder mBind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        this.mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView==null){
            mRootView = inflater.inflate(createLayout(), container,false);
        }else{
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent!=null){

                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    protected abstract int createLayout();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBind = ButterKnife.bind(this, view);
        initView();
        initPresenter();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected void initPresenter() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //((ViewGroup)mRootView.getParent()).removeView(mRootView); 连写
        /*ViewGroup parent = (ViewGroup) mRootView.getParent();
        parent.removeView(mRootView);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBind!=null){
            mBind.unbind();
        }
    }
}
