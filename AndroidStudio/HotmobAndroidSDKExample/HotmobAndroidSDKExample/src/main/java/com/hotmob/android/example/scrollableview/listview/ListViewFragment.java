package com.hotmob.android.example.scrollableview.listview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hotmob.android.example.R;
import com.hotmob.sdk.core.adview.videoView.VideoView;
import com.hotmob.sdk.core.controller.Banner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

    private Banner topBanner, instreamBanner;

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        topBanner = new Banner.Builder(getContext())
                .setIdentifier("TopBanner")
                .setAutoPauseMode(VideoView.getDefaultAutoPauseMode())   // for Video auto-pause when out of screen
                .build();

        // Set ad code
        topBanner.setAdCode(getResources().getStringArray(R.array.video_banner_adcodes)[0]);

        // Create Banner Controller Object
        instreamBanner = new Banner.Builder(getContext())
                .setIdentifier("InstreamBanner")
                .build();

        // Set ad code
        instreamBanner.setAdCode(getResources().getStringArray(R.array.image_banner_adcodes)[0]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_view, container, false);

        final ArrayList<ListViewListItem> items = new ArrayList<>();
        for (int i=0;i<20;i++){
            items.add(new ListViewListItem(i+1+""));
        }

        // Add to ListView
        items.add(0, new ListViewListItem(topBanner));
        items.add(5, new ListViewListItem(instreamBanner));

        ListViewListAdapter adapter = new ListViewListAdapter(getContext(), items);
        ListView listView = root.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        // set scroll view to video banner
        topBanner.setScrollableHolder(listView);

        // load ad for display
        topBanner.loadAd();
        instreamBanner.loadAd();

        return root;
    }

    @Override
    public void onDestroy() {
        Log.d("ListView", "onDestroy()");
        topBanner.destroy();
        instreamBanner.destroy();
        super.onDestroy();
    }
}
