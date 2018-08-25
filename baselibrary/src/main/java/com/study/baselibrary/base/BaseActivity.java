package com.study.baselibrary.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.study.baselibrary.mvp.IPresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2018/2/27 0027.
 * 每一个Activity都需要一个Presenter对象
 *
 */

public abstract class BaseActivity<P extends IPresenter> extends FragmentActivity {

    //通过dagger2注入
    @Inject
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResID = initView(savedInstanceState);
        if(layoutResID!=0){
           setContentView(layoutResID);
        }
        initData(savedInstanceState);
    }

    protected abstract int  initView(Bundle savedInstanceState);
    protected abstract  void initData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.destroy();
        }

    }
}
