package com.study.nobest.rxjava;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jianshengli on 2018/3/26.
 *
 */

public class Collect {
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

    /*------------------------------------聚合结果---------------------------------------------------------*/
    public void testTakeUntil() {

        Observable observable1= Observable.just("6","7");
        Observable observable2=Observable.just("1", "2", "3");
        Observable.merge(observable1,observable2).collect(new Callable() {
            @Override
            public String call() throws Exception {
                return null;
            }
        }, new BiConsumer() {
            @Override
            public void accept(Object o, Object o2) throws Exception {

            }
        }).subscribe(new SingleObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

        /*
日志分析：
        不管是false和true都是同样的结果，不会发送数据。（******）
03-27 09:57:31.068 9587-9587/com.study.nobest D/TestRxjava2Activity: Disposable:[null, null]
03-27 09:57:31.068 9587-9587/com.study.nobest D/TestRxjava2Activity: onComplete
*/


    }




}
