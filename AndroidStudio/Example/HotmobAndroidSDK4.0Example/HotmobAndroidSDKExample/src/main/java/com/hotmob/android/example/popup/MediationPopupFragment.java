package com.hotmob.android.example.popup;


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
import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.R;
import com.hotmob.android.example.popup.list_adapter.PopupCreativeListAdapter;
import com.hotmob.android.example.popup.list_adapter.PopupCreativeListItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediationPopupFragment extends BaseFragment{

    private EditText customIU;

    public MediationPopupFragment() {
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
                    getPopup(customCode);
                }
                return false;
            }
        });

        ListView listView = root.findViewById(R.id.popup_list);

        String[] adCodes = getResources().getStringArray(R.array.mediation_popup);

        ArrayList<PopupCreativeListItem> creativeListItems = new ArrayList<>();
        for (int i=0;i<adCodes.length;i++){
            PopupCreativeListItem item = new PopupCreativeListItem("", adCodes[i]);
            creativeListItems.add(item);
        }

        final PopupCreativeListAdapter adapter = new PopupCreativeListAdapter(getContext(), creativeListItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupCreativeListItem clickedItem = adapter.getItem(position);
                if (clickedItem != null)
                    getPopup(clickedItem.adCode);
                else
                    Toast.makeText(getContext(), R.string.popup_item_null, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void getPopup(String adCode){
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
