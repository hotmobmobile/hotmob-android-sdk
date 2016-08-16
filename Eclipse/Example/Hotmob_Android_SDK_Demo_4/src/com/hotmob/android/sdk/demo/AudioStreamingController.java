package com.hotmob.android.sdk.demo;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Hotmob Ltd. on 2016/05/05.
 */
public class AudioStreamingController {
    private static AudioStreamingController instance;
    private Context context;

    private boolean isPlaying;

    public static AudioStreamingController getInstance(Context context) {
        if (instance == null) {
            instance = new AudioStreamingController();
            instance.context = context.getApplicationContext();
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
    	Toast.makeText(context, "AudioStreamingController: Mute!", Toast.LENGTH_SHORT).show();
    }

    public void unmute() {
        // Unmute video player
    	Toast.makeText(context, "AudioStreamingController: Unmute!", Toast.LENGTH_SHORT).show();
    }

    public boolean isPlayingAudio() {
        return isPlaying;
    }
}
