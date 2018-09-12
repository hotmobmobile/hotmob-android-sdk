package com.hotmob.android.example.scrollableview.recycleview;

import com.hotmob.sdk.core.controller.Banner;

/**
 * A class of RecycleView Items, there should be original data (e.g. name) and an additional field for
 * store Hotmob Banner AdView.
 * If you already have a class for your data, just add a member to store AdView.
 */
public class RecyclerViewItem {
    String id;
    String name;
    Banner banner;

    public RecyclerViewItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // create a constructor to store Banner
    public RecyclerViewItem(Banner banner) {
        this.banner = banner;
    }
}
