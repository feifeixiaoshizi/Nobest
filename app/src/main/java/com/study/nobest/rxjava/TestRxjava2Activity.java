package com.study.nobest.rxjava;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.baselibrary.base.BaseActivity;
import com.study.nobest.R;
import com.study.nobest.thread.MyThread;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class TestRxjava2Activity extends BaseActivity implements View.OnClickListener {
    private String TAG = "TestRxjava2Activity";
    //创建型
    private TextView create, from, just, empty, error, never, timer, interval, range, defer;
    private Create createDemo = new Create();

    //合并型
    private TextView concat, merge, zip, combineLatest;
    private Merge mergeDemo = new Merge();

    //过滤型
    private TextView  testTakeLast, take , ofType , filter;
    private Filter filterDemo = new Filter();

    //判断型
    private TextView takeUntil;
    private Judge judgeDemo = new Judge();

    //变换
    private TextView flatMap;
    private Map mapDemo= new Map();


    @Override
    protected int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rxjava);
        //创建型
        create = findViewById(R.id.create);
        from = findViewById(R.id.from);
        just = findViewById(R.id.just);

        empty = findViewById(R.id.empty);
        error = findViewById(R.id.error);
        never = findViewById(R.id.never);

        timer = findViewById(R.id.timer);
        interval = findViewById(R.id.interval);
        defer = findViewById(R.id.defer);

        range = findViewById(R.id.range);

        //合并型
        concat = findViewById(R.id.concat);
        merge = findViewById(R.id.merge);
        zip = findViewById(R.id.zip);
        combineLatest = findViewById(R.id.combineLatest);



        //过滤型
        testTakeLast = findViewById(R.id.testTakeLast);
        take = findViewById(R.id.take);
        ofType = findViewById(R.id.ofType);
        filter = findViewById(R.id.filter);


        //判断型
        takeUntil = findViewById(R.id.takeUntil);

        //变换类型
        flatMap = findViewById(R.id.flatMap);


        initClickListener();

        return 0;
    }


    private void initClickListener() {
        //创建型
        create.setOnClickListener(this);
        from.setOnClickListener(this);
        just.setOnClickListener(this);
        empty.setOnClickListener(this);
        error.setOnClickListener(this);
        never.setOnClickListener(this);
        timer.setOnClickListener(this);
        interval.setOnClickListener(this);
        defer.setOnClickListener(this);
        range.setOnClickListener(this);

        //合并型
        concat.setOnClickListener(this);
        merge.setOnClickListener(this);
        zip.setOnClickListener(this);
        combineLatest.setOnClickListener(this);


        //过滤型
        testTakeLast.setOnClickListener(this);
        take .setOnClickListener(this);
        ofType .setOnClickListener(this);
        filter .setOnClickListener(this);


        //判断型
        takeUntil.setOnClickListener(this);

        //变换
        flatMap.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                createDemo.testCreate();
                break;
            case R.id.from:
                break;
            case R.id.just:
                break;
            case R.id.empty:
                createDemo.testEmpty();
                break;
            case R.id.error:
                createDemo.testError();
                break;
            case R.id.never:
                createDemo.testNever();
                break;
            case R.id.timer:
                createDemo.testTimer();
                break;
            case R.id.interval:
                //createDemo.testInterval();
                createDemo.testBackPress();
                break;
            case R.id.defer:
                break;
            case R.id.range:
                createDemo.testRange();
                break;

                //合并型
            case R.id.concat:
                mergeDemo.testConcat();
                break;
            case R.id.merge:
                mergeDemo.testMerge1();
                break;
            case R.id.zip:
                mergeDemo.testzip();
                break;
            case R.id.combineLatest:
               // mergeDemo.testCombineLatest();
                mergeDemo.testCombineLatest1();
                break;

            //过滤型
            case R.id.filter:
                filterDemo.testFilter();
                break;
            case R.id.ofType:
                filterDemo.testOfType();
                break;
            case R.id.take:
                filterDemo.testTake();
                break;
            case R.id.testTakeLast:
              filterDemo.testTakeLast();
                break;

            //判断型
            case R.id.takeUntil:
                //judgeDemo.testTakeUntil2();
                judgeDemo.testTakeWhile();
                break;

                //变换类型
            case R.id.flatMap:
                mapDemo.testFlatMap1();
                break;
        }
    }






  /*------------------------------------过滤型---------------------------------------------------------*/
}
