package com.hotmob.android.example.interstitial;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.hotmob.android.example.R;
import com.hotmob.android.example.interstitial.list_adapter.InterstitialCreativeListAdapter;
import com.hotmob.android.example.interstitial.list_adapter.InterstitialCreativeListItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediationInterstitialFragment extends Fragment {

    private EditText customIU;

    public MediationInterstitialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mediation_popup, container, false);

        customIU = root.findViewById(R.id.custom_iu);
        customIU.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                if (result == EditorInfo.IME_ACTION_DONE){
                    String customCode = customIU.getText().toString();
                    getInterstitial(customCode);
                }
                return false;
            }
        });

        ListView listView = root.findViewById(R.id.interstitial_list);

        String[] adCodes = getResources().getStringArray(R.array.mediation_interstitial);

        ArrayList<InterstitialCreativeListItem> creativeListItems = new ArrayList<>();
        for (int i=0;i<adCodes.length;i++){
            InterstitialCreativeListItem item = new InterstitialCreativeListItem("", adCodes[i]);
            creativeListItems.add(item);
        }

        final InterstitialCreativeListAdapter adapter = new InterstitialCreativeListAdapter(getContext(), creativeListItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InterstitialCreativeListItem clickedItem = adapter.getItem(position);
                if (clickedItem != null)
                    getInterstitial(clickedItem.adCode);
                else
                    Toast.makeText(getContext(), R.string.interstitial_item_null, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void getInterstitial(String adCode){
        final PublisherInterstitialAd interstitialAd = new PublisherInterstitialAd(getActivity());
        interstitialAd.setAdUnitId(adCode);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }
}
