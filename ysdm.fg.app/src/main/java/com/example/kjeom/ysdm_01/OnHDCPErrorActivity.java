package com.example.kjeom.ysdm_01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class OnHDCPErrorActivity extends AppCompatActivity {
    private static final String TAG = OnHDCPErrorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_hdcperror);

        Intent intent = getIntent();
        Log.d(TAG, "intent received");
    }
}
