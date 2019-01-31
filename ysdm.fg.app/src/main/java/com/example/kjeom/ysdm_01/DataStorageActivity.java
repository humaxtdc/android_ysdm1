package com.example.kjeom.ysdm_01;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.e32contract.E32Contract;

public class DataStorageActivity extends AppCompatActivity {
    private static final String TAG = DataStorageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);

        initE31();
        initE32();
    }

    /*
     * ================ E3.1 ========================
     */
    SharedPreferences mSp;
    EditText mSpEditText;

    private void initE31() {
        mSp = getSharedPreferences("E31SharedPreference", Activity.MODE_PRIVATE);
        mSpEditText = (EditText) findViewById(R.id.editText_3_shared_preference);
        String data = mSp.getString("E31Data", "");
        Log.d(TAG, "read data = " + data);
        mSpEditText.setText(data);
    }

    public void onE31Save(android.view.View view) {
        String data = mSpEditText.getText().toString();
        SharedPreferences.Editor editor = mSp.edit();
        Log.d(TAG, "save data = " + data);
        editor.putString("E31Data", data);
        editor.commit();
    }

    /*
     * ================ E3.2, 3.3 ========================
     */
    ContentResolver resolver;
    ContentObserver observer = new ContentObserver(new Handler()) {
        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "onChange(" + selfChange + ")");
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.d(TAG, "onChange(" + selfChange + ", " + uri.toString() + ")");
        }
    };

    private void initE32() {
        resolver = getContentResolver();
        resolver.registerContentObserver(E32Contract.URI_E32, true, observer);
    }

    public void onE32Save(android.view.View view) {
        Log.d(TAG, "onE32Save() {");
        ContentValues values = new ContentValues();
        TextView viewAge = (TextView) findViewById(R.id.editText_3_age);
        TextView viewName = (TextView) findViewById(R.id.editText_3_name);
        values.put(E32Contract.E32Columns.NAME, viewName.getText().toString());
        values.put(E32Contract.E32Columns.AGE, viewAge.getText().toString());
        resolver.insert(E32Contract.URI_E32, values);
        Log.d(TAG, "onE32Save() }");
    }

    public void onE33PrintAll(android.view.View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onE33PrintAll() {");
                Cursor c = resolver.query(E32Contract.URI_E32, null, null, null, null);

                if (c == null) {
                    Log.d(TAG, "onE33PrintAll() cursor is null. }");
                    return;
                }
                while (c.moveToNext()) {
                    String strId = c.getString(c.getColumnIndex(E32Contract.E32Columns._ID));
                    String strAge = c.getString(c.getColumnIndex(E32Contract.E32Columns.AGE));
                    String strName = c.getString(c.getColumnIndex(E32Contract.E32Columns.NAME));
                    Log.d(TAG, "item (" + strId + ", " + strAge + ", " + strName + ")");
                }
                Log.d(TAG, "onE33PrintAll() }");
            }
        }).start();
    }
}