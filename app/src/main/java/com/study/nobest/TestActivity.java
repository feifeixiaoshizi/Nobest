package com.study.nobest;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.study.baselibrary.base.BaseActivity;
import com.study.baselibrary.base.BaseApplication;
import com.study.baselibrary.imageloader.BaseImageLoaderStrategy;
import com.study.baselibrary.imageloader.ImageLoader;
import com.study.baselibrary.imageloader.fresco.FrescoImageConfig;
import com.study.baselibrary.imageloader.fresco.FrescoImageLoaderStrategy;
import com.study.baselibrary.imageloader.glide.GlideImageLoaderStrategy;
import com.study.baselibrary.imageloader.glide.GlideImageConfig;
import com.study.baselibrary.utils.CommonUtil;
import com.study.nobest.di.component.DaggerTestActivityComponent;
import com.study.nobest.di.module.TestActivityModule;
import com.study.nobest.mvp.contract.TestContract;
import com.study.nobest.mvp.presenter.TestPresenter;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.ITestView {
    private String TAG = "TestActivity";
    private TextView test;
    private ImageView iv;
    //fresco显示图片的控件
    private SimpleDraweeView img;

    @Override
    protected int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        test = findViewById(R.id.test);
        iv = findViewById(R.id.iv);
        img = findViewById(R.id.img);
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //initPresenter();

        //通过Dagger注入Presenter对象
        DaggerTestActivityComponent.builder().testActivityModule(new TestActivityModule(this)).build().inject(this);
        presenter.start();

        //imageLoader = new ImageLoader(baseImageLoaderStrategy);
        //CommonUtil.getImageLoader().loadImage(this, GlideImageConfig.builder().imageView(iv).url("http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg").build());

        CommonUtil.getImageLoader().setLoadImgStrategy(new FrescoImageLoaderStrategy()).loadImage(this, FrescoImageConfig.builder().width(100).height(100).simpleDraweeView(img).url("http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg").build());


    }



    //手动初始化Presenter
    private void initPresenter(){
        presenter = new TestPresenter();
        presenter.setView(this);

    }

    @Override
    public void setViewContent(String content) {
        if(test!=null){
            test.setText(content);
        }

    }

    @Override
    public void destroy() {
        finish();

    }
}
