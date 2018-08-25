package com.study.nobest.rxjava;

import android.util.Log;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by jianshengli on 2018/3/26.
 */

public class Map {
    public String TAG = "TestRxjava2Activity";
    public Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "Disposable:" + d);
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

    /*------------------------------------变换---------------------------------------------------------*/
    public void testFlatMap() {

        Observable.just(2).
                flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer s) throws Exception {
                        return Observable.just(s + "");
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s:" + s);
            }
        });


    }

    public void testFlatMap1() {
        createObservable().
                flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just(s + "");
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s:" + s);
            }
        });


    }


    public void testFlatMap2() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        Observable.just(list).
                flatMap(new Function<List<String>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(List<String> s) throws Exception {
                        return Observable.fromIterable(s);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s:" + s);
            }
        });


    }


   // @RxLogObservable 可能暂时无法调试Rxjava2可以调试Rxjava1
    public Observable<String> createObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");

                e.onNext("2"+Thread.currentThread().getName());
                //延迟10秒发送数据3
                Thread.sleep(10000);
                e.onNext("3");
                Thread.sleep(10000);
                e.onNext("3.0");
                e.onComplete();


            }
        });


    }


}
