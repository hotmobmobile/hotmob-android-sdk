package com.hotmob.android.example.banner;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.sdk.core.controller.AdController;
import com.hotmob.sdk.core.controller.Banner;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoBannerFragment extends Fragment implements View.OnClickListener, AudioManager.OnAudioFocusChangeListener{
    public static String TAG = VideoBannerFragment.class.getSimpleName();

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private FloatingActionButton fab;
    private TextView adCodeDisplay, errorMsg;
    private Banner banner;

    private int customWidth;

    public VideoBannerFragment() {
        // Required empty public constructor
    }

    public static VideoBannerFragment newInstance(int customWidth) {
        VideoBannerFragment fragment = new VideoBannerFragment();
        fragment.customWidth = customWidth;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        Banner.Builder builder = new Banner.Builder(getContext())
                .setIdentifier("VideoBanner");
        if (customWidth > 0) {
            builder = builder.setWidth(customWidth);
        }
        banner = builder.build();

        banner.setListener(new Banner.Listener() {
            @Override
            public void onAdEvent(AdController.AdEvent event) {
                switch (event) {
                    case START_LOADING:
                        errorMsg.setText("");
                        adCodeDisplay.setText(R.string.loading);
                        break;
                    case LOAD_FAILED:
                        errorMsg.setText(R.string.load_fail);
                        break;
                    case LOAD_TIMEOUT:
                        errorMsg.setText(R.string.load_fail);
                        break;
                    case NO_AD:
                        errorMsg.setText(R.string.noaddcallback);
                        break;
                    case SHOW:
                        adCodeDisplay.setText(banner.getAdCode());
                        break;
                    case HIDE:
                        errorMsg.setText("");
                        adCodeDisplay.setText(R.string.banner_hide);
                        break;
                }
            }
        });
        banner.setDeepLinkListener(new AdController.DeepLinkListener() {
            @Override
            public void onDeepLink(String deepLinkAddress) {
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).changeToInternalFragment(deepLinkAddress);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_banner, container, false);

        LinearLayout creativeList = root.findViewById(R.id.creative_list);
        RelativeLayout bannerContainer = root.findViewById(R.id.banner_container);
        adCodeDisplay = root.findViewById(R.id.adcode_display);
        errorMsg = root.findViewById(R.id.banner_error);

        root.findViewById(R.id.width_full_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, VideoBannerFragment.newInstance(-1))
                            .commit();
                }
            }
        });
        root.findViewById(R.id.width_320_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, VideoBannerFragment.newInstance(320))
                            .commit();
                }
            }
        });
        root.findViewById(R.id.width_200_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, VideoBannerFragment.newInstance(200))
                            .commit();
                }
            }
        });

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.gangnam);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);

        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                    pauseMedia();
                else
                    playMedia();
            }
        });

        root.findViewById(R.id.global_mute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.muteVideo();
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

        // Set ad code
        banner.setAdCode(adCodes[0]);

        // Add the Banner view to the Layout holding it
        banner.bindToView(bannerContainer);

        // load ad for display
        banner.loadAd();

        return root;
    }

    private void playMedia(){
        if (mediaPlayer != null) {
            mediaPlayer.start();
            fab.setImageResource(android.R.drawable.ic_media_pause);
            audioManager.requestAudioFocus(VideoBannerFragment.this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    private void pauseMedia(){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            fab.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    @Override
    public void onClick(View v) {
        // 1. hide the Banner
        banner.hide();
        // 2. change ad code
        banner.setAdCode((String) v.getTag());
        // 3. reload Banner
        banner.loadAd();
    }

    @Override
    public void onDestroy() {
        // destroy instance
        banner.destroy();

        pauseMedia();
        mediaPlayer = null;
        audioManager = null;

        super.onDestroy();
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
