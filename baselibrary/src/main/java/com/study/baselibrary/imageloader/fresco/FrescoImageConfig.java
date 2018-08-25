
package com.study.baselibrary.imageloader.fresco;

import android.widget.ImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.study.baselibrary.imageloader.ImageConfig;


public class FrescoImageConfig extends ImageConfig {
    private int cacheStrategy;
    private int fallback; //请求 url 为空,则使用此图片作为占位符
    private boolean isClearMemory;//清理内存缓存
    private boolean isClearDiskCache;//清理本地缓存

    public SimpleDraweeView getSimpleDraweeView() {
        return simpleDraweeView;
    }

    //fresco显示图片的控件
    private SimpleDraweeView simpleDraweeView;

    private FrescoImageConfig(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.fallback = builder.fallback;
        this.cacheStrategy = builder.cacheStrategy;
        this.simpleDraweeView = builder.simpleDraweeView;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
        this.width=builder.width;
        this.height=builder.height;
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public int getFallback() {
        return fallback;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private int width;
        private int height;
        private String url;
        private ImageView imageView;
        private SimpleDraweeView simpleDraweeView;
        private int placeholder;
        private int errorPic;
        private int fallback; //请求 url 为空,则使用此图片作为占位符
        private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
        private boolean isClearMemory;//清理内存缓存
        private boolean isClearDiskCache;//清理本地缓存

        private Builder() {
        }

        private Builder(FrescoImageConfig imageConfig) {
            this.width = imageConfig.width;
            this.height = imageConfig.height;
            this.url = imageConfig.url;
            this.imageView = imageConfig.imageView;
            this.simpleDraweeView = imageConfig.simpleDraweeView;
            this.placeholder = imageConfig.placeholder;
            this.errorPic = imageConfig.errorPic;
            this.fallback = imageConfig.fallback; //请求 url 为空,则使用此图片作为占位符
            this.cacheStrategy = imageConfig.cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
            this.isClearMemory = imageConfig.isClearMemory;//清理内存缓存
            this.isClearDiskCache = imageConfig.isClearDiskCache;//清理本地缓存
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder simpleDraweeView(SimpleDraweeView simpleDraweeView) {
            this.simpleDraweeView = simpleDraweeView;
            return this;
        }

        public Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }


        public FrescoImageConfig build() {
            return new FrescoImageConfig(this);
        }
    }
}
