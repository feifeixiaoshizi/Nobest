package com.study.nobest.di.module;

import com.study.nobest.mvp.contract.TestContract;
import com.study.nobest.mvp.contract.TestRecyclerViewContract;
import com.study.nobest.mvp.model.TestModel;
import com.study.nobest.mvp.model.TestRecyclerViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/6/29.
 */

@Module
public class TestRecyclerViewModule {
    TestRecyclerViewContract.View view;

    public TestRecyclerViewModule(TestRecyclerViewContract.View view) {
        this.view = view;
    }
    @Singleton
    @Provides
    public TestRecyclerViewContract.View provideView(){
        return view;
    }
    @Singleton
    @Provides
    public TestRecyclerViewContract.Model provideModel(){
        return new TestRecyclerViewModel();
    }
}