package com.hotmob.android.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hotmob.android.example.fragments.HotmobDemoMultipleFragmentActivity;
import com.hotmob.sdk.handler.HotmobHandler;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotmobAndroidSDKExampleMainActivityFragment extends Fragment {

    protected final String TAG = this.getClass().getName();

    private ListView mListView;

    private ArrayList<View> mList;
    private CellArrayAdapter mCellArrayAdapter;

    private LinearLayout mBannerLayout;

    private View mBannerView;

    public HotmobAndroidSDKExampleMainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmob_android_sdkexample_main, container, false);

        mListView = (ListView)view.findViewById(R.id.hotmob_example_main_listView);
        mBannerLayout = (LinearLayout)view.findViewById(R.id.hotmob_example_main_banner_layout);

        this.createList();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: RequestBanner
        final String adCode = "hotmob_android_example_image_banner";

        HotmobManager.setCurrentFragment(this);

        HotmobManagerListener listener = new HotmobManagerListener() {
            public void didLoadBanner(View bannerView) {
                mBannerView = bannerView;
                mBannerLayout.addView(bannerView);
            }

            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);

                if (url.equals("new")) {
                    Toast.makeText(HotmobAndroidSDKExampleMainActivityFragment.this.getActivity(), "This is internal callback.", Toast.LENGTH_SHORT).show();
                }
            }

            public void onResizeBanner(View bannerView) {
                Log.i(TAG, "onResizeBanner");
                super.onResizeBanner(bannerView);
            }
        };

        HotmobManager.getBanner(getActivity(), listener, HotmobManager.getScreenWidth(getActivity()), "main_banner", adCode);

        HotmobHandler.getInstance(this.getActivity()).onFragmentViewCreated();

    }

    private void createList() {
        String[] values = new String[]{
                "Banner",
                "Popup",
                "Video Ads Banner",
                "Multiple Banners",
                "Mediation Banner",
                "Mediation Popup",
                "Video Ads Banner (Mediation)",
                "Multiple Banners (Mediation)",
                "Banner in fragment",
        };

        if (mList == null) {
            mList = new ArrayList<View>();
        }

        for (int i = 0; i < values.length; i++) {
            createListItem(values[i]);
        }

        mCellArrayAdapter = new CellArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mCellArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = HotmobAndroidSDKExampleMainActivityFragment.this.getActivity();

                switch (position) {
                    case 0:
                        Intent aIntent = new Intent(activity, HotmobAndroidSDKExampleBannerActivity.class);
                        startActivity(aIntent);
                        break;
                    case 1:
                        Intent bIntent = new Intent(activity, HotmobAndroidSDKExamplePopupActivity.class);
                        startActivity(bIntent);
                        break;
                    case 2:
                        Intent cIntent = new Intent(activity, HotmobAndroidSDKExampleVideoAdsBannerActivity.class);
                        startActivity(cIntent);
                        break;
                    case 3:
                        Intent dIntent = new Intent(activity, HotmobAndroidSDKExampleMultipleBannersActivity.class);
                        startActivity(dIntent);
                        break;
                    case 4:
                        Intent eIntent = new Intent(activity, HotmobAndroidSDKExampleMediationBannerActivity.class);
                        startActivity(eIntent);
                        break;
                    case 5:
                        Intent fIntent = new Intent(activity, HotmobAndroidSDKExampleMediationPopupActivity.class);
                        startActivity(fIntent);
                        break;
                    case 6:
                        Intent gIntent = new Intent(activity, HotmobAndroidSDKExampleMediationVideoAdsBannerActivity.class);
                        startActivity(gIntent);
                        break;
                    case 7:
                        Intent hIntent = new Intent(activity, HotmobAndroidSDKExampleMediationMultipleBannersActivity.class);
                        startActivity(hIntent);
                        break;
                    case 8:
                        Intent iIntent = new Intent(activity, HotmobDemoMultipleFragmentActivity.class);
                        startActivity(iIntent);
                    default:
                        break;
                }
            }
        });
    }

    private void createListItem(String value) {
        //1.) Add Title View
        LayoutInflater titleInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = titleInflater.inflate(R.layout.listview_cell_item, null);
        TextView cellTextView = (TextView)titleView.findViewById(R.id.listview_cell_item_textview);
        cellTextView.setText(value);
        mList.add(titleView);
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
        HotmobManager.destroyBanner(mBannerView);
    }
}
