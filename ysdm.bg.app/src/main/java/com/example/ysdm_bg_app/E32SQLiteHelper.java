package com.example.ysdm_bg_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class E32SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = E32SQLiteHelper.class.getSimpleName();

    public E32SQLiteHelper(Context context) {
        super(context, "E32.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE table_e32 (_ID INTEGER PRIMARY KEY AUTOINCREMENT, age INTEGER, name TEXT);");
        Log.d(TAG, "onCreate() table table_e32 is created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
