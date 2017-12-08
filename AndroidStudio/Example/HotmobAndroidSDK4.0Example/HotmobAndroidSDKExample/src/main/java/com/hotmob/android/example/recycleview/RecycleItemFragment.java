package com.hotmob.android.example.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RecycleItemFragment extends BaseFragment {

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
            ArrayList<RecyclerViewItem> items = new ArrayList<>();
            for (int i=0;i<50;i++){
                items.add(new RecyclerViewItem(i+1+"", "Item "+(i+1)));
            }
            items.add(38, new RecyclerViewItem(this, "RecyclerView38",
                    getResources().getStringArray(R.array.image_banner_adcodes)[0]));
            items.add(25, new RecyclerViewItem(this, "RecyclerView25",
                    getResources().getStringArray(R.array.video_banner_adcodes)[0]));
            items.add(12, new RecyclerViewItem(this, "RecyclerView12",
                    getResources().getStringArray(R.array.html_banner_adcodes)[3]));
            items.add(8, new RecyclerViewItem(this, "RecyclerView8",
                    getResources().getStringArray(R.array.image_banner_adcodes)[0]));
            items.add(2, new RecyclerViewItem(this, "RecyclerView2",
                    getResources().getStringArray(R.array.image_banner_adcodes)[0]));
            recyclerView.setAdapter(new RecyclerViewAdapter(items, listener));

            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    HotmobManager.updateBannerPosition();
                }
            });
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
