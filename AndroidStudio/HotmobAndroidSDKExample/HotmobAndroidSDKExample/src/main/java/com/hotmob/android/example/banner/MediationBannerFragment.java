package com.hotmob.android.example.banner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.hotmob.android.example.R;
import com.hotmob.android.example.banner.list_adapter.MediationListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediationBannerFragment extends Fragment {

    private EditText customIU;
    private ViewGroup bannerContainer;

    public MediationBannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mediation_banner, container, false);

        bannerContainer = root.findViewById(R.id.banner_view);
        customIU = root.findViewById(R.id.custom_iu);
        customIU.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                if (result == EditorInfo.IME_ACTION_DONE){
                    String customCode = customIU.getText().toString();
                    getBanner(customCode);
                }
                return false;
            }
        });

        ListView listView = root.findViewById(R.id.banner_list);

        String[] adCodes = getResources().getStringArray(R.array.mediation_banner);

        MediationListAdapter adapter = new MediationListAdapter(getContext(), new ArrayList<>(Arrays.asList(adCodes)));
        listView.setAdapter(adapter);

        return root;
    }

    private void getBanner(String iu){
        final PublisherAdView adView = new PublisherAdView(getContext());
        adView.setAdUnitId(iu);
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

}
