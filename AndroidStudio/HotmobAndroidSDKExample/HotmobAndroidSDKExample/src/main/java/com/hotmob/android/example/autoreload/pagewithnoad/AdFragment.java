package com.hotmob.android.example.autoreload.pagewithnoad;


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

/**
 * This is an example of how to do auto reload in one page only without affecting the other page
 * with no ad. The trick is in {@link #onPause()}. By doing this, the other page with no ad can have
 * no Hotmob auto reload implementation. See {@link NoAdFragment}
 */
public class AdFragment extends Fragment {
    public final String LOG_TAG = this.getClass().getSimpleName();

    private Banner banner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        banner = new Banner.Builder(getContext())
                .setIdentifier("AdFragmentBanner")
                .build();

        // Set ad code
        banner.setAdCode(getResources().getStringArray(R.array.video_banner_adcodes)[0]);

        ReloadManager.setAutoReload(banner, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_ad, container, false);

        root.findViewById(R.id.to_no_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NoAdFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

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
    public void onPause() {
        super.onPause();

        /* Special handling here (Leaving page)
         * To hide the Banner in this page when the page is leaving
         * Just set current page to what ever you want which will never have Banner
         * So that the page with no ad can have no Banner implementation
         *
         * Btw, you can also implement ReloadManager.setCurrentPage(this) in the next Fragment
         * and then you do not need this line
         */
        ReloadManager.setCurrentPage("NoAdPage");
    }

    @Override
    public void onDestroy() {
        // destroy all related Banners on this page
        ReloadManager.pageOnDestroy(this);

        super.onDestroy();
    }
}
