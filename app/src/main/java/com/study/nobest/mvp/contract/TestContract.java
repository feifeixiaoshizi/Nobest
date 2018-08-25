package com.study.nobest.mvp.contract;


import com.study.baselibrary.mvp.IModel;
import com.study.baselibrary.mvp.IPresenter;
import com.study.baselibrary.mvp.IView;
import com.study.nobest.mvp.model.entity.PoemListInfo;

import io.reactivex.Observable;


/**
 * Created by Administrator on 2018/2/27 0027.
 */

public interface TestContract {

    interface ITestView extends IView {
        void setViewContent(String content);
    }

    interface ITestPresenter extends IPresenter<ITestView> {

    }

    interface ITestModel extends IModel {
        Observable<String> loadContent();
        Observable<Object> checkVersion(String operableSign,int appType,int versionCode);
        Observable<PoemListInfo> poemList(String operableSign, String userId, int appType, int poemType, int pageId );

    }


}
