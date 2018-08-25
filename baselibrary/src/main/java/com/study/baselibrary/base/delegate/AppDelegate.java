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
package com.study.baselibrary.base.delegate;

import android.app.Application;
import android.content.Context;


import com.study.baselibrary.base.IApplication;
import com.study.baselibrary.di.component.AppComponent;
import com.study.baselibrary.di.component.DaggerAppComponent;
import com.study.baselibrary.di.module.AppModule;

/**
 * ================================================
 * AppDelegate 可以代理 Application 的生命周期,在对应的生命周期,执行对应的逻辑,因为 Java 只能单继承
 * 所以当遇到某些三方库需要继承于它的 Application 的时候,就只有自定义 Application 并继承于三方库的 Application
 * 这时就不用再继承 BaseApplication,只用在自定义Application中对应的生命周期调用AppDelegate对应的方法
 * (Application一定要实现APP接口),框架就能照常运行
 * * Application的代理类 ：定义了Application中一样的生命周期方法。
 * 在BaseApplication中会封装该对象，并调用对应的生命周期的方法。
 *
 * 解决单继承：使用组装的方式来代替继承要扩展的功能。
 *
 *
 * 在代理对象中既可以得到Application的生命周期也可以拿到真实的Application对象，所以就可以扩展Application的功能。
 */
public class AppDelegate implements IApplication {
    //Application对象
    private Application mApplication;
    //AppComponent对象提供Dragger2的依赖注入功能。
    private AppComponent mAppComponent;
    private Context base;

    public AppDelegate() {

    }


    public void attachBaseContext(Context base) {
       this.base=base;
    }


    public void onCreate(Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(mApplication))//提供application
                .build();
        mAppComponent.inject(this);




    }

    public void onTerminate(Application application) {

    }


    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }



}

