package com.study.nobest.recyclerview.test;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.study.baselibrary.base.BaseActivity;
import com.study.nobest.R;
import com.study.nobest.ResultData;
import com.study.nobest.di.component.DaggerTestRecyclerViewComponent;
import com.study.nobest.di.module.TestRecyclerViewModule;
import com.study.nobest.mvp.contract.TestRecyclerViewContract;
import com.study.nobest.mvp.model.entity.PoemListInfo;
import com.study.nobest.mvp.presenter.TestRecyclerViewPresenter;
import com.study.nobest.recyclerview.adapter.MyAdapter;
import com.study.nobest.recyclerview.adapter.WrraperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27 0027.
 *
 * 1：RecyclerView 会影响到ItemView的根View的宽和高的属性，会设置为wrap_content的属性。
 * 2：在Adapter的onBindViewHolder的方法得到的ItemView的宽和高可能为0，viewClassName:LinearLayoutviewHeight:0viewHeight:0
 * 3：设置分割线的原理是在ItemView的四周留下空白的区域，以便可以通过画布在上绘制分割线内容。
 * 4：在注意不同的LayoutManager可能会对分割线造成影响。（线性布局管理器和表格布局管理器）
 * 5：RecyclerView的getChildCount()得到的是当前RecyclcerView上一屏可展示的View个数。（****）
 *
 */

public class TestRecyclerViewSnapHelperActivity extends BaseActivity<TestRecyclerViewPresenter> implements TestRecyclerViewContract.View {
    private final static String TAG = "TestRecyclerView";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<PoemListInfo.ListDataEntity> mDatas = new ArrayList<>();

    private WrraperAdapter wrraperAdapter;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager;


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
       linearLayoutManager = new LinearLayoutManager(this);
       linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        myAdapter = new MyAdapter(mDatas, this);
        wrraperAdapter = new WrraperAdapter(this, myAdapter);
        recyclerView.setAdapter(wrraperAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "newState:" + newState);int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastPosition >= mDatas.size() - 1) {
                    int pageId = mDatas.size() / 10 + 1;
                    Log.d(TAG, "pageId:" + pageId);
                    presenter.loadMoreData(getRequestArguments(pageId));
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int childCount = recyclerView.getChildCount();
                int itemCount = linearLayoutManager.getItemCount();
                int positions = linearLayoutManager.findLastVisibleItemPosition();



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
            //myAdapter.notifyDataSetChanged();
            View headerView1 = View.inflate(this,R.layout.header_view1,null);
            wrraperAdapter.addHeaderView(headerView1);
            wrraperAdapter.addFooterView(headerView);
            wrraperAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void freshLoadMore(ResultData resultData) {
        if (resultData.data instanceof PoemListInfo) {
            totalCount = ((PoemListInfo) resultData.data).getTotalCount();
            mDatas.addAll(((PoemListInfo) resultData.data).getListData());
            // myAdapter.notifyDataSetChanged();
            wrraperAdapter.notifyDataSetChanged();
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
