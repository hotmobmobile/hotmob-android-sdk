package com.hotmob.android.example;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Hotmob Ltd. on 2016/05/05.
 */
public class AudioStreamingController {
    private static AudioStreamingController instance;

    private boolean isPlaying;

    public static AudioStreamingController getInstance() {
        if (instance == null) {
            instance = new AudioStreamingController();
        }
        return instance;
    }

    private AudioStreamingController() {
        // Initialize MediaPlayer here.
        isPlaying = false;
    }

    public void start(String url) {
        // Start Streaming
        isPlaying = true;
    }

    public void stop() {
        // Stop Streaming
        isPlaying = false;
    }

    public void pause() {
        // Pause video, but streaming is still ongoing
    }

    public void resume() {
        // Resume video, streaming is still ongoing.
    }

    public void mute() {
        // Mute video player
    }

    public void unmute() {
        // Unmute video player
    }

    public boolean isPlayingAudio() {
        return isPlaying;
    }
}
