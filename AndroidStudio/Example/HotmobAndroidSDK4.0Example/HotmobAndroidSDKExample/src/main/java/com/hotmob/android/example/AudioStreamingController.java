package com.hotmob.android.example;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Hotmob Ltd. on 2016/05/05.
 */
public class AudioStreamingController {
    private static AudioStreamingController instance;
    private MediaPlayer mMp;
    private Activity mActivity;

    private boolean isPlaying;

    public static AudioStreamingController getInstance(Activity activity) {
        if (instance == null) {
            instance = new AudioStreamingController();
        }
        instance.mActivity = activity;
        return instance;
    }

    private AudioStreamingController() {
        // Initialize MediaPlayer here.
        isPlaying = false;
    }

    public void start(String url) {
        // Start Streaming
        try {
            if (mMp != null) {
                mMp.stop();
                mMp.release();
                mMp = null;
            }
            mMp = MediaPlayer.create(mActivity, Uri.parse(url));
            mMp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                public void onPrepared(MediaPlayer mp)
                {
                    mp.start();
                }
            });
            mMp.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isPlaying = true;
    }

    public void stop() {
        // Stop Streaming
        if (mMp != null) {
            mMp.stop();
            mMp.release();
            mMp = null;
        }
        isPlaying = false;
    }

    public void pause() {
        // Pause video, but streaming is still ongoing
        if (mMp != null) {
            mMp.pause();
        }
    }

    public void resume() {
        // Resume video, streaming is still ongoing.
        if (mMp != null) {
            mMp.start();
        }
    }

    public void mute() {
        // Mute video player
        if (mMp != null) {
            mMp.setVolume(0, 0);
        }
    }

    public void unmute() {
        // Unmute video player
        if (mMp != null) {
            mMp.setVolume(1, 1);
        }
    }

    public boolean isPlayingAudio() {
        return isPlaying;
    }
}
