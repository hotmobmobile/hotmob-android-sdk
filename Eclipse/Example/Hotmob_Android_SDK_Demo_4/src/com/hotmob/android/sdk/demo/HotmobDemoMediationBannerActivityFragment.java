package com.hotmob.android.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HotmobDemoMediationBannerActivityFragment extends Fragment{

    protected final String TAG = this.getClass().getName();

    private ListView mListView;

    private ArrayList<View> mList;
    private CellArrayAdapter mCellArrayAdapter;

    private final int mBannerPosition = 3;
    private final int mCellNum = 50;

    private PublisherAdView mAdView;
    
    public HotmobDemoMediationBannerActivityFragment(){	
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmob_demo_mediation_banner, container, false);

        mListView = (ListView)view.findViewById(R.id.hotmob_demo_mediation_banner_list);

        this.createList();

        return view;
    }

    private void createList() {
        if (mList == null) {
            mList = new ArrayList<View>();
        }

        for (int i=0; i<mCellNum; i++) {
            if (i == mBannerPosition) {
                createBannerCell();
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

    private void createBannerCell() {
        //2.) Add Banner View
        LayoutInflater bannerInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bannerView = bannerInflater.inflate(R.layout.listview_banner_cell_item, null);
        final LinearLayout bannerLayout = (LinearLayout)bannerView.findViewById(R.id.list_banner_cell_layout);

        mAdView = new PublisherAdView(this.getActivity());
        mAdView.setAdUnitId("/13648685/hotmob_android_example_image_banner");
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
        mAdView.destroy();
    }
}
