package com.study.nobest.mvp.contract;

import android.content.ContentValues;

import com.study.baselibrary.mvp.IModel;
import com.study.baselibrary.mvp.IPresenter;
import com.study.baselibrary.mvp.IView;
import com.study.nobest.DefaultApplication;
import com.study.nobest.ResultData;
import com.study.nobest.mvp.model.entity.PoemListInfo;
import com.study.nobest.network.service.TestService;

import io.reactivex.Observable;

/**
 * Created by jianshengli on 2018/4/30.
 */

public interface TestRecyclerViewContract {

    interface View extends IView{
        /**
         * 刷新界面
         */
       void refresh(ResultData resultData);

        /*
        刷新加载更多
         */
        void freshLoadMore(ResultData resultData);

    }


    interface Presenter extends IPresenter<View>{
        /**
         * 加载数据
         */
        void loadData(ContentValues arguments);


        /**
         * 加载更多的数据
         */
        void loadMoreData(ContentValues arguments);



    }


    interface Model extends IModel{
        Observable<PoemListInfo> poemList(String operableSign, String userId, int appType, int poemType, int pageId);
    }



}
