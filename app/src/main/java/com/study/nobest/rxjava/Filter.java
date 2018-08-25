package com.study.nobest.rxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by jianshengli on 2018/3/26.
 * 过滤：
 * 1：自定义过滤条件
 * 2：类型过滤
 * 3：位置过滤
 *
 *
 *
 */

public class Filter {
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

    /*------------------------------------过滤型---------------------------------------------------------*/
    public void testFilter() {
        Observable.just(1,2,4,8).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer>3;
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer+"";
            }
        }).subscribe(observer);


    }


    public void testOfType() {
        Observable.just("1",3,true).ofType(String.class).subscribe(observer);

    }

    public void testTake() {
        //根据位置过滤
       Observable.just("2","3","4").take(2).subscribe(observer);
    }

    public void testTakeLast() {
        //根据位置过滤
       Observable.just("2","3","4").takeLast(2).subscribe(observer);
    }


    public void testDistinct() {
        //根据位置过滤
        Observable.just("2","2","4").distinct().subscribe(observer);

    }



}
