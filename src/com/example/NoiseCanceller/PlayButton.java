package com.example.NoiseCanceller;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import java.io.IOException;

/**
 * Created by g00238234 on 08/03/2016.
 */
public class PlayButton {
    private static final String LOG_TAG = "AudioRecordTest";
    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;
    private static String mFileName = null;

    boolean mStartPlaying = true;

    View.OnClickListener clicker = new View.OnClickListener() {
        public void onClick(View v) {
            onPlay(mStartPlaying);
            if (mStartPlaying) {
              //  setText("Stop playing");
            } else {
               // setText("Start playing");
            }
            mStartPlaying = !mStartPlaying;
        }
    };
    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

}
