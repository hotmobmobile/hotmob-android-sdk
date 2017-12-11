package com.hotmob.android.example.reload.viewpager;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.android.example.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {
    public static String TAG = ViewPagerFragment.class.getSimpleName();

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
        adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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

        // parent view controlling all child pages banner reload:
        // All child's callBanner() should be called in parent when parent detected the child is
        // selected to be the current display. However, this event will not be triggered when first
        // loaded. The child to be the first displayed need to do the first callBanner() itself and
        // this call should not be called in parent due to Android's life cycle. See @TabFragment
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected("+position+")");
                ViewPagerItemFragment fragment = (ViewPagerItemFragment) adapter.getItem(position);
                fragment.callBanner();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return root;
    }

    // callBanner() in correct page when onResume() for parent Activity resume banner reload
    @Override
    public void onResume() {
        super.onResume();
        ViewPagerItemFragment current = (ViewPagerItemFragment) adapter.getItem(viewPager.getCurrentItem());
        if (current != null)
            current.callBanner();
    }
}
