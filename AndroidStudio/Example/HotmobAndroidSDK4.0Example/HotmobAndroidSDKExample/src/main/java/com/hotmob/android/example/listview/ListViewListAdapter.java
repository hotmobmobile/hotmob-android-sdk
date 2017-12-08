package com.hotmob.android.example.listview;

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
 * Created by ken on 10/11/2017.
 */

public class ListViewListAdapter extends ArrayAdapter<ListViewListItem> {

    public ListViewListAdapter(Context context, ArrayList<ListViewListItem> items){
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
            if (item.bannerLayout != null){
                // if item is a banner, hide all other view and add the banner
                name.setVisibility(GONE);
                bannerLayout.setVisibility(VISIBLE);
                bannerLayout.addView(item.bannerLayout);
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
