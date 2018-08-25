/**
 *
 *
 *
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
package com.study.baselibrary.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.baselibrary.network.RetrofitServiceManager;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 提供一些框架必须的实例的 {@link Module}
 * <p>
 * Created by JessYan on 8/4/2016.
 * Contact with <mailto:jess.yan.effort@gmail.com>
 * Follow me on <https://github.com/JessYanCoding>
 * ================================================
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }


    /*
    1：Module里面的每个方法都会生成一个工厂类  例如： AppModule_ProvideGsonFactory implements Factory<Gson>


    2：public interface Factory<T> extends Provider<T> {
      }

      //每个Provider都提供了一个get方法来提供对象
      public interface Provider<T> {
          T get();
     }


    3：每个工厂类都封装了该Module对象
    public final class AppModule_ProvideGsonFactory implements Factory<Gson> {
    private final AppModule module;
    //提供方法的Application参数
    private final Provider<Application> applicationProvider;
    //提供方法参数GsonConfiguration
    private final Provider<AppModule.GsonConfiguration> configurationProvider;


    4：方法中所需的参数是由Provider对象提供的。


    5：Providr 是如何提供？
    Factory继承了Provider，同时封装了Module对象，而Module提供了具体提供实例的方法。

    6:Dragger2的精髓在于每个方法都会对应一个Provider对象，负责提供该方法的返回值对象。

    7：使用Dragger2的方法，其方法参数也必须由Dragger2注入。
    *
    *
    *
    * */
    @Singleton
    @Provides
    public Gson provideGson(Application application) {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }




}
