package com.study.nobest.recyclerview.itemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 *万能的recylerview的分隔线
 * 1：在Item的四周留出空白的区域提供绘制的区域（重写该getItemOffsets方法设置ItemView四周要设置的区域的大小）
 * 2：调用Canvas在上绘制内容，在四周的区域上进行绘制分割线内容
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    private final static String TAG = "RecycleViewDivider";
    //画笔对象
    private Paint mPaint;
    //分隔线对象
    private Drawable mDivider;
    //分隔线高度
    private int mDividerHeight = 20;//分割线高度，默认为1px
    //分隔线方向
    private int mOrientation; // 列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};


    public RecycleViewDivider(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        //设置方向
        mOrientation = orientation;
        //根据context得到属性集对象
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //利用属性集对象，拿到属性对应的值
        mDivider = a.getDrawable(0);
        //回收
        a.recycle();
    }


    public RecycleViewDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        //Intrinsic:本能 天性
        mDividerHeight = mDivider.getIntrinsicHeight();//拿到控件本来高度
    }


    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);
        //设置分隔线高度
        mDividerHeight = dividerHeight;
        //创建画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔的颜色
        mPaint.setColor(dividerColor);
        //设置画笔的样式
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Log.d(TAG,"viewClass:"+view.getClass().getSimpleName());
        Log.d(TAG,"viewHeight:"+view.getWidth()+"viewHeight:"+view.getHeight());

       int layoutPosition =  parent.getChildLayoutPosition(view);
       int adapterPosition = parent.getChildAdapterPosition(view);
        Log.d(TAG,"layoutPosition:"+layoutPosition+"adapterPosition:"+adapterPosition);
        if(layoutPosition%3==0){
            //给分隔线的腾出空间来
            outRect.set(0, mDividerHeight/2, mDividerHeight/2, mDividerHeight/2);
            return;
        }

        if(layoutPosition%3==2){
            //给分隔线的腾出空间来
            outRect.set(mDividerHeight/2, mDividerHeight/2, 0, mDividerHeight/2);
            return;

        }

        outRect.set(mDividerHeight/2, mDividerHeight/2, mDividerHeight/2, mDividerHeight/2);




        /*
        * 该方法主要是为了装饰ItemView，在ItemView的四周留出空白的区域。以便可以利用画布在上面绘制分割线的内容。只要留出了画布的可以绘制的内容区域，
        * 就可以绘制任意的内容作为分割线了。
        * 四个参数的含义：分别表示为四个边所要留出的空白空间为多大，根据四个参数的大小相当于在ItemView的四周包裹了一层画布，以便在ItemView的四周绘制内容。
        * */
        //outRect.set(mDividerHeight, mDividerHeight, mDividerHeight, mDividerHeight);
    }


    //不用onDraw来绘制分割线，仅仅留出来空白的区域，使用背景色来填充空白的区域。（****）
   /* @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }*/


    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }



    private void drawHorizontal (Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}
