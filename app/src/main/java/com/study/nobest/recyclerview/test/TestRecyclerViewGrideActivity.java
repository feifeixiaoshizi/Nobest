package com.study.nobest.recyclerview.test;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.study.baselibrary.base.BaseActivity;
import com.study.nobest.R;
import com.study.nobest.ResultData;
import com.study.nobest.recyclerview.adapter.MyAdapter;
import com.study.nobest.di.component.DaggerTestRecyclerViewComponent;
import com.study.nobest.di.module.TestRecyclerViewModule;
import com.study.nobest.mvp.contract.TestRecyclerViewContract;
import com.study.nobest.mvp.model.entity.PoemListInfo;
import com.study.nobest.mvp.presenter.TestRecyclerViewPresenter;
import com.study.nobest.recyclerview.itemdecoration.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27 0027.
 *
 * GridLayoutManager设置列数为2时候：
 *
 * 1:展示一种类型的Item时候，没有问题，每行的高度是一致的，宽度是两个Item进行平分。
 *
 * 2:展示不同类型的Item的时候，如果item的高度不一致在同一行，强行设置为同样的高度，高度值取两者中较大的那个高度。
 * 如果一行连个Item的样式一致，而下一行的样式虽然于上一行的样式不一致但是两个样式是一致的也是可以的。
 *
 * 3：行和行之间的高度可以不一致，但是一行内两列的高度必须是一致的，且不一致的时候取较大的值作为行高。
 *
 *
 *
 *
 */

public class TestRecyclerViewGrideActivity extends BaseActivity<TestRecyclerViewPresenter> implements TestRecyclerViewContract.View {
    private final static String TAG = "TestRecyclerViewGride";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<PoemListInfo.ListDataEntity> mDatas = new ArrayList<>();

    //private WrraperAdapter wrraperAdapter;
    private MyAdapter myAdapter;
    //private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private int totalCount;

    private View headerView;


    @Override
    protected int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recyclerview);
        swipeRefreshLayout = findViewById(R.id.srf);
        recyclerView = findViewById(R.id.rv);

        initRefreshView();
        initHeaderView();
        initRecyclerView();


        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        DaggerTestRecyclerViewComponent.builder().testRecyclerViewModule(new TestRecyclerViewModule(this)).build().inject(this);
        presenter.start();
        presenter.loadData(getRequestArguments(1));
    }


    private void initHeaderView() {
        headerView = View.inflate(this, R.layout.header_view, null);
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
        //linearLayoutManager = new LinearLayoutManager(this);
       // recyclerView.setLayoutManager(linearLayoutManager);

        gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        myAdapter = new MyAdapter(mDatas, this);
        //wrraperAdapter = new WrraperAdapter(this, myAdapter);
       // wrraperAdapter.addHeaderView(headerView);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /*
                newState:1
05-06 13:09:27.058 25941-25941/com.study.nobest D/TestRecyclerViewGride: itemTotalCount::10getChildCount:4
05-06 13:09:27.197 25941-25941/com.study.nobest D/TestRecyclerViewGride: newState:2
05-06 13:09:27.198 25941-25941/com.study.nobest D/TestRecyclerViewGride: itemTotalCount::10getChildCount:4
05-06 13:09:27.201 25941-25941/com.study.nobest D/TestRecyclerViewGride: newState:0
05-06 13:09:27.201 25941-25941/com.study.nobest D/TestRecyclerViewGride: itemTotalCount::10getChildCount:4
根据日志可以分析出来：每次手动触发的滑动都是从1--》2--》0的变化 0：表示空闲状态  recyclerView.getChildCount()：表示RecyclerView 身上现有的子View的数量，也就是一屏可以展示的数量。
                 */
                Log.d(TAG, "newState:" + newState);
                Log.d(TAG, "itemTotalCount::" + gridLayoutManager.getItemCount()+"getChildCount:"+recyclerView.getChildCount());

                int lastPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastPosition >= mDatas.size() - 1) {
                    int pageId = mDatas.size() / 10 + 1;
                    Log.d(TAG, "pageId:" + pageId);
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
            totalCount = ((PoemListInfo) resultData.data).getTotalCount();
            mDatas.clear();
            mDatas.addAll(((PoemListInfo) resultData.data).getListData());
            myAdapter.notifyDataSetChanged();

            //View headerView1 = View.inflate(this,R.layout.header_view1,null);
            //wrraperAdapter.addHeaderView(headerView1);
            //wrraperAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void freshLoadMore(ResultData resultData) {
        if (resultData.data instanceof PoemListInfo) {
            totalCount = ((PoemListInfo) resultData.data).getTotalCount();
            mDatas.addAll(((PoemListInfo) resultData.data).getListData());
            myAdapter.notifyDataSetChanged();
            //wrraperAdapter.notifyDataSetChanged();
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
