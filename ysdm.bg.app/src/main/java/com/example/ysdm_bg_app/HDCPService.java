package com.example.ysdm_bg_app;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class HDCPService extends Service {
    private static final String TAG = HDCPService.class.getSimpleName();

    public HDCPService() {
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "start handleMessage()");
            Toast.makeText(getApplicationContext(), "handleMessage()", Toast.LENGTH_LONG).show();

            String prop = getSystemProperty("humax.hdcp.test");
            Log.d(TAG, "humax.hdcp.text = " + prop);

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            Log.d(TAG, "stop handleMessage()");
            stopSelf(msg.arg1);
        }

        private String getSystemProperty(String propString) {
            String prop = "";
            try {
                Process proc = Runtime.getRuntime().exec(new String[]{"/system/bin/getprop", propString});
                BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                prop = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prop;
        }
    }

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    boolean bRunning = false;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
    }
}
