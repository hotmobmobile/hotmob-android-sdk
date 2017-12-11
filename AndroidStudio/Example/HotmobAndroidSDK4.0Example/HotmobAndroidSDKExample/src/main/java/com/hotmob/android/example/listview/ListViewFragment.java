package com.hotmob.android.example.listview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends BaseFragment {


    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_view, container, false);

        ArrayList<ListViewListItem> items = new ArrayList<>();
        for (int i=0;i<50;i++){
            items.add(new ListViewListItem(i+1+""));
        }
        items.add(38, new ListViewListItem(this, "ListView38",
                getResources().getStringArray(R.array.image_banner_adcodes)[0]));
        items.add(25, new ListViewListItem(this, "ListView25",
                getResources().getStringArray(R.array.video_banner_adcodes)[0]));
        items.add(12, new ListViewListItem(this, "ListView12",
                getResources().getStringArray(R.array.html_banner_adcodes)[3]));
        items.add(8, new ListViewListItem(this, "ListView8",
                getResources().getStringArray(R.array.image_banner_adcodes)[0]));
        items.add(2, new ListViewListItem(this, "ListView2",
                getResources().getStringArray(R.array.image_banner_adcodes)[0]));

        ListViewListAdapter adapter = new ListViewListAdapter(getContext(), items);
        ListView listView = root.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // for Video Banner auto pause
                HotmobManager.updateBannerPosition();
            }
        });

        return root;
    }

}
