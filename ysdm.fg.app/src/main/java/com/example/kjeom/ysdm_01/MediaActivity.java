package com.example.kjeom.ysdm_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        initE51();
    }

    /*
     * E5.1
     */
    private VideoView mVideo;
    private MediaController mMediaController;
    boolean isPlaying1 = false;

    private void initE51() {
        mVideo=(VideoView) findViewById(R.id.videoView);
        MediaController mMediaController = new MediaController(this);
        mMediaController.setAnchorView(mVideo);
        mVideo.setMediaController(mMediaController);
        mVideo.setKeepScreenOn(true);
        mVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.jack_reacher_trailer_2012);
    }
    public void onE51Play(View view) {
        if (isPlaying1 == false) {
            mVideo.start();
            isPlaying1 = true;
        }
    }

    public void onE51Stop(View view) {
        mVideo.stopPlayback();
        isPlaying1 = false;
    }
}
