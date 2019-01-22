package com.example.kjeom.ysdm_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToActivity1(android.view.View view) {
        this.view = view;
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}
