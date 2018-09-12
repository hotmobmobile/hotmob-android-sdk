package com.hotmob.android.example.interstitial.list_adapter;

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

/**
 * Created by ken on 10/11/2017.
 */

public class InterstitialCreativeListAdapter extends ArrayAdapter<InterstitialCreativeListItem> {

    public InterstitialCreativeListAdapter(Context context, ArrayList<InterstitialCreativeListItem> items){
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        InterstitialCreativeListItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_popup_creative, parent, false);
        }
        if (item != null) {
            // Lookup view for data population
            TextView name = convertView.findViewById(R.id.name);
            TextView adCode = convertView.findViewById(R.id.adcode);
            // Populate the data into the template view using the data object
            if (item.name != null && !item.name.isEmpty()) {
                name.setVisibility(View.VISIBLE);
                name.setText(item.name);
            } else {
                name.setVisibility(View.GONE);
            }
            adCode.setText(item.adCode);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
