package com.hotmob.android.example.interstitial;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.android.example.banner.VideoBannerFragment;
import com.hotmob.android.example.interstitial.list_adapter.InterstitialCreativeListAdapter;
import com.hotmob.android.example.interstitial.list_adapter.InterstitialCreativeListItem;
import com.hotmob.sdk.core.controller.AdController;
import com.hotmob.sdk.core.controller.Interstitial;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoInterstitialFragment extends Fragment implements AudioManager.OnAudioFocusChangeListener{
    public static String TAG = VideoBannerFragment.class.getSimpleName();

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private FloatingActionButton fab;

    public VideoInterstitialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_interstitial, container, false);

        ListView listView = root.findViewById(R.id.interstitial_list);

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

        String[] buttonLabels = getResources().getStringArray(R.array.video_creative_actions);
        String[] adCodes = getResources().getStringArray(R.array.video_interstitial_adcodes);

        ArrayList<InterstitialCreativeListItem> creativeListItems = new ArrayList<>();
        for (int i=0;i<buttonLabels.length;i++){
            InterstitialCreativeListItem item = new InterstitialCreativeListItem(buttonLabels[i], adCodes[i]);
            creativeListItems.add(item);
        }

        final InterstitialCreativeListAdapter adapter = new InterstitialCreativeListAdapter(getContext(), creativeListItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InterstitialCreativeListItem clickedItem = adapter.getItem(position);
                if (clickedItem != null)
                    getInterstitial(clickedItem.adCode);
                else
                    Toast.makeText(getContext(), R.string.interstitial_item_null, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void getInterstitial(String adCode){
        final Interstitial interstitial = new Interstitial.Builder(getContext())
                .setIdentifier("VideoInterstitial")
                .build();
        interstitial.setDeepLinkListener(new AdController.DeepLinkListener() {
            @Override
            public void onDeepLink(String deepLinkAddress) {
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).changeToInternalFragment(deepLinkAddress);
            }
        });
        interstitial.setAdCode(adCode);
        interstitial.loadAd();
    }

    private void playMedia(){
        mediaPlayer.start();
        fab.setImageResource(android.R.drawable.ic_media_pause);
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    private void pauseMedia(){
        mediaPlayer.pause();
        fab.setImageResource(android.R.drawable.ic_media_play);
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
