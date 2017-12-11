package com.hotmob.android.example.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotmob.android.example.R;
import com.hotmob.android.example.recycleview.RecycleItemFragment.OnListFragmentInteractionListener;
import com.hotmob.android.example.recycleview.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItem> items;
    private final OnListFragmentInteractionListener listener;

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> items, OnListFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);

        // clear all banner views first
        holder.bannerLayout.removeAllViews();

        if (holder.item.bannerLayout != null){ // this item is a banner

            // set visibility to banner view
            holder.normalLayout.setVisibility(View.GONE);
            holder.bannerLayout.setVisibility(View.VISIBLE);

            // add item's banner into the layout
            holder.bannerLayout.addView(holder.item.bannerLayout);

        } else { // normal item, go to normal render

            // set visibility to normal view
            holder.normalLayout.setVisibility(View.VISIBLE);
            holder.bannerLayout.setVisibility(View.GONE);

            // normal flow to init view
            holder.idView.setText(items.get(position).id);
            holder.contentView.setText(items.get(position).name);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        listener.onListFragmentInteraction(holder.item);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView idView;
        public final TextView contentView;
        public final LinearLayout normalLayout;
        public final LinearLayout bannerLayout;
        public RecyclerViewItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.id);
            contentView = (TextView) view.findViewById(R.id.content);
            normalLayout = (LinearLayout) view.findViewById(R.id.normal_layout);
            bannerLayout = (LinearLayout) view.findViewById(R.id.banner_container);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }
}
