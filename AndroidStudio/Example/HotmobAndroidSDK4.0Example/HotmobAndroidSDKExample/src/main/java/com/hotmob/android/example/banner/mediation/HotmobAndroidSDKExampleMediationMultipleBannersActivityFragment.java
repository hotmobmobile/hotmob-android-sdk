package com.hotmob.android.example.banner.mediation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.hotmob.android.example.AudioStreamingController;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HotmobAndroidSDKExampleMediationMultipleBannersActivityFragment extends Fragment {

    protected final String TAG = this.getClass().getName();

    private ListView mListView;

    private ArrayList<View> mList;
    private ArrayList<PublisherAdView> mAdViewsList;
    private CellArrayAdapter mCellArrayAdapter;

    private final int mBannerPositionStart = 2;
    private final int mBannerPositionForEach = 5;
    private final int mCellNum = 20;

    private BroadcastReceiver mBroadcastReceiver;

    private int updateBannerPositionCount = 0;
    private final int updateBannerPositionExecuteNumber = 10;

    public HotmobAndroidSDKExampleMediationMultipleBannersActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmob_android_sdkexample_mediation_multiple_banners, container, false);

        mListView = (ListView)view.findViewById(R.id.hotmob_example_mediation_multiple_banners_listView);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                updateBannerPosition();
            }
        });

        this.createList();

        createLocalBroadcastReceiver();

        return view;
    }

    private void createList() {
        String [] adCodes = new String [] {
                "/13648685/hotmob_android_example_image_banner",
                "/13648685/hotmob_android_example_image_banner",
                "/13648685/hotmob_android_example_image_banner",
                "/13648685/hotmob_android_example_image_banner",
        };

        if (mList == null) {
            mList = new ArrayList<View>();
        }

        if (mAdViewsList == null) {
            mAdViewsList = new ArrayList<>();
        }

        for (int i=0; i<mCellNum; i++) {
            if (i%mBannerPositionForEach == mBannerPositionStart) {
                createBannerCell(adCodes[i/mBannerPositionForEach]);
            } else {
                createNormalCell(i);
            }
        }

        mCellArrayAdapter = new CellArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mCellArrayAdapter);
    }

    private void createNormalCell(int position) {
        //1.) Add Title View
        LayoutInflater titleInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = titleInflater.inflate(R.layout.listview_cell_item, null);
        TextView cellTextView = (TextView)titleView.findViewById(R.id.listview_cell_item_textview);
        cellTextView.setText("Item #" + position);
        mList.add(titleView);
    }

    private void createBannerCell(String adCode) {
        LayoutInflater bannerInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bannerView = bannerInflater.inflate(R.layout.listview_banner_cell_item, null);
        final LinearLayout bannerLayout = (LinearLayout)bannerView.findViewById(R.id.list_banner_cell_layout);

        final PublisherAdView mAdView = new PublisherAdView(this.getActivity());
        mAdView.setAdUnitId(adCode);
        mAdView.setAdSizes(AdSize.SMART_BANNER);

//        HotmobCustomEventBanner.bannerWidth = 320

        AdListener mAdListener = new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                Log.d(TAG, "ErrCode: " + errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.v(TAG, "ad opened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                bannerLayout.addView(mAdView);
                Log.v(TAG, "ad loaded");
            }
        };
        mAdView.setAdListener(mAdListener);

        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mList.add(bannerView);
        mAdViewsList.add(mAdView);
    }

    private class CellArrayAdapter extends ArrayAdapter<View> {
        private HashMap<Integer, View> mIdMap;

        public CellArrayAdapter(Context context, int textViewResourceId,List<View> objects) {
            super(context, textViewResourceId, objects);

            mIdMap = new HashMap<Integer, View>();

            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(i, objects.get(i));
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = mIdMap.get(position);
            return rowView;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (PublisherAdView view: mAdViewsList) {
            view.destroy();
        }

        destroyLocalBroadcastManager();

        mAdViewsList.clear();
        mAdViewsList = null;
        mList.clear();
        mList = null;
    }

    private void destroyLocalBroadcastManager() {
        LocalBroadcastManager.getInstance(this.getActivity().getApplicationContext()).unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;
    }

    private void createLocalBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(HotmobManager.HotmobVideoPlayerMuteNotification);
        filter.addAction(HotmobManager.HotmobVideoPlayerUnmuteNotification);

        if (mBroadcastReceiver == null) {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String actionName = intent.getAction();
                    if (actionName.equals(HotmobManager.HotmobVideoPlayerMuteNotification)) {
                        // Mute
                        AudioStreamingController.getInstance().mute();
                    } else if (actionName.equals(HotmobManager.HotmobVideoPlayerUnmuteNotification)) {
                        // Unmute
                        AudioStreamingController.getInstance().unmute();
                    }
                }
            };
        }

        LocalBroadcastManager.getInstance(this.getActivity().getApplicationContext()).registerReceiver(mBroadcastReceiver, filter);

    }

    private void updateBannerPosition() {
        updateBannerPositionCount++;
        if (updateBannerPositionCount >= updateBannerPositionExecuteNumber) {
            Intent intent = new Intent(HotmobManager.HotmobVideoPlayerBannerPositionNotification);
            LocalBroadcastManager.getInstance(this.getActivity().getApplicationContext()).sendBroadcast(intent);

            updateBannerPositionCount = 0;
        }
    }

}
