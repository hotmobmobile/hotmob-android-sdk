package com.hotmob.android.example.banner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotmob.android.example.BaseFragment;
import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherBannerFragment extends BaseFragment implements View.OnClickListener{

    private HotmobManagerListener hotmobManagerListener;
    private String currentAdCode;

    private TextView adCodeDisplay, errorMsg;
    private LinearLayout bannerContainer;
    private View bannerView;
    private EditText customAdcode;

    public OtherBannerFragment() {
        // Required empty public constructor
        hotmobManagerListener = new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
                OtherBannerFragment.this.bannerView = bannerView;
                bannerContainer.addView(bannerView);
                adCodeDisplay.setText(currentAdCode);
            }

            @Override
            public void didLoadFailed(View bannerView) {
                super.didLoadFailed(bannerView);
                errorMsg.setText(R.string.load_fail);
            }

            @Override
            public void didHideBanner(View bannerView) {
                super.didHideBanner(bannerView);
                adCodeDisplay.setText(R.string.banner_hide);
            }

            @Override
            public void openNoAdCallback(View bannerView) {
                super.openNoAdCallback(bannerView);
                errorMsg.setText(R.string.noaddcallback);
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);
                ((MainActivity) OtherBannerFragment.this.getActivity()).changeToInternalFragment(url);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_other_banner, container, false);

        LinearLayout creativeList = root.findViewById(R.id.creative_list);
        bannerContainer = root.findViewById(R.id.banner_container);
        adCodeDisplay = root.findViewById(R.id.adcode_display);
        errorMsg = root.findViewById(R.id.banner_error);
        customAdcode = root.findViewById(R.id.custom_adcode);
        customAdcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                if (result == EditorInfo.IME_ACTION_DONE){
                    String customCode = customAdcode.getText().toString();
                    getBanner(customCode);
                }
                return false;
            }
        });

        String[] buttonLabels = getResources().getStringArray(R.array.other_creative_actions);
        String[] adCodes = getResources().getStringArray(R.array.other_banner_adcodes);

        for (int i=0;i<buttonLabels.length;i++) {
            String label = buttonLabels[i];
            String adCode = adCodes[i];
            Button button = (Button) inflater.inflate(R.layout.creative_button, container, false);
            button.setText(label);
            button.setTag(adCode);
            button.setOnClickListener(this);
            creativeList.addView(button);
        }
        getBanner(adCodes[0]);

        return root;
    }

    @Override
    public void onClick(View v) {
        getBanner((String) v.getTag());
    }

    private void getBanner(String adCode){
        HotmobManager.getBanner(this, hotmobManagerListener,
                HotmobManager.getScreenWidth(getActivity()), "OtherBanner", adCode);
        currentAdCode = adCode;
        errorMsg.setText("");
    }
}
