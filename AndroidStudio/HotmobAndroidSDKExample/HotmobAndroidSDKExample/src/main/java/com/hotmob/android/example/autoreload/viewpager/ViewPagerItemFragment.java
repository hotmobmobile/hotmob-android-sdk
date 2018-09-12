package com.hotmob.android.example.autoreload.viewpager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotmob.android.example.R;
import com.hotmob.sdk.core.controller.Banner;
import com.hotmob.sdk.module.autoreload.ReloadManager;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerItemFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();

    private int fragmentCounter;

    private Banner banner;

    public static ViewPagerItemFragment newInstance(int fragmentCounter) {
        Bundle args = new Bundle();

        ViewPagerItemFragment fragment = new ViewPagerItemFragment();
        args.putInt("fragmentCounter", fragmentCounter);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentCounter = getArguments().getInt("fragmentCounter");

        // Create Banner Controller Object
        banner = new Banner.Builder(getContext())
                .setIdentifier("ViewPagerBanner_"+fragmentCounter)
                .build();

        // Set ad code
        banner.setAdCode(getResources().getStringArray(R.array.image_banner_adcodes)[0]);

        // set the Banner to page "ViewPage(N)", this name should be sync in parent
        ReloadManager.setAutoReload(banner, "ViewPager"+fragmentCounter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "onCreateView() - "+fragmentCounter);
        View root = inflater.inflate(R.layout.fragment_blank, container, false);

        ((TextView) root.findViewById(R.id.blank_fragment_title)).setText(
                String.format(
                        Locale.ENGLISH,
                        getString(R.string.blank_fragment_title),
                        fragmentCounter));


        // Add the Banner view to the Layout holding it
        LinearLayout bottomBannerLayout = root.findViewById(R.id.banner_view);
        banner.bindToView(bottomBannerLayout);

        // NO NEED to call loadAd() as ReloadManager will handle it

        return root;
    }

    @Override
    public void onDestroy() {
        // destroy all related Banners on this page
        ReloadManager.pageOnDestroy("ViewPager"+fragmentCounter);

        super.onDestroy();
    }
}
