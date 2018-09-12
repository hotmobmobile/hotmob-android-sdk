package com.hotmob.android.example.autoreload.changefragment;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {
    public final String LOG_TAG = this.getClass().getSimpleName();

    private String pageName;

    private Banner banner;

    public static ContentFragment newInstance(String pageName) {
        Bundle args = new Bundle();

        ContentFragment fragment = new ContentFragment();
        args.putString("pageName", pageName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageName = getArguments().getString("pageName", null);

        // Create Banner Controller Object
        banner = new Banner.Builder(getContext())
                .setIdentifier("Banner_in_"+pageName)
                .build();

        // Set ad code
        banner.setAdCode(getResources().getStringArray(R.array.image_banner_adcodes)[0]);

        ReloadManager.setAutoReload(banner, pageName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(LOG_TAG, "onCreateView() - "+pageName);
        View root = inflater.inflate(R.layout.fragment_blank, container, false);

        ((TextView) root.findViewById(R.id.blank_fragment_title)).setText(pageName);

        // Add the Banner view to the Layout holding it
        LinearLayout bottomBannerLayout = root.findViewById(R.id.banner_view);
        banner.bindToView(bottomBannerLayout);

        // NO NEED to call loadAd() as ReloadManager will handle it

        return root;
    }

    @Override
    public void onDestroy() {
        // Destroy all related Banners on this page
        // This is important as if the Banner is not destroyed, the same Banner will be used again
        // when a page with the same name is created
        ReloadManager.pageOnDestroy(pageName);

        super.onDestroy();
    }
}
