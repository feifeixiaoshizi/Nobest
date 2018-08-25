package com.study.nobest.widgt.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ImageView;

/**
 * Created by jianshengli on 2018/5/3.
 */

public class EventImageView extends ImageView {
    private final  String TAG = "EventImageView";
    VelocityTracker velocityTracker = VelocityTracker.obtain();

    public EventImageView(Context context) {
        super(context);
    }

    public EventImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    int xVelocity;
    int yVelocity;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"event:"+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //初始化
                velocityTracker = VelocityTracker.obtain();
                break;
            case MotionEvent.ACTION_MOVE:
                //追踪
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                xVelocity = (int) velocityTracker.getXVelocity();
                yVelocity = (int) velocityTracker.getYVelocity();

                Log.d(TAG,"x:"+xVelocity+"y:"+yVelocity);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //回收
                velocityTracker.clear();
                velocityTracker.recycle();
                break;
        }
        return true;
    }
}
