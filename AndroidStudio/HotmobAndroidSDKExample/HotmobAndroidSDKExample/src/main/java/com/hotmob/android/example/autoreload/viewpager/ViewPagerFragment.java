package com.hotmob.android.example.autoreload.viewpager;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.android.example.R;
import com.hotmob.sdk.module.autoreload.ReloadManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {
    public static String LOG_TAG = ViewPagerFragment.class.getSimpleName();

    private PagerAdapter adapter;
    private ViewPager viewPager;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_pager, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = root.findViewById(R.id.pager);
        adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(LOG_TAG, "onPageSelected("+position+")");

                // update current page when page changed, this name should be sync in all child
                ReloadManager.setCurrentPage("ViewPager"+(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // set the first page for the first show Fragment
        ReloadManager.setCurrentPage("ViewPager1");

        return root;
    }
}
