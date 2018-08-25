
package com.study.baselibrary.imageloader;

import android.content.Context;

/**
 * 1：加载
 * 2：取消加载

 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    /**
     * 加载图片
     *
     * @param ctx
     * @param config
     */
    void loadImage(Context ctx, T config);

    /**
     * 停止加载
     * @param ctx
     * @param config
     */
    void clear(Context ctx, T config);

    /**
     * 获取配置对象类型
     */
    Class getImageConfigClass();


}
