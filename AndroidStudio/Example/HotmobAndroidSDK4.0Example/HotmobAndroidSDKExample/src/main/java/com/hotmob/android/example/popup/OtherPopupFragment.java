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

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.android.example.popup.list_adapter.PopupCreativeListAdapter;
import com.hotmob.android.example.popup.list_adapter.PopupCreativeListItem;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherPopupFragment extends BaseFragment{

    private HotmobManagerListener hotmobManagerListener;
    private EditText customAdcode;

    public OtherPopupFragment() {
        // Required empty public constructor
        hotmobManagerListener = new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
            }

            @Override
            public void didLoadFailed(View bannerView) {
                super.didLoadFailed(bannerView);
                Toast.makeText(getContext(), R.string.load_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void openNoAdCallback(View bannerView) {
                super.openNoAdCallback(bannerView);
                Toast.makeText(getContext(), R.string.noaddcallback, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);
                ((MainActivity) OtherPopupFragment.this.getActivity()).changeToInternalFragment(url);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_other_popup, container, false);

        ListView listView = root.findViewById(R.id.popup_list);
        customAdcode = root.findViewById(R.id.custom_adcode);
        customAdcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                if (result == EditorInfo.IME_ACTION_DONE){
                    String customCode = customAdcode.getText().toString();
                    getPopup(customCode);
                }
                return false;
            }
        });

        String[] buttonLabels = getResources().getStringArray(R.array.other_creative_actions_popup);
        String[] adCodes = getResources().getStringArray(R.array.other_popup_adcodes);

        ArrayList<PopupCreativeListItem> creativeListItems = new ArrayList<>();
        for (int i=0;i<buttonLabels.length;i++){
            PopupCreativeListItem item = new PopupCreativeListItem(buttonLabels[i], adCodes[i]);
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
        HotmobManager.getPopup(getActivity(), hotmobManagerListener,"otherPopup", adCode, false);
    }
}
