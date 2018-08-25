package com.study.nobest.mvp.presenter;

import android.content.ContentValues;
import android.util.Log;

import com.study.baselibrary.network.BaseResponseObserver;
import com.study.nobest.ResultData;
import com.study.nobest.mvp.contract.TestRecyclerViewContract;
import com.study.nobest.mvp.model.entity.PoemListInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jianshengli on 2018/4/30.
 */

@Singleton
public class TestRecyclerViewPresenter implements TestRecyclerViewContract.Presenter {
    private final static String TAG = "TestRecycler";
    private TestRecyclerViewContract.View mView;
    private TestRecyclerViewContract.Model mModel;
    //采用集合存储是为了在销毁的时候取消未完成的订阅，使用弱引用包装是为了防止已经完成的请求仍然存储不能被释放导致内存泄漏。
    private List<WeakReference<Disposable>> disposables;

    @Inject
    public TestRecyclerViewPresenter(TestRecyclerViewContract.View mView, TestRecyclerViewContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        disposables= new ArrayList<>();
    }

    @Override
    public void loadData(ContentValues arguments) {
        //appType=1&pageId=1&userId=776814BF941B47E3B6776B2EE5A14724&poemType=2&operableSign=776814BF941B47E3B6776B2EE5A14724
        String operableSign = arguments.getAsString("operableSign");
        String userId = arguments.getAsString("userId");
        int appType = arguments.getAsInteger("appType");
        int poemType = arguments.getAsInteger("poemType");
        int pageId = arguments.getAsInteger("pageId");

        mModel.poemList(operableSign, userId, appType, poemType, pageId).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach().
                subscribe(new BaseResponseObserver<PoemListInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposables.add(new WeakReference<Disposable>(d));
                    }

                    @Override
                    public void onSuccess(PoemListInfo poemListInfo) {
                        Log.d(TAG,"poemListInfo"+poemListInfo.toString());
                        ResultData resultData = new ResultData<>();
                        resultData.data=poemListInfo;
                        mView.refresh(resultData);


                    }
                });
    }

    @Override
    public void loadMoreData(ContentValues arguments) {
        //appType=1&pageId=1&userId=776814BF941B47E3B6776B2EE5A14724&poemType=2&operableSign=776814BF941B47E3B6776B2EE5A14724
        String operableSign = arguments.getAsString("operableSign");
        String userId = arguments.getAsString("userId");
        int appType = arguments.getAsInteger("appType");
        int poemType = arguments.getAsInteger("poemType");
        int pageId = arguments.getAsInteger("pageId");

        //onTerminateDetach是关键的方法，在取消订阅的时候可以断开链子。
        mModel.poemList(operableSign, userId, appType, poemType, pageId).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach().
                subscribe(new BaseResponseObserver<PoemListInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposables.add(new WeakReference<Disposable>(d));
                    }

                    @Override
                    public void onSuccess(PoemListInfo poemListInfo) {
                        ResultData resultData = new ResultData<>();
                        resultData.data=poemListInfo;
                        mView.freshLoadMore(resultData);

                    }
                });

    }

    @Override
    public void start() {


    }

    @Override
    public void destroy() {
        //释放数据层资源
        if(mModel!=null){
            mModel.destroy();
            mModel=null;
        }

        //在销毁的时候取消订阅并清空集合
        if(disposables!=null){
            for(WeakReference<Disposable> weakReference :disposables){
                if(weakReference.get()!=null){
                    weakReference.get().dispose();
                }

            }
            disposables.clear();
            disposables=null;
        }


    }


    @Override
    public void setView(TestRecyclerViewContract.View view) {
        this.mView = view;
    }
}
