package com.study.nobest;

import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.study.baselibrary.base.BaseApplication;
import com.study.baselibrary.network.RetrofitServiceManager;
import com.study.baselibrary.utils.CommonUtil;
import com.study.nobest.network.Api;

import retrofit2.Retrofit;

/**
 * Created by jianshengli on 2018/2/27.
 */

public class DefaultApplication extends BaseApplication {

    private static RetrofitServiceManager mainRetrofitServiceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofitServiceManager();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)//减小内存的开销
                .build();
        Fresco.initialize(this, config);

    }

    private void initRetrofitServiceManager(){
        Retrofit retrofit = getAppComponent().retrofit().newBuilder().baseUrl(Api.APP_MOYAN_DOMAIN).build();
        mainRetrofitServiceManager  = CommonUtil.globalSingleRetrofitServiceManager().resetRetrofit(retrofit);;

    }

    public static RetrofitServiceManager getMainRetrofitServiceManager(){
        if(mainRetrofitServiceManager!=null){
            return mainRetrofitServiceManager;
        }
        return null;
    }

}
