/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.study.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.study.baselibrary.base.delegate.AppDelegate;
import com.study.baselibrary.di.component.AppComponent;


/**
 * ================================================
 * 本框架由 MVP + Dagger2 + Retrofit + RxJava + Androideventbus + Butterknife 组成
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki">请配合官方 Wiki 文档学习本框架</a>
 * Created by JessYan on 22/03/2016
 * Contact with <mailto:jess.yan.effort@gmail.com>
 * Follow me on <https://github.com/JessYanCoding>
 * ================================================
 *
 * BaseApplication 和  AppLifecycles 相互依赖。
 *
 * AppLifecycles :扩展了Application的一部分功能，采用组合模式把Application的一部分功能放入到了
 * AppLifecycles中。
 *
 * AppLifecycles对象主要是定义了和Application对象一样的生命周期的方法。
 *
 *
 * 简单理解：把自己和自己的生命周期的方法放入到另外一个和自己长的像的类。
 */
public class BaseApplication extends Application implements IApplication {

    private static Application application;
    private static IApplication iApplication;

    public static Application getInstance() {
        if (application != null) {
            return application;
        }
        return null;
    }

    public static IApplication getiApplication() {
        if (iApplication != null) {
            return iApplication;
        }
        return null;
    }

    //通过组装扩展Application的功能。
    //在代理对象中既可以得到Application的生命周期也可以拿到真实的Application对象，所以就可以扩展Application的功能。
    private AppDelegate mAppDelegate;

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mAppDelegate = new AppDelegate();
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        iApplication=this;
        this.mAppDelegate.onCreate(this);
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }


    public AppComponent getAppComponent() {
        return mAppDelegate.getAppComponent();
    }

}
