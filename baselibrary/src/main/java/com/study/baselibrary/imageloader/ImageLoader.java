
package com.study.baselibrary.imageloader;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 只能包含一个带@Inject的构造方法
 Error:(37, 12) ??: Types may only contain one @Inject constructor.
 Error:(32, 12) ??: Types may only contain one @Inject constructor.
 *
 */
@Singleton
public final class ImageLoader {
    private String TAG="ImageLoader";
    private BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader(BaseImageLoaderStrategy strategy) {
        setLoadImgStrategy(strategy);
    }


    public ImageLoader() {

    }


    /**
     * 加载图片
     *
     * @param context
     * @param config
     */
    public  void loadImage(Context context, ImageConfig config) {
       Class clas=  mStrategy.getImageConfigClass();
        Log.d(TAG,"class"+clas+"name:"+clas.getName());
        //必须保证参数config是当前策略所支持的图片配置类型
        if(mStrategy.getImageConfigClass()!=null&&mStrategy.getImageConfigClass().isInstance(config)){
            this.mStrategy.loadImage(context, config);
        }else {
            throw  new RuntimeException("当前策略对象所需的ImageConfig配置和当前方法中传递的ImageConfig config参数类型不匹配");
        }



    }

    /**
     * 停止加载
     *
     * @param context
     * @param config
     */
    public void clear(Context context, ImageConfig config) {
        //必须保证参数config是当前策略所支持的图片配置类型
        if(mStrategy.getImageConfigClass()!=null&&mStrategy.getImageConfigClass().isInstance(config)){
            this.mStrategy.clear(context, config);
        }else {
            throw  new RuntimeException("当前策略对象所需的ImageConfig配置和当前方法中传递的ImageConfig config参数类型不匹配");
        }
    }

    /**
     * @param strategy
     */
    public ImageLoader setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
        return this;
    }

    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }
}
