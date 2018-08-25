package com.study.baselibrary.network;

import android.widget.Toast;

import com.study.baselibrary.base.BaseApplication;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public abstract class BaseResponseObserver<T> implements Observer<T> {

    private Disposable d;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.d=d;
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Toast.makeText(BaseApplication.getInstance(),"网络错误："+e.toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onComplete() {

    }


    public Disposable getDisposable(){
     return d;
    }

    public abstract void onSuccess( T t);

}
