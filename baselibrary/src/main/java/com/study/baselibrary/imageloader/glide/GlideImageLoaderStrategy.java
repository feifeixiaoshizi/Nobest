
package com.study.baselibrary.imageloader.glide;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.study.baselibrary.imageloader.BaseImageLoaderStrategy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {
    private  String TAG = "GlideImage";

    @Override
    public void loadImage(Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new NullPointerException("Context is required");
        if (config == null) throw new NullPointerException("GlideImageConfig is required");
        if (TextUtils.isEmpty(config.getUrl())) throw new NullPointerException("Url is required");
        Glide.with(ctx).load(config.getUrl()).into(config.getImageView());

    }

    @Override
    public void clear(final Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new NullPointerException("Context is required");
        if (config == null) throw new NullPointerException("GlideImageConfig is required");

        if (config.isClearDiskCache()) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearDiskCache();
                        }
                    });
        }

        if (config.isClearMemory()) {//清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearMemory();
                        }
                    });
        }

    }

    @Override
    public Class getImageConfigClass() {
        //获取实现的父泛型接口
        Type[] types = this.getClass().getGenericInterfaces();
        Type GenericInterface=null ;
        if(types.length>0){
            //取第一个泛型接口
            GenericInterface = types[0];
            if(GenericInterface instanceof ParameterizedType){
                //泛型接口的这是参数
                Type[] typeArguments= ((ParameterizedType) GenericInterface).getActualTypeArguments();
                //泛型参数数组size大于0
                if(typeArguments.length>0&&typeArguments[0] instanceof Class){
                    return (Class) typeArguments[0];
                }

            }
        }
         // Log.d(TAG,"type:"+type.toString());
        //com.study.baselibrary.imageloader.BaseImageLoaderStrategy<com.study.baselibrary.imageloader.glide.GlideImageConfig>
        return null;
    }


}
