/**
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.study.baselibrary.di.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.study.baselibrary.base.delegate.AppDelegate;
import com.study.baselibrary.di.module.AppModule;
import com.study.baselibrary.di.module.ImageModule;
import com.study.baselibrary.di.module.NetworkModule;
import com.study.baselibrary.imageloader.ImageLoader;
import com.study.baselibrary.network.RetrofitServiceManager;

import java.io.File;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ================================================
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.2">AppComponent wiki 官方文档</a>
 * Created by JessYan on 8/4/2016
 * Contact with <mailto:jess.yan.effort@gmail.com>
 * Follow me on <https://github.com/JessYanCoding>
 * ================================================
 *
 * 源码解析：
 *
 * 把所有依赖的Module里面的方法都对应生成一个Provider对象，
 * 然后把所有的Provider对象封装到该Component组件中，然后
 * 在具体实现方法里调用具体的Provider提供对象。
 *例如：
 * public final class DaggerAppComponent implements AppComponent {

//封装了额所有的Provider对象
private Provider<Application> provideApplicationProvider;

.......

private Provider<BaseImageLoaderStrategy> provideImageLoaderStrategyProvider;

private Provider<ImageLoader> imageLoaderProvider;

private Provider<Map<String, Object>> provideExtrasProvider;

private Provider<ActivityLifecycle> activityLifecycleProvider;

private Provider<ActivityLifecycleForRxLifecycle> activityLifecycleForRxLifecycleProvider;

private MembersInjector<AppDelegate> appDelegateMembersInjector;

 //具体提供Application方法的实现。
 @Override
 public Application application() {
 return provideApplicationProvider.get();
 }


 提供全局的一些单例的对象：比如AppManager对象。



本类的作用：

    1：把基本的module提供的依赖对象整合到一起。
  * 2：注入到BaseApplication。
  * 3：提供抽象方法把依赖对象提供出去，以便把这些单例对象供给其他的Component使用。
  * 4：只在Application中调用一次，保证提供对象唯一性（单例）。
  * 5：把所有的module注入到BaseApplication中，其中有的module可能需要依赖其他的module中提供的对象。
  *    比如：ClientModule需要依赖GlobeConfigModule提供的对象。
 *
 */
@Singleton
@Component(modules = {AppModule.class,NetworkModule.class, ImageModule.class})
public interface AppComponent {
    Application application();

    //用于管理所有 activity
    //AppManager appManager();

    //用于管理网络请求层,以及数据缓存层
    RetrofitServiceManager retrofitServiceManager();

    //RxJava 错误处理管理类
    //RxErrorHandler rxErrorHandler();

    //图片管理器,用于加载图片的管理类,默认使用 Glide ,使用策略模式,可在运行时替换框架
    ImageLoader imageLoader();

    OkHttpClient okHttpClient();

    Retrofit retrofit();

    //gson
    Gson gson();

    //缓存文件根目录(RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下),应该将所有缓存都放到这个根目录下,便于管理和清理,可在 GlobalConfigModule 里配置
    //File cacheFile();


    void inject(AppDelegate delegate);
}
