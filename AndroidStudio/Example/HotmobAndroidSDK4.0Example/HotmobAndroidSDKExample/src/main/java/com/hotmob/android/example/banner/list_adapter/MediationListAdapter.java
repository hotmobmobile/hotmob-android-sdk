package com.hotmob.android.example.banner.list_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.hotmob.android.example.R;

import java.util.ArrayList;

/**
 * Created by ken on 10/11/2017.
 */

public class MediationListAdapter extends ArrayAdapter<String> {

    public MediationListAdapter(Context context, ArrayList<String> items){
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        String item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_mediation_banner, parent, false);
        }
        // Lookup view for data population
        TextView adCode = convertView.findViewById(R.id.adcode);
        final ViewGroup bannerContainer = convertView.findViewById(R.id.banner_view);
        // Populate the data into the template view using the data object
        adCode.setText(item);
        if (bannerContainer.getChildCount() == 0) {
            final PublisherAdView adView = new PublisherAdView(getContext());
            adView.setAdUnitId(item);
            adView.setAdSizes(AdSize.SMART_BANNER);
            AdListener mAdListener = new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    bannerContainer.addView(adView);
                }
            };
            adView.setAdListener(mAdListener);
            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
