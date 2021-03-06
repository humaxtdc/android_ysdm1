package com.example.ysdm_bg_app;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HDCPService extends Service {
    private static final String TAG = HDCPService.class.getSimpleName();

    public HDCPService() {
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

    class HDCPMonitor implements Runnable {
        private boolean bRunning = false;
        int cnt = 0;
        String prevStatus = "false";

        @Override
        public void run() {
            if (bRunning == false) {
                Log.d(TAG, "not running");
                return;
            }
            String curStatus = getSystemProperty("humax.hdcp.test");
            Log.d(TAG, String.valueOf(cnt++) + " humax.hdcp.test = [" + curStatus + "]");
            if (!curStatus.equals(prevStatus)) {
                if (curStatus.equals("true")) {
                    sendHDCPError();
                }
                prevStatus = curStatus;
            }
            mHDCPServiceHandler.postDelayed(mHDCPMonitor, 500);
        }

        public void start() {
            if (bRunning == true) {
                Log.d(TAG, "HDCP monitoring is already started.");
                return;
            }
            bRunning = true;
            Log.d(TAG, "startMonitoring");
            run();
        }

        public void stop() {
            Log.d(TAG, "stop()");
            bRunning = false;
        }
    }

    private void sendHDCPError() {
        Log.d(TAG, "sendHDCPError()");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.kjeom.ysdm_01", "com.example.kjeom.ysdm_01.OnHDCPErrorActivity"));
        //intent.setAction("com.example.kjeom.ysdm_01.OnHDCPErrorActivity");
        //intent.setPackage("com.example.kjeom.ysdm_01");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private Handler mHDCPServiceHandler = new Handler();
    private HDCPMonitor mHDCPMonitor = new HDCPMonitor();

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mHDCPMonitor.start();
                    break;
                case 2:
                    mHDCPMonitor.stop();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        mHDCPMonitor.start();
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        mHDCPMonitor.stop();
    }
}
