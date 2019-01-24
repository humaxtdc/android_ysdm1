package com.example.kjeom.ysdm_01;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import static android.content.ContentValues.TAG;

public class DataStorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);

        initE31();
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

    public void onE32Save(android.view.View view) {
    }

    public void onE33PrintAll(android.view.View view) {
    }
}