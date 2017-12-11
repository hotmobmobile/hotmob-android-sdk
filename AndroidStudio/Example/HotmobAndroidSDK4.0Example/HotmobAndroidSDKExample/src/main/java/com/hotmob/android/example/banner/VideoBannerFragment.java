package com.hotmob.android.example.banner;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoBannerFragment extends BaseFragment implements View.OnClickListener, AudioManager.OnAudioFocusChangeListener{
    public static String TAG = VideoBannerFragment.class.getSimpleName();

    private HotmobManagerListener hotmobManagerListener;
    private String currentAdCode;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private FloatingActionButton fab;
    private TextView adCodeDisplay, errorMsg;
    private LinearLayout bannerContainer;
    private View bannerView;

    public VideoBannerFragment() {
        // Required empty public constructor
        hotmobManagerListener = new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
                VideoBannerFragment.this.bannerView = bannerView;
                bannerContainer.addView(bannerView);
                adCodeDisplay.setText(currentAdCode);
            }

            @Override
            public void didLoadFailed(View bannerView) {
                super.didLoadFailed(bannerView);
                errorMsg.setText(R.string.load_fail);
            }

            @Override
            public void didHideBanner(View bannerView) {
                super.didHideBanner(bannerView);
                adCodeDisplay.setText(R.string.banner_hide);
            }

            @Override
            public void openNoAdCallback(View bannerView) {
                super.openNoAdCallback(bannerView);
                errorMsg.setText(R.string.noaddcallback);
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);
                ((MainActivity) VideoBannerFragment.this.getActivity()).changeToInternalFragment(url);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_banner, container, false);

        LinearLayout creativeList = root.findViewById(R.id.creative_list);
        bannerContainer = root.findViewById(R.id.banner_container);
        adCodeDisplay = root.findViewById(R.id.adcode_display);
        errorMsg = root.findViewById(R.id.banner_error);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.gangnam);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);

        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMedia();
            }
        });

        root.findViewById(R.id.global_mute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("kHotmobVideoAdsBannerShouldMuteNotification");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            }
        });

        String[] buttonLabels = getResources().getStringArray(R.array.video_creative_actions);
        String[] adCodes = getResources().getStringArray(R.array.video_banner_adcodes);

        for (int i=0;i<buttonLabels.length;i++) {
            String label = buttonLabels[i];
            String adCode = adCodes[i];
            Button button = (Button) inflater.inflate(R.layout.creative_button, container, false);
            button.setText(label);
            button.setTag(adCode);
            button.setOnClickListener(this);
            creativeList.addView(button);
        }
        getBanner(adCodes[0]);

        return root;
    }

    private void getBanner(String adCode){
        HotmobManager.getBanner(this, hotmobManagerListener,
                HotmobManager.getScreenWidth(getActivity()), "VideoBanner", adCode);
        currentAdCode = adCode;
        errorMsg.setText("");
    }

    private void playMedia(){
        mediaPlayer.start();
        fab.setImageResource(android.R.drawable.ic_media_pause);
        audioManager.requestAudioFocus(VideoBannerFragment.this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    private void pauseMedia(){
        mediaPlayer.pause();
        fab.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void onClick(View v) {
        getBanner((String) v.getTag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pauseMedia();
        mediaPlayer = null;
        audioManager = null;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.e(TAG, "onAudioFocusChange: received AUDIOFOCUS_LOSS, turning FM off");
                pauseMedia();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.e(TAG, "onAudioFocusChange: received AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                pauseMedia();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.e(TAG, "onAudioFocusChange: received AUDIOFOCUS_LOSS_TRANSIENT");
                pauseMedia();
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.v(TAG, "onAudioFocusChange: received AUDIOFOCUS_GAIN");
                playMedia();
                break;
            default:
                Log.e(TAG, "onAudioFocusChange: Unknown audio focus change code " + focusChange);
        }
    }
}
