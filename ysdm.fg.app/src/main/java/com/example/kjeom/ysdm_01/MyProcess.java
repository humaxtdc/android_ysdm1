package yongin.bora.dkkang.kutils;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class MyProcess {
    private static final int RUN_COUNT = 100;
    private static final int BUFFER_SIZE = 1024 * 1024;

    private TextView mTextView;
    private Handler mHandler;
    private ArrayList<byte[]> mResultList = new ArrayList<byte[]>();

    private long mStartTime;

    private OnNeedOptimizeProcess mOnNeedOptimizeProcess;
    public interface OnNeedOptimizeProcess {
        public void onProcess(byte[] inputBuffer);
    }

    public MyProcess(TextView view, OnNeedOptimizeProcess onNeedOptimizeProcess) {
        mTextView = view;
        mOnNeedOptimizeProcess = onNeedOptimizeProcess;
        mHandler = new Handler();
    }

    public void testStart() {
        new Thread(mRunnableStart).start();
    }

    private byte[] readBuffer() {
        int bufferSize = BUFFER_SIZE;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new byte[bufferSize];
    }

    public byte[] process(byte[] inputBuffer) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < inputBuffer.length; i++) {
            inputBuffer[i] += 1;
        }
        return inputBuffer;
    }

    public byte[] sendBuffer(byte[] inputBuffer) {
        final int completeCount = mResultList.size();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("" + completeCount + " / " + RUN_COUNT);
            }
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < inputBuffer.length; i++) {
            inputBuffer[i] += 1;
        }
        mResultList.add(inputBuffer);
        if (mResultList.size() == RUN_COUNT) {
            final boolean success = isValid(mResultList);
            mResultList.clear();
            long finishTime = System.currentTimeMillis();
            final long elapsedTime = finishTime - mStartTime;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (success) {
                        mTextView.setText("Success : " + elapsedTime);
                    }
                    else {
                        mTextView.setText("Failed !!");
                    }
                }
            });
        }
        return inputBuffer;
    }

    private Runnable mRunnableStart = new Runnable() {
        @Override
        public void run() {
            mStartTime = System.currentTimeMillis();
            ArrayList<byte[]> resultList = new ArrayList<byte[]>();
            for (int i = 0; i < RUN_COUNT; i++) {
                byte[] inputBuffer = readBuffer();
                if (mOnNeedOptimizeProcess != null) {
                    mOnNeedOptimizeProcess.onProcess(inputBuffer);
                }
            }
        }
    };

    private boolean isValid(ArrayList<byte[]> resultList) {
        if (resultList.size() != RUN_COUNT) {
            return false;
        }

        for (byte[] byteArray : resultList) {
            if (byteArray.length != BUFFER_SIZE) {
                return false;
            }

            for (byte bt : byteArray) {
                if (bt != 2) {
                    return false;
                }
            }
        }
        return true;
    }
}
