package com.study.baselibrary.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.baselibrary.mvp.IPresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2018/2/27 0027.
 * 每一个Fragment都需要一个Presenter对象
 */

public abstract class BaseFragment<P extends IPresenter> extends Fragment {
    //通过dragger2注入
    @Inject
    protected P presenter;
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //避免每次都进行重新绘制view的过程
        if (rootView == null) {
            rootView = initView(inflater, container, savedInstanceState);
            initData(savedInstanceState);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;

    }


    protected abstract View  initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract  void initData(Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.destroy();
        }
       if(rootView!=null){
           rootView=null;
       }

    }
}
