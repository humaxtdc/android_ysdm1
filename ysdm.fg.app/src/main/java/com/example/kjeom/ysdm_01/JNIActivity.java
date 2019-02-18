package com.example.kjeom.ysdm_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class JNIActivity extends AppCompatActivity {
    private static final String TAG = JNIActivity.class.getSimpleName();

    static {
        System.loadLibrary("jni-etude");
    }

    public native String N1();
    public native void N2();

    static TextView mTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        mTv2 = (TextView) findViewById(R.id.textView26);
    }

    public void onE71JavaNative(View view) {
        Log.d(TAG, "onE71JavaNative()");
        String fromN = N1();
        Log.d(TAG, "String: " + fromN);
        TextView tv = (TextView) findViewById(R.id.textView24);
        tv.setText(fromN);
    }

    public void onE72JavaNativeJava(View view) {
        Log.d(TAG, "onE72JavaNativeJava()");
        N2();
    }

    // call by native
    public static void J1(String str) {
        Log.d(TAG, "J1()");
        Log.d(TAG, "String: " + str);
        mTv2.setText(str);
    }
}
