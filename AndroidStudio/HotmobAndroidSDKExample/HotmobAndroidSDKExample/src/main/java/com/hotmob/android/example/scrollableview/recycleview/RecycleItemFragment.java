package com.hotmob.android.example.scrollableview.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.android.example.R;
import com.hotmob.sdk.core.adview.videoView.VideoView;
import com.hotmob.sdk.core.controller.Banner;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RecycleItemFragment extends Fragment {

    private Banner topBanner, instreamBanner;

    // TODO: Customize parameters
    private int columnCount = 1;

    private OnListFragmentInteractionListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecycleItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        topBanner = new Banner.Builder(getContext())
                .setIdentifier("TopBanner")
                .shouldAutoPause()   // for Video auto-pause when out of screen
                .build();

        // Set ad code
        topBanner.setAdCode(getResources().getStringArray(R.array.video_banner_adcodes)[0]);

        // Create Banner Controller Object
        instreamBanner = new Banner.Builder(getContext())
                .setIdentifier("InstreamBanner")
                .setFocusable(false)    // prevent auto-focus and lead to scrolling of list
                .build();

        // Set ad code
        instreamBanner.setAdCode(getResources().getStringArray(R.array.image_banner_adcodes)[0]);
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
            for (int i=0;i<20;i++){
                items.add(new RecyclerViewItem(i+1+"", "Item "+(i+1)));
            }

            items.add(0, new RecyclerViewItem(topBanner));
            items.add(5, new RecyclerViewItem(instreamBanner));

            // set scroll view to video banner
            topBanner.setScrollableHolder(recyclerView);

            // load ad for display
            topBanner.loadAd();
            instreamBanner.loadAd();

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, listener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
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
    public void onDestroy() {
        Log.d("ListView", "onDestroy()");
        topBanner.destroy();
        instreamBanner.destroy();
        super.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(RecyclerViewItem item);
    }
}
