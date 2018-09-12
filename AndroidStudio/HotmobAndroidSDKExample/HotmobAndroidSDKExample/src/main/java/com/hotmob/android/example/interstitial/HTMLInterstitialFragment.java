package com.hotmob.android.example.interstitial;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.android.example.interstitial.list_adapter.InterstitialCreativeListAdapter;
import com.hotmob.android.example.interstitial.list_adapter.InterstitialCreativeListItem;
import com.hotmob.sdk.core.controller.AdController;
import com.hotmob.sdk.core.controller.Interstitial;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HTMLInterstitialFragment extends Fragment {

    public HTMLInterstitialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_html_interstitial, container, false);

        ListView listView = root.findViewById(R.id.interstitial_list);

        String[] buttonLabels = getResources().getStringArray(R.array.html_creative_actions_interstitial);
        String[] adCodes = getResources().getStringArray(R.array.html_interstitial_adcodes);

        ArrayList<InterstitialCreativeListItem> creativeListItems = new ArrayList<>();
        for (int i=0;i<buttonLabels.length;i++){
            InterstitialCreativeListItem item = new InterstitialCreativeListItem(buttonLabels[i], adCodes[i]);
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
        final Interstitial interstitial = new Interstitial.Builder(getContext())
                .setIdentifier("HTMLInterstitial")
                .build();
        interstitial.setListener(new AdController.Listener() {
            @Override
            public void onAdEvent(AdController.AdEvent event) {
                switch (event) {
                    case LOAD_FAILED:
                        Toast.makeText(getContext(), "Load fail", Toast.LENGTH_SHORT).show();
                        break;
                    case LOAD_TIMEOUT:
                        Toast.makeText(getContext(), "Load timeout", Toast.LENGTH_SHORT).show();
                        break;
                    case NO_AD:
                        Toast.makeText(getContext(), "No ad", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        interstitial.setDeepLinkListener(new AdController.DeepLinkListener() {
            @Override
            public void onDeepLink(String deepLinkAddress) {
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).changeToInternalFragment(deepLinkAddress);
            }
        });
        interstitial.setAdCode(adCode);
        interstitial.loadAd();
    }
}
