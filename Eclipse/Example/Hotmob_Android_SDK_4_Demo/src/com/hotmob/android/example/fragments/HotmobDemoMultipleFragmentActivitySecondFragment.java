package com.hotmob.android.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hotmob.android.example.R;
import com.hotmob.sdk.handler.HotmobHandler;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

public class HotmobDemoMultipleFragmentActivitySecondFragment extends Fragment {
    private LinearLayout mBannerLayout;

    public HotmobDemoMultipleFragmentActivitySecondFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Test", "Fragment 2 onActivityCreated");

        HotmobManager.setCurrentFragment(this);

        HotmobManagerListener bannerListener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                mBannerLayout.addView(bannerView);
            }
        };
        HotmobManager.getBanner(this, bannerListener, HotmobManager.getScreenWidth(this.getActivity()), "fragment_2", "hotmob_android_example_image_banner", false);


        HotmobHandler.getInstance(this.getActivity()).onFragmentViewCreated();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Test", "Fragment 2 onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Test", "Fragment 2 onCreateView");
        return inflater.inflate(R.layout.fragment_hotmob_demo_multiple_fragment_activity_second, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Test", "Fragment 2 onViewCreated");
        mBannerLayout = (LinearLayout)view.findViewById(R.id.fragment_2_banner_layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Test", "Fragment 2 onResume");
    }
}
