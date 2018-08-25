package com.study.baselibrary.utils;

import com.google.gson.Gson;
import com.study.baselibrary.base.BaseApplication;
import com.study.baselibrary.imageloader.ImageLoader;
import com.study.baselibrary.network.RetrofitServiceManager;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class CommonUtil {
    public static ImageLoader getImageLoader(){
        return  BaseApplication.getiApplication().getAppComponent().imageLoader();
    }

    public static Retrofit retrofit(){
        return BaseApplication.getiApplication().getAppComponent().retrofit();
    }



    public static RetrofitServiceManager globalSingleRetrofitServiceManager(){
        return BaseApplication.getiApplication().getAppComponent().retrofitServiceManager();
    }

    public static RetrofitServiceManager getNewRetrofitServiceManager(Retrofit retrofit){
        return new RetrofitServiceManager(retrofit);
    }


    public static Gson gson(){
        return BaseApplication.getiApplication().getAppComponent().gson();
    }

}
