package com.study.nobest.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by jianshengli on 2018/3/26.
 *
 * 1：Activity关闭后，如果未发生gc，则handler和Activity均存在内存中。依然可以发送消息和处理消息。
 * 2：Activity关闭后，发生gc，则handler和Activity均被回收。就无消息的发送和处理了。
 * 3：Activity开着时候，发生gc，则handler和Activity均存在内存中。依然可以发送消息和处理消息。

 */

public class MyThread extends Thread {
    private String TAG = "MyThread";
    //使用弱引用当只有弱引用指向handler的时候，gc的时候handler可以被回收。如果使用强引用，则不能被回收，如果子线程一直指向，那么handler就一直不能被释放，导致
    //Activity也不能被释放。（******）
   private WeakReference<Handler> handlerWeakReference ;

    public Handler getHandler() {
        if(handlerWeakReference!=null && handlerWeakReference.get()!=null){
            return handlerWeakReference.get();
        }
        return null;
    }

    public void setHandler(Handler handler) {
        handlerWeakReference = new WeakReference<Handler>(handler);
    }





    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(15000);
            //模拟请求数据延时
            Message message = Message.obtain();
            message.obj="handler ";
            if(getHandler()!=null){
                getHandler().sendMessage(message);
            }
            while (true){
                Log.d(TAG,"threadName:"+Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG,"InterruptedException:"+e.toString());
        }
    }
}
