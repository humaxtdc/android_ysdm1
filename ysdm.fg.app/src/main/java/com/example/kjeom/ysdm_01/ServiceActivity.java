package com.example.kjeom.ysdm_01;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = ServiceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void onE61StartHDCPService(View view) {
        Log.d(TAG, "onE61StartHDCPService()");
        Intent intent = new Intent();
        //intent.setComponent(new ComponentName("com.example.ysdm_bg_app","com.example.ysdm_bg_app.HDCPService"));
        intent.setAction("com.example.ysdm_bg_app.START_HDCP_SERVICE");
        intent.setPackage("com.example.ysdm_bg_app");
        try {
            startService(intent);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Toast.makeText(this /* getApplicationContext() */, "IllegalStateException", Toast.LENGTH_LONG).show();
        }
    }
}
