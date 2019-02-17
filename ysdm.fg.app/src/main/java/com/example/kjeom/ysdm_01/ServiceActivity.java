package com.example.kjeom.ysdm_01;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    /**
     * Messenger for communicating with the service.
     */
    Messenger mService = null;

    /**
     * Flag indicating whether we have called bind on the service.
     */
    boolean mBound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected()");
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "onServiceDisconnected()");
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };

    public void onE61StartHDCPService(View view) {
        Log.d(TAG, "onE61StartHDCPService()");
        if (mBound) return;
        Intent intent = new Intent();
        //intent.setComponent(new ComponentName("com.example.ysdm_bg_app","com.example.ysdm_bg_app.HDCPService"));
        intent.setAction("com.example.ysdm_bg_app.START_HDCP_SERVICE");
        intent.setPackage("com.example.ysdm_bg_app");
        // Bind to the service
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void onE62ClearPopup(View view) {
        Log.d(TAG, "onE62ClearPopup()");
        if (!mBound) return;
        // Unbind from the service
        if (mBound) {
            Log.d(TAG, "unbindService()");
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void onE63ClearPopup(View view) {
        Log.d(TAG, "onE63ClearPopup()");
        if (requestCustomPermission()) {
            Log.d(TAG, "has permission");
            if (mBound) {
                Log.d(TAG, "unbindService()");
                unbindService(mConnection);
                mBound = false;
            }
        }
    }

    private boolean requestCustomPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,"com.humaxdigital.test.ACCESS_ONLY_MY_PERMISSION" );

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission granted.");
            return true;
        } else {
            Log.d(TAG, "No permission.");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "com.humaxdigital.test.ACCESS_ONLY_MY_PERMISSION")) {
                Toast.makeText(this, "Need custom permission acceptance", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"com.humaxdigital.test.ACCESS_ONLY_MY_PERMISSION"}, 1);
            }
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Permission allowed.");
                        Toast.makeText(this, "Permission allowed.", Toast.LENGTH_LONG).show();
                    }
                    else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Log.d(TAG, "Permission denied.");
                        Toast.makeText(this, "Permission denied.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d(TAG, "Permission not allowed.");
                    Toast.makeText(this, "Permission not allowed.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}