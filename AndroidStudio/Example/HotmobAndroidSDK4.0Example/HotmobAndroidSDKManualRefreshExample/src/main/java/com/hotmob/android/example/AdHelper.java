package com.hotmob.android.example;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

/**
 * Created by technicalteam on 26/8/2016.
 */
public class AdHelper {
    private static AdHelper instance;
    private String TAG = "AdManager_Demo";

    public static AdHelper getInstance() {
        if (instance == null) {
            instance = new AdHelper();
        }
        return instance;
    }

    public static void getPopup(Activity activity, String identifier, String adcode, boolean showWhenResume) {AdHelper.getInstance().createPopup(activity, identifier, adcode, showWhenResume);}
    public static View getBanner(Object rootElement, HotmobManagerListener owner, String identifier, String adcode) {return AdHelper.getInstance().createBanner(rootElement, owner, identifier, adcode);}

    private void createPopup(final Activity activity, final String identifier, final String adcode, final boolean showWhenResume) {
        // TODO: Call launch popup
        HotmobManagerListener listener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                Log.d(TAG, "didLoadBanner");
            }

            @Override
            public void willShowBanner(View bannerView) {
                Log.d(TAG, "willShowBanner");
            }

            @Override
            public void didShowBanner(View bannerView) {
                Log.d(TAG, "didShowBanner");
            }

            @Override
            public void willHideBanner(View bannerView) {
                Log.d(TAG, "willHideBanner");
            }

            @Override
            public void didHideBanner(View bannerView) {
                Log.d(TAG, "didHideBanner");
            }

            @Override
            public void didLoadFailed(View bannerView) {
                Log.d(TAG, "didLoadFailed");
            }

            @Override
            public void didClick(View bannerView) {
                Log.d(TAG, "didClick");
            }

            @Override
            public void openNoAdCallback(View bannerView) {
                Log.d(TAG, "openNoAdCallback");
            }

            @Override
            public void willShowInAppBrowser(View bannerView) {
                Log.d(TAG, "willShowInAppBrowser");
            }

            @Override
            public void didShowInAppBrowser(View bannerView) {
                Log.d(TAG, "didShowInAppBrowser");
            }

            @Override
            public void wilLCloseInAppBrowser(View bannerView) {
                Log.d(TAG, "wilLCloseInAppBrowser");
            }

            @Override
            public void didCloseInAppBrowser(View bannerView) {
                Log.d(TAG, "didCloseInAppBrowser");
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                Log.d(TAG, "openInternalCallback");
            }

            public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                if (isSoundEnable) {
                    Toast.makeText(activity, "Banner Direct: Unmute!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Banner Direct: Mute!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        HotmobManager.getPopup(activity, listener, identifier, adcode, showWhenResume, false);

    }

    private View createBanner(Object rootElement, HotmobManagerListener owner, String identifier, String adCode) {
        Log.d(TAG, "createBanner");

        if (!HotmobManager.shouldBlockBannerRequest()) {
            Log.d(TAG, "should load the banner.");

            int screenWidth = 0;
            if (rootElement instanceof Activity) {
                screenWidth = HotmobManager.getScreenWidth((Activity)rootElement);
            } else if (rootElement instanceof android.app.Fragment) {
                screenWidth = HotmobManager.getScreenWidth(((android.app.Fragment)rootElement).getActivity());
            } else if (rootElement instanceof android.support.v4.app.Fragment) {
                screenWidth = HotmobManager.getScreenWidth(((android.support.v4.app.Fragment)rootElement).getActivity());
            }

            return HotmobManager.getBanner(rootElement, owner, screenWidth, identifier, adCode, false);
        }

        return null;
    }
}
