package com.hotmob.android.example.autoreload.deeplinkinterstitial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hotmob.android.example.R;
import com.hotmob.sdk.core.controller.Banner;
import com.hotmob.sdk.module.autoreload.ReloadManager;


public class DeepLinkTargetFragment extends Fragment {
    public final String LOG_TAG = this.getClass().getSimpleName();

    private LinearLayout bottomBannerLayout;
    private Banner banner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        banner = new Banner.Builder(getContext())
                .setIdentifier("NormalAdBanner")
                .build();

        // Set ad code
        banner.setAdCode(getResources().getStringArray(R.array.image_banner_adcodes)[0]);

        ReloadManager.setAutoReload(banner, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_deep_link_target, container, false);

        // Add the Banner view to the Layout holding it
        LinearLayout bottomBannerLayout = root.findViewById(R.id.banner_view);
        banner.bindToView(bottomBannerLayout);

        // NO NEED to call loadAd() as ReloadManager will handle it

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        // for page shows 1st time load Banner, resume app reload and back to previous page reload
        ReloadManager.setCurrentPage(this);
    }

    @Override
    public void onDestroy() {
        // destroy all related Banners on this page
        ReloadManager.pageOnDestroy(this);

        super.onDestroy();
    }
}
