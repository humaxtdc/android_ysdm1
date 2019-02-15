package com.example.kjeom.ysdm_01;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
}