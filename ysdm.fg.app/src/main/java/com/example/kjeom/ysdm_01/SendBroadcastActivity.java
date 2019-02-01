package com.example.kjeom.ysdm_01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class SendBroadcastActivity extends AppCompatActivity {
    private static final String TAG = SendBroadcastActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onE41PrintAll(View view) {
        Log.d(TAG, "onE41PrintAll()");
    }

    public void onE42SendToBG(View view) {
        Log.d(TAG, "onE42SendToBG()");
    }
}
