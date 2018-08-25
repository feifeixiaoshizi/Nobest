package com.study.nobest.di.module;

import android.content.Context;
import android.location.LocationManager;

import com.study.nobest.di.annotation.ActivityScope;
import com.study.nobest.mvp.contract.TestContract;
import com.study.nobest.mvp.model.TestModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/6/29.
 */

@Module
public class TestActivityModule {
    TestContract.ITestView view;

    public TestActivityModule(TestContract.ITestView view) {
        this.view = view;
    }
    @Singleton
    @Provides
    public TestContract.ITestView provideView(){
        return view;
    }
    @Singleton
    @Provides
    public TestContract.ITestModel provideModel(){
        return new TestModel();
    }
}