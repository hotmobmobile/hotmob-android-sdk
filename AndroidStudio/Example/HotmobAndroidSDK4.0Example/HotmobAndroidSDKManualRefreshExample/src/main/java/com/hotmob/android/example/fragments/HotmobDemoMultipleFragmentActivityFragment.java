package com.hotmob.android.example.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hotmob.android.example.AdHelper;
import com.hotmob.android.example.R;
import com.hotmob.sdk.handler.HotmobHandler;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class HotmobDemoMultipleFragmentActivityFragment extends Fragment implements Button.OnClickListener{

    private Button mFragment2Button;
    private LinearLayout mBannerLayout;
    private LinearLayout mTopBannerLayout;

    public HotmobDemoMultipleFragmentActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Test", "Fragment 1 onActivityCreated");

        HotmobManager.setCurrentFragment(this);

        HotmobManagerListener bannerListener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                if (HotmobManager.getIdentifier(bannerView).equals("fragment_bottom")) {
                    mBannerLayout.addView(bannerView);
                } else if (HotmobManager.getIdentifier(bannerView).equals("fragment_top")) {
                    mTopBannerLayout.addView(bannerView);
                }
            }

            @Override
            public void onResizeBanner(View bannerView) {
                super.onResizeBanner(bannerView);
            }

            // Please note:
            // isSoundEnable == true --> Unmute
            // isSoundEnable == false --> Mute
            public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                if (isSoundEnable) {
                    Toast.makeText(HotmobDemoMultipleFragmentActivityFragment.this.getActivity(), "Banner Direct: Unmute!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HotmobDemoMultipleFragmentActivityFragment.this.getActivity(), "Banner Direct: Mute!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        AdHelper.getBanner(this, bannerListener, "fragment_bottom", "hotmob_android_example_image_banner");
        AdHelper.getBanner(this, bannerListener, "fragment_top", "hotmob_android_example_image_banner");

        HotmobHandler.getInstance(this.getActivity()).onFragmentViewCreated();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Test", "Fragment 1 onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Test", "Fragment 1 onCreateView");
        return inflater.inflate(R.layout.fragment_hotmob_demo_multiple_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Test", "Fragment 1 onViewCreated");

        mFragment2Button = (Button)view.findViewById(R.id.fragment_2_button);
        mFragment2Button.setOnClickListener(this);

        mBannerLayout = (LinearLayout)view.findViewById(R.id.fragment_1_banner_layout);
        mTopBannerLayout = (LinearLayout)view.findViewById(R.id.fragment_banner_top_layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Test", "Fragment 1 onResume");
    }

    @Override
    public void onClick(View v) {
        if (this.getActivity().getClass().equals(HotmobDemoMultipleFragmentActivity.class)) {
            HotmobDemoMultipleFragmentActivity activity = (HotmobDemoMultipleFragmentActivity) this.getActivity();

            HotmobDemoMultipleFragmentActivitySecondFragment secondFragment = new HotmobDemoMultipleFragmentActivitySecondFragment();

            activity.changeFragment(secondFragment);
        }
    }
}
