package com.example.kjeom.ysdm_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private View view;
    private RecyclerView mMainMenu;
    private RecyclerView.Adapter mMainMenuAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToActivity1(android.view.View view) {
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    public void goToActivity2(android.view.View view) {
        Intent intent = new Intent(this, ThreadHandlingActivity.class);
        startActivity(intent);
    }

    public void goToActivity3(android.view.View view) {
        Intent intent = new Intent(this, DataStorageActivity.class);
        startActivity(intent);
    }

    public void goToActivity4(View view) {
        Intent intent = new Intent(this, SendBroadcastActivity.class);
        startActivity(intent);
    }
}
