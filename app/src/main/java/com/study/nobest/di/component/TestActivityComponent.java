package com.study.nobest.di.component;

import com.study.nobest.TestActivity;
import com.study.nobest.di.module.TestActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 1：如果Component上不加注解，但是它所提供的对象是带注解的，那就会报错。
 Error:(14, 1) ??: com.study.nobest.di.component.TestActivityComponent (unscoped) may not reference scoped bindings:
 @Singleton class com.study.nobest.mvp.presenter.TestPresenter



 2：如果Component上加了注解，但是它所提供的对象带的注解和它不一样就会报错。
 Error:(20, 1) ??: com.study.nobest.di.component.TestActivityComponent scoped with @Singleton may not reference bindings with different scopes:
 @com.study.nobest.di.annotation.ActivityScope @Provides com.study.nobest.mvp.contract.TestContract.ITestModel com.study.nobest.di.module.TestActivityModule.provideModel()


 3：如果Component是带注解的，那么通过他注入的对象，可以不带注解，但是如果要带注解就必须和它带同样的注解。


 *
 *
 */
@Singleton
@Component(modules = TestActivityModule.class)
public interface TestActivityComponent {
    void inject(TestActivity testActivity);

}
