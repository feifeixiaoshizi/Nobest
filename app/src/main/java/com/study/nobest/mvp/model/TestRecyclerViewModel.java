package com.study.nobest.mvp.model;

import com.study.baselibrary.utils.CommonUtil;
import com.study.nobest.DefaultApplication;
import com.study.nobest.mvp.contract.TestContract;
import com.study.nobest.mvp.contract.TestRecyclerViewContract;
import com.study.nobest.mvp.model.entity.PoemListInfo;
import com.study.nobest.network.Api;
import com.study.nobest.network.service.TestService;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class TestRecyclerViewModel implements TestRecyclerViewContract.Model{

    @Override
    public Observable<PoemListInfo> poemList(String operableSign, String userId, int appType, int poemType, int pageId) {
        TestService testService=  DefaultApplication.getMainRetrofitServiceManager().obtainRetrofitService(TestService.class);
        Observable<PoemListInfo> result = testService.poemList(operableSign,userId,appType,poemType,pageId);
        return result;
    }


    @Override
    public void destroy() {

    }
}
