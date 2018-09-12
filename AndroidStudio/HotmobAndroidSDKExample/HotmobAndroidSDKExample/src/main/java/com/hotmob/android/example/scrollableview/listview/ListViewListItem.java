package com.hotmob.android.example.scrollableview.listview;

import com.hotmob.sdk.core.controller.Banner;

/**
 * A class of ListView Items, there should be original data (e.g. name) and an additional field for
 * store Hotmob Banner AdView.
 * If you already have a class for your data, just add a member to store AdView.
 */
class ListViewListItem {
    String name;
    Banner banner;

    ListViewListItem(String name) {
        this.name = name;
    }

    // create a constructor for store Hotmob Banner AdView
    ListViewListItem(Banner banner) {
        this.banner = banner;
    }
}
