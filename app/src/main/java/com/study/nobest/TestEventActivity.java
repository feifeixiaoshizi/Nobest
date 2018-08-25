package com.study.nobest;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.study.baselibrary.base.BaseActivity;
import com.study.baselibrary.imageloader.fresco.FrescoImageConfig;
import com.study.baselibrary.imageloader.fresco.FrescoImageLoaderStrategy;
import com.study.baselibrary.utils.CommonUtil;
import com.study.nobest.di.component.DaggerTestActivityComponent;
import com.study.nobest.di.module.TestActivityModule;
import com.study.nobest.mvp.contract.TestContract;
import com.study.nobest.mvp.presenter.TestPresenter;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class TestEventActivity extends BaseActivity  {
    private String TAG = "TestActivity";

    @Override
    protected int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_event);
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //initPresenter();

    }




}
