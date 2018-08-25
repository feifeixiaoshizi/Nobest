package com.study.nobest.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jianshengli on 2018/3/26.
 * 1:整体上
 * concat（逻辑顺序）  merge（时间顺序）
 *
 * 2：个体上
 * zip（逻辑顺序）    combineLatest（时间最近）
 *
 *
 *
 */

public class Merge {
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
    /*------------------------------------合并型---------------------------------------------------------*/
    public   void testConcat() {

        Observable<String> observable1= Observable.just("1","2","3");
        Observable<String> observable2= Observable.just("4","5","6");
        Observable.concat(observable1,observable2).subscribe(observer);
/*03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: Disposable:0
03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:1
03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:2
03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:3
03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:4
03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:5
03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:6
03-26 18:07:47.746 9401-9401/com.study.nobest D/TestRxjava2Activity: onComplete
*/

    }


    public void testMerge() {
        Observable<String> observable1= Observable.just("1","2","3");
        Observable<String> observable2= Observable.just("4","5","6");
        Observable.merge(observable1,observable2).subscribe(observer);

        /*
        * 03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: Disposable:0
03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:1
03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:2
03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:3
03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:4
03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:5
03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:6
03-26 18:07:31.237 9401-9401/com.study.nobest D/TestRxjava2Activity: onComplete
        *
        *
        * */


    }

    public void testMerge1() {
        //测试一个空的和一个非空的合并的结果
        Observable<String> observable1= Observable.<String>empty();
        Observable<String> observable2= Observable.just("4","5","6");
        Observable.merge(observable1,observable2).subscribe(observer);

/*
*日志分析：一个空的和一个非空的，合并后仅仅是非空的数据。（0+1=1；）
* 03-27 09:38:36.013 5946-5946/com.study.nobest D/TestRxjava2Activity: Disposable:0
03-27 09:38:36.014 5946-5946/com.study.nobest D/TestRxjava2Activity: onNext:4
03-27 09:38:36.014 5946-5946/com.study.nobest D/TestRxjava2Activity: onNext:5
03-27 09:38:36.014 5946-5946/com.study.nobest D/TestRxjava2Activity: onNext:6
03-27 09:38:36.016 5946-5946/com.study.nobest D/TestRxjava2Activity: onComplete
* */


    }

    public void testzip() {
        Observable<String> observable1= Observable.just("1","2","3");
        Observable<String> observable2= Observable.just("4","5","6");
        Observable.zip(observable1, observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s+s2;
            }
        }).subscribe(observer);

        /*
        *
        * 03-26 18:06:49.504 9401-9401/com.study.nobest D/TestRxjava2Activity: Disposable:0
03-26 18:06:49.504 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:14
03-26 18:06:49.504 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:25
03-26 18:06:49.504 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:36
03-26 18:06:49.505 9401-9401/com.study.nobes D/TestRxjava2Activity: onComplete

        * */

    }




    public void testCombineLatest() {
        Observable<String> observable1= Observable.just("1","2","3");
        Observable<String> observable2= Observable.just("4","5","6");
        Observable.combineLatest(observable1, observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s+s2;
            }
        }).subscribe(observer);

        /*

 日志分析：
03-26 18:05:30.231 9401-9401/com.study.nobest D/TestRxjava2Activity: Disposable:0
03-26 18:05:30.231 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:34
03-26 18:05:30.231 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:35
03-26 18:05:30.231 9401-9401/com.study.nobest D/TestRxjava2Activity: onNext:36
03-26 18:05:30.231 9401-9401/com.study.nobest D/TestRxjava2Activity: onComplete*/

    }

    public Observable<String> createObservable() {
        return  Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");

                e.onNext("2");
                //延迟10秒发送数据3
                Thread.sleep(10000);
                e.onNext("3");
                Thread.sleep(10000);
                e.onNext("3.0");
                e.onComplete();


            }
        });


    }

    public Observable<String> createObservable1() {
        return  Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Thread.sleep(1000);
                e.onNext("4");
                e.onNext("5");
                //延迟10秒发送数据3
                Thread.sleep(5000);
                e.onNext("6");
                e.onComplete();


            }
        });


    }


    public void testCombineLatest1() {
        Observable<String> observable1= createObservable();
        Observable<String> observable2= createObservable1();
        Observable.combineLatest(observable1, observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s+s2;
            }
        }).subscribeOn(Schedulers.io()).subscribe(observer);

        /*

observable2中的每个发送的数据都会和observable1中的最后一个数据结合。（*****）
 日志分析：
03-26 21:41:29.269 28425-28425/com.study.nobest D/TestRxjava2Activity: Disposable:null
03-26 21:41:50.276 28425-28692/com.study.nobest D/TestRxjava2Activity: onNext:3.04
03-26 21:41:50.277 28425-28692/com.study.nobest D/TestRxjava2Activity: onNext:3.05
03-26 21:41:55.278 28425-28692/com.study.nobest D/TestRxjava2Activity: onNext:3.06
03-26 21:41:55.281 28425-28692/com.study.nobest D/TestRxjava2Activity: onComplete*/

    }
}
