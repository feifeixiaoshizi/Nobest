package com.study.nobest.mvp.presenter;

import android.util.Log;

import com.study.baselibrary.network.BaseResponseObserver;
import com.study.nobest.mvp.contract.TestContract;
import com.study.nobest.mvp.model.TestModel;
import com.study.nobest.mvp.model.entity.PoemListInfo;

import javax.inject.Inject;
import javax.inject.Singleton;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/27 0027.
 * 参数传递的设计的
 */
@Singleton
public class TestPresenter implements TestContract.ITestPresenter {
    String TAG = "TestPresenter";
    TestContract.ITestView view;
    TestContract.ITestModel model;

    public TestPresenter() {
    }

    @Inject
    public TestPresenter(TestContract.ITestView view, TestContract.ITestModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
        model = new TestModel();
        //测试墨言的接口
        // checkVersion();
        //poemList1();
        loadContent();
        checkVersion();



    }


    private void loadContent() {
        model.loadContent().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.setViewContent(s);
                Log.d(TAG, "s:" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.setViewContent(throwable.toString());
                Log.d(TAG, "throwable:" + throwable.toString());

            }
        });

    }


    private void checkVersion() {
        model.checkVersion("776814BF941B47E3B6776B2EE5A14724", 1, 14).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "o:" + o.toString());
                view.setViewContent(o.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.setViewContent(throwable.toString());
                Log.d(TAG, "throwable:" + throwable.toString());

            }
        });

    }

    /*
        //appType=1&pageId=1&userId=776814BF941B47E3B6776B2EE5A14724&poemType=2&operableSign=776814BF941B47E3B6776B2EE5A14724
        @GET("poemList.action")
        Observable<Object> poemList(@Query("operableSign")String operableSign,@Query("userId")String userId,@Query("appType")int appType,@Query("poemType")int poemType,@Query("pageId")int pageId );
*/
    private void poemList() {
        model.poemList("776814BF941B47E3B6776B2EE5A14724", "", 1, 2, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "o:" + o.toString());
                view.setViewContent(o.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.setViewContent(throwable.toString());
                Log.d(TAG, "throwable:" + throwable.toString());

            }
        });

    }

    private void poemList1() {
        model.poemList("776814BF941B47E3B6776B2EE5A14724", "776814BF941B47E3B6776B2EE5A14724", 1, 2, 1).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new BaseResponseObserver<PoemListInfo>() {
                    @Override
                    public void onSuccess(PoemListInfo poemListInfo) {
                        Log.d(TAG,"poemListInfo:"+poemListInfo.toString());
                        view.setViewContent(poemListInfo.toString());
                    }
                });

    }


    @Override
    public void destroy() {
        view.destroy();

    }

    @Override
    public void setView(TestContract.ITestView view) {
        this.view = view;

    }
}
