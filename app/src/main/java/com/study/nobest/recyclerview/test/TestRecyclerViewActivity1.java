package com.study.nobest.recyclerview.test;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.study.baselibrary.base.BaseActivity;
import com.study.nobest.R;
import com.study.nobest.ResultData;
import com.study.nobest.recyclerview.adapter.MutilAdapter;
import com.study.nobest.di.component.DaggerTestRecyclerViewComponent;
import com.study.nobest.di.module.TestRecyclerViewModule;
import com.study.nobest.mvp.contract.TestRecyclerViewContract;
import com.study.nobest.mvp.model.entity.PoemListInfo;
import com.study.nobest.mvp.presenter.TestRecyclerViewPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class TestRecyclerViewActivity1 extends BaseActivity<TestRecyclerViewPresenter> implements TestRecyclerViewContract.View {
    private final static  String TAG = "TestRecyclerView";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<PoemListInfo.ListDataEntity> mDatas = new ArrayList<>();
    private MutilAdapter myAdapter;
    //private LinearLayoutManager linearLayoutManager;
   // private GridLayoutManager gridLayoutManager;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int totalCount ;



    @Override
    protected int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recyclerview);
        swipeRefreshLayout = findViewById(R.id.srf);
        recyclerView = findViewById(R.id.rv);
        initRefreshView();
        initRecyclerView();

        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        DaggerTestRecyclerViewComponent.builder().testRecyclerViewModule(new TestRecyclerViewModule(this)).build().inject(this);
        presenter.start();
        presenter.loadData(getRequestArguments(1));
    }

    private void initRefreshView() {
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        //设置下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(getRequestArguments(1));
            }
        });


    }


    private void initRecyclerView() {
       /* linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);*/

       /*gridLayoutManager = new GridLayoutManager(this,2);
       recyclerView.setLayoutManager(gridLayoutManager);*/

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        myAdapter = new MutilAdapter(mDatas, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG,"newState:"+newState);
                //int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
               // int lastPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                //int lastPosition = staggeredGridLayoutManager.findLastCompletelyVisibleItemPosition();
                int lastPosition = 10;
                if(lastPosition>= mDatas.size()-1){
                    int pageId= mDatas.size()/10+1;
                    Log.d(TAG,"pageId:"+pageId);
                    presenter.loadMoreData(getRequestArguments(pageId));
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



    }


    @Override
    public void refresh(ResultData resultData) {
        swipeRefreshLayout.setRefreshing(false);
        if (resultData.data instanceof PoemListInfo) {
            totalCount=((PoemListInfo) resultData.data).getTotalCount();
            mDatas.clear();
            mDatas.addAll(((PoemListInfo) resultData.data).getListData());
            myAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void freshLoadMore(ResultData resultData) {
        if (resultData.data instanceof PoemListInfo) {
            totalCount=((PoemListInfo) resultData.data).getTotalCount();
            mDatas.addAll(((PoemListInfo) resultData.data).getListData());
            myAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void destroy() {

    }

    /**
     * @param pageId 请求的页数
     * @return 请求所需的所有的参数
     */
    private ContentValues getRequestArguments(int pageId) {
        if (pageId < 1) {
            pageId = 1;
        }
        //通知Presenter加载数据
        ContentValues contentValues = new ContentValues();
        contentValues.put("operableSign", "776814BF941B47E3B6776B2EE5A14724");
        contentValues.put("userId", "776814BF941B47E3B6776B2EE5A14724");
        contentValues.put("appType", 1);
        contentValues.put("pageId", pageId);
        contentValues.put("poemType", 2);
        return contentValues;

    }


}
