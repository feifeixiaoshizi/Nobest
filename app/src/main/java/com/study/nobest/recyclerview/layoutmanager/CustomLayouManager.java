package com.study.nobest.recyclerview.layoutmanager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jianshengli on 2018/5/6.
 * 1：布局
 * 2：滑动
 * 3：缓存
 *
 * 思路：
 * 1：提前把所有的子view都进行了布局
 * 2：滑动的时候把RecyclerView上的所有的子view都暂时分离下来
 * 3：然后根据偏移量重新布局子View
 * 4：根据位置控制子View是否需要进行回收和利用
 */

public class CustomLayouManager extends RecyclerView.LayoutManager {

    private final String TAG = "CustomLayouManager";

    /*
    * 提供一个默认的LayoutParams，会影响到Item的布局参数对象，在这里的提供的LayoutParams会把item根布局的View的属性进行更改。
    * */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private int offeset = 0;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        layoutChild(recycler, state, 0);
        totalHeight = totalHeight(recycler);
        Log.i(TAG, "onLayoutChildren的方法调用了！");

    }


    private void layoutChild(RecyclerView.Recycler recycler, RecyclerView.State state, int offeset) {
        Log.d(TAG, "offset:" + offeset);
        //先清空RecyclerView上的子View，放入到Recycler中
        detachAndScrapAttachedViews(recycler);
        //获取适配器提供的总数Item（要摆放到RecyclerView上的子View）
        int itemCount = getItemCount();
        if(itemCount<=0){
            return;
        }
        for (int i = 0; i < itemCount; i++) {
            View itemView = recycler.getViewForPosition(i);
            //添加View到RecyclerView
            addView(itemView);
            offeset = layoutChildView(recycler,itemView, i, offeset);

        }

    }

    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }


    private int getVerticalSpace() {
        //计算RecyclerView的可用高度，除去上下Padding值
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }


    private int totalHeight(RecyclerView.Recycler recycler) {
        int itemCount = getItemCount();
        int totalHeight = 0;
        for (int i = 0; i < itemCount; i++) {
            View itemView = recycler.getViewForPosition(i);
            measureChildWithMargins(itemView, 0, 0);
            totalHeight += itemView.getMeasuredHeight();
        }
        return totalHeight;
    }

    int verticalScrollOffset;
    int totalHeight;

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d(TAG, "dy:" + dy);//正数为向上滑动，负数为向下滑动。

        //通过偏移量来实现所有的子view的滑动效果（****）
        int travel = dy;

        //可以滑动的距离就在0-totalHeight - getVerticalSpace()之间
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 调用该方法通知view在y方向上移动指定距离
        //offsetChildrenVertical(-travel);

        layoutChild(recycler, state, -verticalScrollOffset);
        // offsetChildrenVertical(-dy);
        return dy;
    }


    /**
     * @param itemView
     */
    private int layoutChildView(RecyclerView.Recycler recycler,View itemView, int position, int topStart) {
        measureChildWithMargins(itemView, 0, 0);
        int height = itemView.getMeasuredHeight();
        int width = itemView.getMeasuredWidth();
        int left = itemView.getLeft();
        int top = itemView.getTop();
        //  Log.d(TAG, "height:" + height + "width:" + width + "left:" + left + "top:" + top);
        //height:507width:450left:0top:0
        //如果超出了上边界或者超出了下边界则进行回收
        if(topStart+height<=0||topStart>=getVerticalSpace()){
            //回收item
            removeAndRecycleView(itemView,recycler);
            Log.d(TAG, "removeAndRecycleView"+position);
        }else {
            itemView.layout(left, topStart, width, topStart + height);

        }
        return topStart + height;

    }


}
