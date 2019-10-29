package com.qn.rascal.wanandroid.http;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Rascal on 2019/10/23.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    //防止RXjava内存泄漏，方便解耦

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }


    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            ApiException apiException = (ApiException) e;
            String message = apiException.getMessage();
            Log.e("111", "onError: "+message);

        }else if(e instanceof HttpException){
            Log.e("111", "onError: "+e.getMessage());
        }else{
            Log.e("111", "onError: "+e.getMessage());
        }
        onFail(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onComplete() {
        if(mCompositeDisposable!=null){
            mCompositeDisposable.clear();
        }
    }

    public abstract void onSuccess(T t);
    public abstract void onFail(String error);
}
