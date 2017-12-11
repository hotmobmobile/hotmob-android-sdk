package com.hotmob.android.example.popup;


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

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.android.example.banner.VideoBannerFragment;
import com.hotmob.android.example.popup.list_adapter.PopupCreativeListAdapter;
import com.hotmob.android.example.popup.list_adapter.PopupCreativeListItem;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoPopupFragment extends BaseFragment implements AudioManager.OnAudioFocusChangeListener{
    public static String TAG = VideoBannerFragment.class.getSimpleName();

    private HotmobManagerListener hotmobManagerListener;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private FloatingActionButton fab;

    public VideoPopupFragment() {
        // Required empty public constructor
        hotmobManagerListener = new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
            }

            @Override
            public void didLoadFailed(View bannerView) {
                super.didLoadFailed(bannerView);
                Toast.makeText(getContext(), R.string.load_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void openNoAdCallback(View bannerView) {
                super.openNoAdCallback(bannerView);
                Toast.makeText(getContext(), R.string.noaddcallback, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);
                ((MainActivity) VideoPopupFragment.this.getActivity()).changeToInternalFragment(url);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_popup, container, false);

        ListView listView = root.findViewById(R.id.popup_list);

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

        String[] buttonLabels = getResources().getStringArray(R.array.video_creative_actions);
        String[] adCodes = getResources().getStringArray(R.array.video_popup_adcodes);

        ArrayList<PopupCreativeListItem> creativeListItems = new ArrayList<>();
        for (int i=0;i<buttonLabels.length;i++){
            PopupCreativeListItem item = new PopupCreativeListItem(buttonLabels[i], adCodes[i]);
            creativeListItems.add(item);
        }

        final PopupCreativeListAdapter adapter = new PopupCreativeListAdapter(getContext(), creativeListItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupCreativeListItem clickedItem = adapter.getItem(position);
                if (clickedItem != null)
                    getPopup(clickedItem.adCode);
                else
                    Toast.makeText(getContext(), R.string.popup_item_null, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void getPopup(String adCode){
        HotmobManager.getPopup(getActivity(), hotmobManagerListener,"videoPopup", adCode, false);
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
