package com.hotmob.android.example.autoreload.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ken on 15/11/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int numOfTabs;
    private ViewPagerItemFragment tab1;
    private ViewPagerItemFragment tab2;
    private ViewPagerItemFragment tab3;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (tab1 == null)
                    tab1 = ViewPagerItemFragment.newInstance(1);
                return tab1;
            case 1:
                if (tab2 == null)
                    tab2 = ViewPagerItemFragment.newInstance(2);
                return tab2;
            case 2:
                if (tab3 == null)
                    tab3 = ViewPagerItemFragment.newInstance(3);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
