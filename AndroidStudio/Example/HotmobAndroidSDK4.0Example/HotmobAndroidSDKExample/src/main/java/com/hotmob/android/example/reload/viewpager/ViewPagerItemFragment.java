package com.hotmob.android.example.reload.viewpager;


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
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerItemFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();

    private boolean firstTimeResume = true;
    private int fragmentCounter;
    private HotmobManagerListener bannerListener;

    private LinearLayout bottomBannerLayout;
    private View bannerView;

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

        bannerListener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                Log.d(TAG, "didLoadBanner in Fragment " + fragmentCounter);
                bottomBannerLayout.addView(bannerView);
            }

            @Override
            public void willHideBanner(View bannerView) {
                super.willHideBanner(bannerView);
                Log.d(TAG, "willHideBanner in Fragment " + fragmentCounter);
            }

            @Override
            public void didHideBanner(View bannerView) {
                super.didHideBanner(bannerView);
                Log.d(TAG, "didHideBanner in Fragment " + fragmentCounter);
            }
        };

        // the 1st fragment displayed needs to call banner so that banner will be shown when the
        // view pager 1st shows without changing tabs
        if (fragmentCounter == 1 && firstTimeResume) {
            firstTimeResume = false;
            callBanner();
        }

        return root;
    }

    // called by parent when changing tabs or on resume
    // notice that getBanner() is used as this function has logic to hide other banners while
    // refreshBanner() does not
    public void callBanner() {
        HotmobManager.setCurrentFragment(this);
        if (getActivity() != null)
            bannerView = HotmobManager.getBanner(this, bannerListener, HotmobManager.getScreenWidth(getActivity()),
                    "ViewPagerFragment" + fragmentCounter,
                    getResources().getStringArray(R.array.image_banner_adcodes)[0],
                    false);
    }

}
