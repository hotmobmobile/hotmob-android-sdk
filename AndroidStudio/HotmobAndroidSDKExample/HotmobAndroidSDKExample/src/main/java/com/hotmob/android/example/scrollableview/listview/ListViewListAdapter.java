package com.hotmob.android.example.scrollableview.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotmob.android.example.R;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * In ListView Adapter, there should be a logic identifying Banner and non-Banner item in {@link #getView(int, View, ViewGroup)}
 * and control the item display.
 *
 * In this example, list item layout listitem_listview has 2 views for Banner display switching for
 * simpler and easier handling
 */
public class ListViewListAdapter extends ArrayAdapter<ListViewListItem> {

    ListViewListAdapter(Context context, ArrayList<ListViewListItem> items){
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ListViewListItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_listview, parent, false);
        }
        if (item != null) {
            // Lookup view for data population
            TextView name = convertView.findViewById(R.id.name);
            ViewGroup bannerLayout = convertView.findViewById(R.id.banner_container);

            // check whether the item is a banner or not
            // first, remove all view in bannerLayout
            bannerLayout.removeAllViews();
            if (item.banner != null){
                // if item is a banner, hide all other view and add the banner
                name.setVisibility(GONE);
                bannerLayout.setVisibility(VISIBLE);
                item.banner.bindToView(bannerLayout);
            }else{
                // if item is not a banner, hide banner view and show normal content
                name.setVisibility(VISIBLE);
                bannerLayout.setVisibility(GONE);
                name.setText(item.name);
            }

        }
        // Return the completed view to render on screen
        return convertView;
    }
}
