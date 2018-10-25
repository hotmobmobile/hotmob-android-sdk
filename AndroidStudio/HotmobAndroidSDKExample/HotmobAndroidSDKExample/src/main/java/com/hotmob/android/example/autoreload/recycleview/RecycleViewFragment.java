package com.hotmob.android.example.autoreload.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.android.example.R;
import com.hotmob.android.example.scrollableview.recycleview.RecycleItemFragment;
import com.hotmob.android.example.scrollableview.recycleview.RecyclerViewAdapter;
import com.hotmob.android.example.scrollableview.recycleview.RecyclerViewItem;
import com.hotmob.sdk.core.adview.videoView.VideoView;
import com.hotmob.sdk.core.controller.Banner;
import com.hotmob.sdk.module.autoreload.ReloadManager;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link RecycleItemFragment.OnListFragmentInteractionListener}
 * interface.
 */
public class RecycleViewFragment extends Fragment {

    private Banner topBanner, instreamBanner;

    // TODO: Customize parameters
    private int columnCount = 1;

    private RecycleItemFragment.OnListFragmentInteractionListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecycleViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        topBanner = new Banner.Builder(getContext())
                .setIdentifier("TopBanner")
                .build();

        // Set ad code
        topBanner.setAdCode(getResources().getStringArray(R.array.image_banner_adcodes)[0]);

        // set auto reload
        ReloadManager.setAutoReload(topBanner, this);

        // Create Banner Controller Object
        instreamBanner = new Banner.Builder(getContext())
                .setIdentifier("InstreamBanner")
                .shouldAutoPause()
                .build();

        // Set ad code
        instreamBanner.setAdCode(getResources().getStringArray(R.array.video_banner_adcodes)[0]);

        // set auto reload
        ReloadManager.setAutoReload(instreamBanner, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            final ArrayList<RecyclerViewItem> items = new ArrayList<>();
            for (int i=0;i<30;i++){
                items.add(new RecyclerViewItem(i+1+"", "Item "+(i+1)));
            }

            // Add to ListView
            items.add(0, new RecyclerViewItem(topBanner));
            items.add(20, new RecyclerViewItem(instreamBanner));

            // set scroll view to video banner
            instreamBanner.setScrollableHolder(recyclerView);

            // NO NEED to call loadAd() as ReloadManager will handle it

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, listener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecycleItemFragment.OnListFragmentInteractionListener) {
            listener = (RecycleItemFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        // for page shows 1st time load Banner, resume app reload and back to previous page reload
        ReloadManager.setCurrentPage(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // destroy all related Banners on this page
        ReloadManager.pageOnDestroy(this);
    }
}
