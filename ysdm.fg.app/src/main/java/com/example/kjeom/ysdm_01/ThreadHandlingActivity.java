package com.example.kjeom.ysdm_01;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yongin.bora.dkkang.kutils.MyProcess;

import static android.content.ContentValues.TAG;

public class ThreadHandlingActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mBtnStart;
    private MyProcess mMyProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handling);

        mTextView = (TextView) findViewById(R.id.textView_2_4);
        mMyProcess = new MyProcess(mTextView, new MyProcess.OnNeedOptimizeProcess() {
            @Override
            public void onProcess(byte[] inputBuffer) {
                makeYourCode(inputBuffer);
            }
        });

        mBtnStart = (Button) findViewById(R.id.button_2_4_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyProcess.testStart();
            }
        });

        initE2_1();
        initE2_3();
    }

    private void makeYourCode(byte[] inputBuffer) {
        // Do optimize
        byte[] processedBuffer = mMyProcess.process(inputBuffer);
        mMyProcess.sendBuffer(processedBuffer);
    }

    /*
     * ================ E2.1 ========================
     */
    private boolean runStatus = true;
    private List<StringBuffer> mList;   // 수정 E2.1: mList 변수 선언

    private void initE2_1() {   // 수정 E2.1: mList 변수 초기화
        mList = new ArrayList<StringBuffer>();
    }

    private void runAdd() {
        runStatus = true;   // 수정 E2.1: ADD 버튼이 눌리면 runStatus 값을 true로 만들어 준다.

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runStatus) {
                    StringBuffer addItem = new StringBuffer("");
                    synchronized (mList) {  // 수정 E2.1: mList는 여러 thread에서 공유되므로, 동기화 시킨다.
                        mList.add(addItem);
                        for (StringBuffer item : mList) {
                            item.append(1);
                        }
                        Log.d(TAG, "add() -> " + mList.size());
                    }
                }
                Log.d(TAG, "return from runAdd()");
            }
        }).start();
    }

    private void runRemove() {
        runStatus = true;   // 수정 E2.1: ADD 버튼이 눌리면 runStatus 값을 true로 만들어 준다.

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runStatus) {
                    synchronized (mList) {  // 수정 E2.1: mList는 여러 thread에서 공유되므로, 동기화 시킨다.
                        if (mList.size() > 0) {
                            mList.remove(0);
                        }
                        Log.d(TAG, "remove() -> " + mList.size());
                    }
                }
                Log.d(TAG, "return from runRemove()");
            }
        }).start();
    }

    public void onE21Add(android.view.View view) {
        runAdd();
    }

    public void onE21Remove(android.view.View view) {
        runRemove();
    }

    public void onE21Finish(android.view.View view) {
        runStatus = false;
    }

    /*
     * ================ E2.3 ========================
     */
    private TextView mMemStatView;
    private Handler mE23Handler;
    private Runnable mE23MemReporter;

    private void initE2_3() {
        mMemStatView = (TextView)findViewById(R.id.textView_2_3);
        mE23MemReporter = new MemReporter();
        mE23Handler = new Handler();
        mE23Handler.postDelayed(mE23MemReporter, 500);
    }

    private class MemReporter implements Runnable {
        @Override
        public void run() {
            Runtime runtime = Runtime.getRuntime();
            long usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
            long maxHeapSizeInMB = runtime.maxMemory() / 1048576L;
            String memStat = String.format("%d / %d MB", usedMemInMB, maxHeapSizeInMB);
            mMemStatView.setText(memStat);
            mE23Handler.postDelayed(mE23MemReporter, 500);
        }
    };

    private class Allocer extends Thread {
        List<byte[]> buf = new ArrayList<byte[]>();

        private void alloc20MB() {
            byte aaa[] = new byte[20 * 1024 * 1024];
            buf.add(aaa);
        }
        public void run() {
            while (true) {
                alloc20MB();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onE23Start(android.view.View view) {
        Allocer allocer = new Allocer();
        allocer.start();
    }
}
