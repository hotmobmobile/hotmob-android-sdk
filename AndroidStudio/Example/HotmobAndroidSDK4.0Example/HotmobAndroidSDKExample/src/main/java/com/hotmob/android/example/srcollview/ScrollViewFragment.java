package com.hotmob.android.example.srcollview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScrollViewFragment extends BaseFragment {

    private LinearLayout videoBanner, banner;

    public ScrollViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_scroll_view, container, false);

        videoBanner = root.findViewById(R.id.video_banner_container);
        banner = root.findViewById(R.id.banner_container);

        ScrollView scrollView = root.findViewById(R.id.scrollview);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                HotmobManager.updateBannerPosition();
            }
        });

        HotmobManager.getBanner(this, new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                videoBanner.addView(bannerView);
            }
        }, HotmobManager.getScreenWidth(getActivity()), "ScrollViewVideo", getResources().getStringArray(R.array.video_banner_adcodes)[0]);

        HotmobManager.getBanner(this, new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                banner.addView(bannerView);
            }
        }, HotmobManager.getScreenWidth(getActivity()), "ScrollViewBanner", getResources().getStringArray(R.array.image_banner_adcodes)[0]);

        return root;
    }

}
