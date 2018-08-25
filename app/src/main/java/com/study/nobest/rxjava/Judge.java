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
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jianshengli on 2018/3/26.
 * 从观察者的onNext（） onError（） onComplete（）方法入手
 * 1:不调用onNext
 * never（）  empty（）  error()
 * <p>
 * 2:调用onNext多次
 * just  from  range
 * <p>
 * 3:时间上
 * timer  interval  defer（延迟创建）
 */

public class Judge {
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

    /*------------------------------------判断型---------------------------------------------------------*/
    public void testTakeUntil() {
        Observable<Boolean> flag = Observable.just(true);
        Observable.just("1", "2", "3").takeUntil(flag).subscribe(observer);

        /*
日志分析：
        不管是false和true都是同样的结果，不会发送数据。（******）
03-27 09:57:31.068 9587-9587/com.study.nobest D/TestRxjava2Activity: Disposable:[null, null]
03-27 09:57:31.068 9587-9587/com.study.nobest D/TestRxjava2Activity: onComplete
*/


    }


    public void testTakeUntil1() {
        Observable<Boolean> flag = Observable.timer(2, TimeUnit.SECONDS).map(new Function<Long, Boolean>() {
            @Override
            public Boolean apply(Long aLong) throws Exception {
                return false;
            }
        });
        createObservable().takeUntil(flag).subscribe(observer);

        /*

 日志分析：2秒后发送false，则终止1再发送数据。（******）
03-27 10:08:36.506 10778-10778/com.study.nobest D/TestRxjava2Activity: Disposable:[null, null]
03-27 10:08:36.508 10778-10778/com.study.nobest D/TestRxjava2Activity: onNext:1
03-27 10:08:36.508 10778-10778/com.study.nobest D/TestRxjava2Activity: onNext:2
03-27 10:08:37.508 10778-10778/com.study.nobest D/TestRxjava2Activity: onNext:3
03-27 10:08:38.509 10778-10836/com.study.nobest D/TestRxjava2Activity: onComplete
*/


    }


    public void testTakeUntil2() {
        Observable<Boolean> flag = createObservable1();
        createObservable().takeUntil(flag).subscribe(observer);


    }

    public void testTakeWhile() {
        createObservable().takeWhile(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                Log.d(TAG,"s:"+s);
                return s.compareTo("2")<0;
            }
        }).subscribe(observer);

        /*
不包含临界条件： 遇到结果未false的时候直接终止的数据的发送，而且临界的数据2也不会被发送。
03-27 11:20:51.576 23788-23788/com.study.nobest D/TestRxjava2Activity: Disposable:io.reactivex.internal.operators.observable.ObservableTakeWhile$TakeWhileObserver@709f356
03-27 11:20:51.581 23788-23853/com.study.nobest D/TestRxjava2Activity: s:1
03-27 11:20:51.581 23788-23853/com.study.nobest D/TestRxjava2Activity: onNext:1
03-27 11:20:51.581 23788-23853/com.study.nobest D/TestRxjava2Activity: s:2
03-27 11:20:51.581 23788-23853/com.study.nobest D/TestRxjava2Activity: onComplete
*/

    }


    //注意线程切换和线程异常
    public Observable<String> createObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                //延迟10秒发送数据3
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                e.onNext("3");
                e.onComplete();


            }
        }).subscribeOn(Schedulers.io());//切换到io线程（****）


    }

    public Observable<Boolean> createObservable1() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                Thread.sleep(500);
                e.onNext(true);
                //e.onComplete();


            }
        }).subscribeOn(Schedulers.io());


    }


}
