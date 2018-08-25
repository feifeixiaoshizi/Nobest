package com.study.nobest.rxjava;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.baselibrary.base.BaseActivity;
import com.study.nobest.R;

import java.lang.annotation.Target;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TestRxjava2DisposeActivity extends BaseActivity implements View.OnClickListener {
    public static  final String TAG = "TestRxjava2Activity";
    //创建型
    private TextView create;


    @Override
    protected int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rxjava);
       // SystemClock.sleep(30*1000);
        Thread t= new  Thread(){
            @Override
            public void run() {
                test();
            }
        };

        t.start();

        SystemClock.sleep(1000);
        test();

       /* int i=0;
        while(i < 1000000000){
            i++;
        }*/
       /*
*/

        //创建型
        create = findViewById(R.id.create);
        initClickListener();

        return 0;
    }


    public static synchronized   void test(){

        try {
            Thread.currentThread().sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG,"error:"+e.toString());
        }
    }


    private void initClickListener() {
        //创建型
        create.setOnClickListener(this);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                testCreate();

                break;

        }
    }

    public static  Disposable disposable;
    public Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "Disposable:" + d.isDisposed()+d.hashCode());
            disposable=d;

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

    static class MyObservableOnSubscribe implements ObservableOnSubscribe<String>{

        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
            Log.d(TAG, "ObservableEmitter:" + e.hashCode());
            int i = 0;
            while (true){
                i++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                e.onNext(i+"");
                Log.d(TAG, "ThreadName:" + Thread.currentThread().getName()+"数据发送了："+i);
                if(i>=100){
                    //仅仅是切断了数据传送流，不再发送数据了，但是订阅关系依然存在。（***）
                    disposable.dispose();
                }

                if(i>=200){
                    break;
                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();

    }

    public void testCreate() {

        Observable.create(new MyObservableOnSubscribe()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }



}
