package com.hotmob.android.example.reload;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {
    protected final String TAG = this.getClass().getSimpleName();

    private int activityCounter;
    private int fragmentCounter;

    private LinearLayout bottomBannerLayout;
    private View bannerView;

    public static BlankFragment newInstance(int activityCounter, int fragmentCounter) {
        Bundle args = new Bundle();

        BlankFragment fragment = new BlankFragment();
        args.putInt("activityCounter", activityCounter);
        args.putInt("fragmentCounter", fragmentCounter);
        fragment.setArguments(args);

        return fragment;
    }

    public int getFragmentCounter() { return fragmentCounter; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCounter = getArguments().getInt("activityCounter");
        fragmentCounter = getArguments().getInt("fragmentCounter");
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

        bottomBannerLayout = root.findViewById(R.id.banner_view);
        callBanner();

        return root;
    }

    public void callBanner() {
        if (bannerView == null) {
            Log.d(TAG, "start creating banner");

            // TODO: Call Banner
            final HotmobManagerListener bannerListener = new HotmobManagerListener() {
                @Override
                public void didLoadBanner(View bannerView) {
                    Log.d(TAG, "didLoadBanner in Fragment " + fragmentCounter + " in Activity " + activityCounter);
                    bottomBannerLayout.addView(bannerView);
                }

                @Override
                public void willHideBanner(View bannerView) {
                    super.willHideBanner(bannerView);
                    Log.d(TAG, "willHideBanner in Fragment " + fragmentCounter + " in Activity " + activityCounter);
                }

                @Override
                public void didHideBanner(View bannerView) {
                    super.didHideBanner(bannerView);
                    Log.d(TAG, "didHideBanner in Fragment " + fragmentCounter + " in Activity " + activityCounter);
                }
            };

            bannerView = HotmobManager.getBanner(this, bannerListener, HotmobManager.getScreenWidth(getActivity()),
                    "ReloadFragment" + activityCounter + fragmentCounter,
                    getResources().getStringArray(R.array.image_banner_adcodes)[0],
                    false);
        }else{
            // Need to refresh banner manually
            HotmobManager.refreshBanner(bannerView);
        }
    }

}
