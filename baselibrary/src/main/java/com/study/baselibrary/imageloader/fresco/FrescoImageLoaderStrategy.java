
package com.study.baselibrary.imageloader.fresco;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.study.baselibrary.imageloader.BaseImageLoaderStrategy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class FrescoImageLoaderStrategy implements BaseImageLoaderStrategy<FrescoImageConfig> {
    private static final String TAG = "FrescoImage";

    @Override
    public void loadImage(Context ctx, FrescoImageConfig config) {
        if (ctx == null) throw new NullPointerException("Context is required");
        if (config == null) throw new NullPointerException("GlideImageConfig is required");
        if (TextUtils.isEmpty(config.getUrl())) throw new NullPointerException("Url is required");

        if(config.getSimpleDraweeView()!=null){
            Uri uri =Uri.parse(config.getUrl());
            if(config.getWidth()>0){
                resizeSimpleDraweeView(config.getSimpleDraweeView(),uri,config.getWidth(),config.getHeight());
            }else {
                //直接加载图片
                loadDraweeView(config.getSimpleDraweeView(),uri);
            }

        }
    }

    /*设置fresco图片的大小，减小内存的开销*/
    private   void resizeSimpleDraweeView (SimpleDraweeView mSimpleDraweeView , Uri uri , int width , int height){
        //创建一个请求对象设置路径和目标size的大小。
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        //建立一个图片请求对象，把request对象设置给控制器对象，把原来的控制器传递进去复用减少内存的开销。
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mSimpleDraweeView.getController())
                .build();
        //给控件设置请求的控制器对象
        mSimpleDraweeView.setController(controller);
        //Log.d(TAG,"resizeSimpleDraweeView:"+width+""+height);
    }

    private   void loadDraweeView (SimpleDraweeView mSimpleDraweeView , Uri uri ){
        mSimpleDraweeView.setImageURI(uri);
    }


    @Override
    public void clear(Context ctx, FrescoImageConfig config) {


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
