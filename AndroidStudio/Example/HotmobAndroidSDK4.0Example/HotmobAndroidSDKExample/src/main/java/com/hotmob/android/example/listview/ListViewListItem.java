package com.hotmob.android.example.listview;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

/**
 * Created by ken on 10/11/2017.
 */

public class ListViewListItem {
    public String name;
    public LinearLayout bannerLayout;   // a LinearLayout stored in list item to hold Hotmob banner view

    public ListViewListItem(String name) {
        this.name = name;
    }

    // create a constructor to getBanner() to store in the LinearLayout
    public ListViewListItem(Fragment rootElement, String identifier, String adCode) {
        bannerLayout = new LinearLayout(rootElement.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        bannerLayout.setLayoutParams(layoutParams);
        HotmobManagerListener listener = new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                bannerLayout.addView(bannerView);
            }
        };
        HotmobManager.getBanner(rootElement, listener,
                HotmobManager.getScreenWidth(rootElement.getActivity()),
                identifier, adCode);
    }
}
