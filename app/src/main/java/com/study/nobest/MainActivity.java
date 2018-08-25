package com.study.nobest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.study.nobest.recyclerview.test.TestRecyclerViewActivity;
import com.study.nobest.recyclerview.test.TestRecyclerViewLayoutManagerActivity;
import com.study.nobest.recyclerview.test.TestRecyclerViewSnapHelperActivity;
import com.study.nobest.recyclerview.test.TestRecyclerViewTochHelperActivity;
import com.study.nobest.rxjava.TestRxjava2Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    TextView test_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setOnClickListener(this);
        tv.setText(stringFromJNI());

        TextView test_event = (TextView) findViewById(R.id.test_event);
        test_event.setOnClickListener(this);





    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View v) {
        Intent intent =null;
        switch (v.getId()){
            case R.id.test_event:
                 intent = new Intent(MainActivity.this,TestRecyclerViewTochHelperActivity.class);
                startActivity(intent);
                break;
            case R.id.sample_text:
                 intent = new Intent(MainActivity.this,TestRxjava2Activity.class);
                startActivity(intent);
                break;
        }
    }
}
