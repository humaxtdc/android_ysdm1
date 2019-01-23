package com.example.kjeom.ysdm_01;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        mTextView = (TextView)findViewById(R.id.textView_2_4);
        mMyProcess = new MyProcess(mTextView, new MyProcess.OnNeedOptimizeProcess() {
            @Override
            public void onProcess(byte[] inputBuffer) {
                makeYourCode(inputBuffer);
            }
        });

        mBtnStart = (Button)findViewById(R.id.button_2_4_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyProcess.testStart();
            }
        });

        initE2_1();
    }

    private void makeYourCode(byte[] inputBuffer) {
        // Do optimize
        byte[] processedBuffer = mMyProcess.process(inputBuffer);
        mMyProcess.sendBuffer(processedBuffer);
    }

    // ================ E 2_1 ========================
    private boolean runStatus = true;
    private List<StringBuffer> mList;

    private void initE2_1() {
        mList = new ArrayList<StringBuffer>();
    }

    private void runAdd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runStatus) {
                    StringBuffer addItem = new StringBuffer("");
                    mList.add(addItem);
                    for (StringBuffer item : mList) {
                        item.append(1);
                    }
                    Log.d(TAG, "add()");
                }
            }
        }) .start();
    }

    private void runRemove() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(runStatus) {
                    if (mList.size() > 0) {
                        mList.remove(0);
                        Log.d(TAG, "remove()");
                    }
                }
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

}
