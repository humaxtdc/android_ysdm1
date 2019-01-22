package com.example.kjeom.ysdm_01;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        String sizeString = String.format("%d x %d", size.x, size.y);

        TextView textViewWindowSize = (TextView)findViewById(R.id.textViewWindowSize);
        textViewWindowSize.setText(sizeString);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        String densityString = String.format("%d dpi", (int)(metrics.density * 160f));

        TextView textViewDensity = (TextView)findViewById(R.id.textDensity);
        textViewDensity.setText(densityString);
    }

}
