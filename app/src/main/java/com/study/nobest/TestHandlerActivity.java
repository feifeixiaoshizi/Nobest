package com.study.nobest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.study.baselibrary.base.BaseActivity;
import com.study.nobest.thread.MyThread;


public class TestHandlerActivity extends BaseActivity {
    private String TAG = "TestHandlerActivity";
    private TextView testHandler;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            testHandler.setText((String) msg.obj);
            Log.d(TAG,"handlerd的方法执行了！");
        }
    };


    @Override
    protected int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        testHandler = findViewById(R.id.test);
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loadDataThread();
    }


    /*
    * 1: 在Activity中定义了匿名的内部类Handler，匿名的Handler会指向当前Activity
      2：在线程结束后关闭Activity不会造成内存泄露
      3：在线程未结束的情况下，关闭Activity会导致Activity不能正常关闭。
      4：在线程执行完毕后，未执行gc前，Activity依然占据内存空间不能关闭。
      5：在线程执行完毕后，执行gc后，Activity能关闭，不再占据内存空间。
    *
    * */

    MyThread t;
    private void loadDataThread() {
       /* t = new MyThread();
        t.setHandler(handler);
        t.start();*/

    }


    @Override
    protected void onDestroy() {
        try {
            t.interrupt();
            t=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
        Log.d(TAG,"onDestroy！方法调用了！");
    }
}
