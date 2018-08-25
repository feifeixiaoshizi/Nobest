package com.study.nobest.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.study.nobest.recyclerview.viewholder.ViewContainerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianshengli on 2018/4/30.
 *
 * RecyclerView的机制时每次滑动都会先暂时把子View从Recyclerview上分离下来，然后再把新的子View重新绘制上去。（*****）
 *
 * 可以添加任意数量头布局的适配器。
 *
 */

public class WrraperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "WrraperAdapter";
    private Context context;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    //头布局的数量
    private List<View> headerViews = new ArrayList<>();

    private List<View> footerViews = new ArrayList<>();

    //item的类型
    private final static int VIEW_TYPE_HEADER = 1000;
    private final static int VIEW_TYPE_FOOTER = 1001;


    public WrraperAdapter(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter) {
        this.context = context;
        this.mAdapter = mAdapter;
    }

    /**
     * 添加头布局
     * @param view
     */
    public void addHeaderView(View view){
        headerViews.clear();
        headerViews.add(view);
    }


    /**
     * 添加头布局
     * @param view
     */
    public void addFooterView(View view){
        footerViews.clear();
        footerViews.add(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent != null) {
            Log.d(TAG, "parent:" + parent.toString() + "parentName:" + parent.getClass().getSimpleName() + "viewType:" + viewType);
        }

        if (viewType == VIEW_TYPE_HEADER && headerViews.size() > 0) {
           //View headerView = View.inflate(context, R.layout.header_view_container, null);
            return new ViewContainerViewHolder(headerViews.get(0));

        }

        if (viewType == VIEW_TYPE_FOOTER && footerViews.size() > 0) {
            //View footerView = View.inflate(context, R.layout.header_view_container, null);
            return new ViewContainerViewHolder(footerViews.get(0));
        }

        return mAdapter.onCreateViewHolder(parent, viewType);

    }


    /**
     * 主要用于更新Item的内容，每次滑动时所有可见的Item都会调用一次该方法。
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:position:" + position);
        if(position<headerViews.size()){
            if(holder instanceof ViewContainerViewHolder){
                //添加前要确保添加的子View没有父View，否则会报错。
               // ((ViewContainerViewHolder) holder).viewContainer.addView(headerViews.get(position));
            }
            return;
        }


        if(position>=headerViews.size()&&mAdapter.getItemCount()>0&&position<headerViews.size()+mAdapter.getItemCount()){
            mAdapter.onBindViewHolder(holder,position-headerViews.size());
            return;
        }

        if(position>=headerViews.size()+mAdapter.getItemCount()){
            //添加前要确保添加的子View没有父View，否则会报错。
           // ((ViewContainerViewHolder) holder).viewContainer.addView(footerViews.get(position-headerViews.size()-mAdapter.getItemCount()));
            return;

        }

    }


    /**
     * 每次滑动的时候，界面中可见的Item都会执行一遍该方法。
     * @param holder
     */
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d(TAG,"onViewDetachedFromWindow 方法执行了！");
        if(holder instanceof ViewContainerViewHolder){
            //((ViewContainerViewHolder) holder).viewContainer.removeAllViews();
        }


    }



    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int viewType = holder.getItemViewType();
        //头布局或者尾布局
        if(viewType==VIEW_TYPE_HEADER||viewType==VIEW_TYPE_FOOTER){
            setFullSpan(holder);
        }

    }

    /**
     * 设置headerView或者FooterView宽度为占满
     * @param holder
     */
    protected  void setFullSpan(RecyclerView.ViewHolder holder){
        if(holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

        if(holder.itemView.getLayoutParams() instanceof GridLayoutManager.LayoutParams){
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.width=  GridLayoutManager.LayoutParams.MATCH_PARENT;

        }


    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+headerViews.size()+footerViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position<headerViews.size()){
            return VIEW_TYPE_HEADER;
        }
        //如果存在footer
        if(footerViews.size()>0&&position==getItemCount()-1){
            return VIEW_TYPE_FOOTER;
        }

        return mAdapter.getItemViewType(position-headerViews.size());
    }
}
