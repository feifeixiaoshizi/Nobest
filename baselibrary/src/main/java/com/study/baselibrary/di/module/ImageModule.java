package com.study.baselibrary.di.module;

import com.study.baselibrary.imageloader.BaseImageLoaderStrategy;
import com.study.baselibrary.imageloader.ImageLoader;
import com.study.baselibrary.imageloader.glide.GlideImageLoaderStrategy;
import com.study.baselibrary.network.ResponseInterceptor;
import com.study.baselibrary.network.StringConverterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianshengli on 2018/2/27.
 */

@Module
public class ImageModule {
    private static  final String TAG="ImageModule";

    @Singleton
    @Provides
    public BaseImageLoaderStrategy provideImageLoaderStrategy (){
       return  new GlideImageLoaderStrategy();
    }



}
