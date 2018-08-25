package com.study.nobest.mvp.model;

import android.util.Log;

import com.study.baselibrary.base.BaseApplication;
import com.study.baselibrary.network.RetrofitServiceManager;
import com.study.baselibrary.network.StringConverterFactory;
import com.study.baselibrary.utils.CommonUtil;
import com.study.nobest.DefaultApplication;
import com.study.nobest.mvp.contract.TestContract;
import com.study.nobest.mvp.model.entity.PoemListInfo;
import com.study.nobest.network.Api;
import com.study.nobest.network.service.TestService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class TestModel implements TestContract.ITestModel {
    String TAG= "TestModel";
    @Override
    public Observable<String> loadContent() {
       /* OkHttpClient client = new OkHttpClient();
        client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request =chain.request();
                Response response =  chain.proceed(request);
                Log.d(TAG,"response:"+response.body().string());
                return response;
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.APP_DOMAIN)
                .addConverterFactory(StringConverterFactory.create())//添加直接返回String的转化器，在Gson转换器前面添加。
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
       */

        Retrofit retrofit = DefaultApplication.getiApplication().getAppComponent().retrofit().newBuilder().baseUrl(Api.APP_DOMAIN).build();
        TestService testService=  CommonUtil.getNewRetrofitServiceManager(retrofit).obtainRetrofitService(TestService.class);
        Observable<String> result = testService.getContent();
        return result;
    }

    @Override
    public Observable<Object>  checkVersion(String operableSign,int appType,int versionCode){
        TestService testService=  DefaultApplication.getMainRetrofitServiceManager().obtainRetrofitService(TestService.class);
        Observable<Object> result = testService.checkVersion(operableSign,appType,versionCode);
        return result;
    }


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
