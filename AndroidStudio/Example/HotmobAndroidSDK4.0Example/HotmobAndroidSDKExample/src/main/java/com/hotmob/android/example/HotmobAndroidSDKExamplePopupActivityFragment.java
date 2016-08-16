package com.hotmob.android.example;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class HotmobAndroidSDKExamplePopupActivityFragment extends Fragment {

    public HotmobAndroidSDKExamplePopupActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmob_android_sdkexample_popup, container, false);

        Button mImagePopupButton = (Button) view.findViewById(R.id.hotmob_popup_normal_button);
        mImagePopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopup("hotmob_android_example_normal_popup");
            }
        });


        Button mVideoAdsPopupButton = (Button) view.findViewById(R.id.hotmob_popup_videoads_button);
        mVideoAdsPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopup("hotmob_android_example_videoads_popup");
            }
        });

        return view;
    }

    private void callPopup(String adCode) {
        HotmobManagerListener listener = new HotmobManagerListener() {
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
            }

            public void didShowBanner(View bannerView) {
                super.didShowBanner(bannerView);
            }

            public void didHideBanner(View bannerView) {
                super.didHideBanner(bannerView);
            }

            public void didLoadFailed(View bannerView) {
                super.didLoadFailed(bannerView);
            }

            public void didClick(View bannerView) {
                super.didClick(bannerView);
            }

            public void didShowInAppBrowser(View bannerView) {
                super.didShowInAppBrowser(bannerView);
            }

            public void didCloseInAppBrowser(View bannerView) {
                super.didCloseInAppBrowser(bannerView);
            }

            public void openNoAdCallback(View bannerView) {
                super.openNoAdCallback(bannerView);
            }

            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);

                if (url.equals("new")) {
                    Toast.makeText(HotmobAndroidSDKExamplePopupActivityFragment.this.getActivity(), "This is internal callback!", Toast.LENGTH_SHORT).show();
                }
            }

            // Please note:
            // isSoundEnable == true --> Unmute
            // isSoundEnable == false --> Mute
            public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                if (isSoundEnable) {
                    // Unmute
                    AudioStreamingController.getInstance().unmute();
                } else {
                    // Mute
                    AudioStreamingController.getInstance().mute();
                }
            }
        };

        HotmobManager.getPopup(HotmobAndroidSDKExamplePopupActivityFragment.this.getActivity(), listener, adCode, adCode, true);
    }
}
