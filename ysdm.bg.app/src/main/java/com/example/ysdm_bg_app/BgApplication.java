package com.example.ysdm_bg_app;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class BgApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
