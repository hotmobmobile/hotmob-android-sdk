package com.hotmob.android.example.scrollableview.srcollview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hotmob.android.example.R;
import com.hotmob.sdk.core.adview.videoView.VideoView;
import com.hotmob.sdk.core.controller.Banner;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScrollViewFragment extends Fragment {

    private Banner banner, videoBanner;

    public ScrollViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        banner = new Banner.Builder(getContext())
                .setIdentifier("ScrollViewBanner")
                .build();

        // Set ad code
        banner.setAdCode(getResources().getStringArray(R.array.html_banner_adcodes)[0]);

        // Create Banner Controller Object
        videoBanner = new Banner.Builder(getContext())
                .setIdentifier("ScrollViewVideo")
                .setAutoPauseMode(VideoView.getDefaultAutoPauseMode())   // for Video auto-pause when out of screen
                .build();

        // Set ad code
        videoBanner.setAdCode(getResources().getStringArray(R.array.video_banner_adcodes)[0]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_scroll_view, container, false);

        // Add the Banner view to the Layout holding it
        LinearLayout bannerContainer = root.findViewById(R.id.banner_container);
        banner.bindToView(bannerContainer);
        LinearLayout videoBannerContainer = root.findViewById(R.id.video_banner_container);
        videoBanner.bindToView(videoBannerContainer);

        // set scroll view to video banner
        videoBanner.setScrollableHolder(root.findViewById(R.id.scrollview));

        // load ad for display
        banner.loadAd();
        videoBanner.loadAd();

        return root;
    }

    @Override
    public void onDestroy() {
        banner.destroy();
        videoBanner.destroy();
        super.onDestroy();
    }
}
