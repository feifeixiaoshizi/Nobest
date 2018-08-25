package com.study.baselibrary.di.module;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.study.baselibrary.base.BaseApplication;
import com.study.baselibrary.network.CustomNetwokException;
import com.study.baselibrary.network.NetResponse;
import com.study.baselibrary.network.ResponseInterceptor;
import com.study.baselibrary.network.StringConverterFactory;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianshengli on 2018/2/27.
 */

@Module
public class NetworkModule {
    private static  final String TAG="NetworkModule";

    @Singleton
    @Provides
    public Retrofit provideRetrofit (OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())//添加直接返回String的转化器，在Gson转换器前面添加。
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl("http://news.baidu.com")
                .build();

        return retrofit;
    }


    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(){

        //先构建一个默认的OkHttpClient，然后在回炉重造。
        OkHttpClient client = new OkHttpClient();

        //添加一个对响应结果过滤的拦截器
        client= client.newBuilder().addInterceptor(new ResponseInterceptor()).build();
        return client;


    }






}
