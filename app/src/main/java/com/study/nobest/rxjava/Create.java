package com.study.nobest.rxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jianshengli on 2018/3/26.
 * 从观察者的onNext（） onError（） onComplete（）方法入手
 * 1:不调用onNext
 * never（）  empty（）  error()
 *
 * 2:调用onNext多次
 *   just  from  range
 *
 * 3:时间上
 * timer  interval  defer（延迟创建）
 *
 *
 *
 *
 *
 */

public class Create {
    public String TAG = "TestRxjava2Activity";
    public Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "Disposable:" + d.isDisposed()+d.hashCode());
            d.dispose();

        }

        @Override
        public void onNext(String o) {
            Log.d(TAG, "onNext:" + o);

        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError:" + e.toString());

        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete");
        }
    };

    /*------------------------------------创建型---------------------------------------------------------*/
    public void testCreate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "ObservableEmitter:" + e.hashCode());
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onComplete();


            }
        }).subscribe(observer);


    }


    public void testNever() {
        //给方法传递泛型
        Observable.<String>never().subscribe(observer);
        //只会调用这一个方法onSubscribe()


    }





    public void testEmpty() {
        //给方法传递泛型
        Observable.<String>empty().subscribe(observer);
        //只会调用方法onSubscribe()和onComplete（）
    }


    public void testError() {
        Exception exception = new RuntimeException("rxjava error!");
        //给方法传递泛型
        Observable.<String>error(exception).subscribe(observer);
        //只会调用方法onSubscribe()和onError（）

    }


    public void testRange() {
        Observable.<String>range(1, 3).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "";
            }
        }).subscribe(observer);

        /*
          日志分析：
          D/TestRxjava2Activity: Disposable:io.reactivex.internal.operators.observable.ObservableMap$MapObserver@f83ded0
          D/TestRxjava2Activity: onNext:1
          D/TestRxjava2Activity: onNext:2
          D/TestRxjava2Activity: onNext:3
          D/TestRxjava2Activity: onComplete*/

    }

    public void testInterval() {
        Observable.interval(1, TimeUnit.SECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                return aLong + "";
            }
        }).subscribe(observer);


    }

    /**
     * 测试背压
     */
    public void testBackPress(){
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        try {
                            Thread.currentThread().sleep(1000);
                            Log.d("testback:",aLong+"");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




    public void testTimer() {
        Observable.timer(1000, TimeUnit.MILLISECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                return aLong + "";
            }
        }).subscribe(observer);


    }

}
