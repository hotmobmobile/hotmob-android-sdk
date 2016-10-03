package com.hotmob.android.example.banner;

import android.content.Context;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hotmob.android.example.AudioStreamingController;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HotmobAndroidSDKExampleMultipleBannersActivityFragment extends Fragment {

    protected final String TAG = this.getClass().getName();

    private ListView mListView;

    private ArrayList<View> mList;
    private ArrayList<View> mBannerList;
    private CellArrayAdapter mCellArrayAdapter;

    private final int mBannerPositionStart = 2;
    private final int mBannerPositionForEach = 5;
    private final int mCellNum = 20;

    public HotmobAndroidSDKExampleMultipleBannersActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmob_android_sdkexample_multiple_banners, container, false);

        mListView = (ListView)view.findViewById(R.id.hotmob_example_multiple_banners_listView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.createList();
    }

    private void createList() {
        String [] adCodes = new String[] {
                "hotmob_android_example_image_banner",
                "hotmob_android_example_image_banner",
                "hotmob_android_example_image_banner",
                "hotmob_android_example_image_banner"
        };

        if (mList == null) {
            mList = new ArrayList<View>();
        } else {
            mList.clear();
        }

        if (mBannerList == null) {
            mBannerList = new ArrayList<View>();
        } else {
            removeAllBanners();
        }

        for (int i=0; i<mCellNum; i++) {
            if (i%mBannerPositionForEach == mBannerPositionStart) {
                createBannerCell(adCodes[i/mBannerPositionForEach], i);
            } else {
                createNormalCell(i);
            }
        }

        mCellArrayAdapter = new CellArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mCellArrayAdapter);
    }

    private void removeAllBanners() {
        Iterator<View> iter = mBannerList.iterator();

        while (iter.hasNext()) {
            View view = iter.next();
            HotmobManager.destroyBanner(view);
            iter.remove();
        }
    }

    private void createNormalCell(int position) {
        //1.) Add Title View
        LayoutInflater titleInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = titleInflater.inflate(R.layout.listview_cell_item, null);
        TextView cellTextView = (TextView)titleView.findViewById(R.id.listview_cell_item_textview);
        cellTextView.setText("Item #" + position);
        mList.add(titleView);
    }

    private void createBannerCell(String adCode, int position) {
        LayoutInflater bannerInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bannerView = bannerInflater.inflate(R.layout.listview_banner_cell_item, null);
        final LinearLayout bannerLayout = (LinearLayout)bannerView.findViewById(R.id.list_banner_cell_layout);

        HotmobManagerListener listener = new HotmobManagerListener() {
            public void didLoadBanner(View bannerView) {
                bannerLayout.addView(bannerView);
            }

            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);

                if (url.equals("new")) {
                    Toast.makeText(HotmobAndroidSDKExampleMultipleBannersActivityFragment.this.getActivity(), "This is internal callback!", Toast.LENGTH_SHORT).show();
                }
            }

            public void onResizeBanner(View bannerView) {
                Log.i(TAG, "onResizeBanner");
                super.onResizeBanner(bannerView);
            }

            // Please note:
            // isSoundEnable == true --> Unmute
            // isSoundEnable == false --> Mute
            public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                if (isSoundEnable) {
                    // Unmute
                    AudioStreamingController.getInstance(getActivity()).unmute();
                } else {
                    // Mute
                    AudioStreamingController.getInstance(getActivity()).mute();
                }
            }
        };

        View hotmobBannerView = HotmobManager.getBanner(getActivity(), listener, HotmobManager.getScreenWidth(getActivity()), ("banner_position_" + position), adCode);
        mBannerList.add(hotmobBannerView);
        mList.add(bannerView);

    }

    @Override
    public void onStop() {
        super.onStop();
        removeAllBanners();
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
}
