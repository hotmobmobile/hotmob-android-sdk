package com.hotmob.android.example.banner;


import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.sdk.core.controller.AdController;
import com.hotmob.sdk.core.controller.Banner;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherBannerFragment extends Fragment implements View.OnClickListener{

    private TextView adCodeDisplay, errorMsg;
    private EditText customAdcode;

    private Banner banner;

    public OtherBannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        banner = new Banner.Builder(getContext())
                .setIdentifier("OtherBanner")
                .build();
        banner.setListener(new Banner.Listener() {
            @Override
            public void onAdEvent(AdController.AdEvent event) {
                switch (event) {
                    case START_LOADING:
                        adCodeDisplay.setText(R.string.loading);
                        errorMsg.setText("");
                        break;
                    case LOAD_FAILED:
                        errorMsg.setText(R.string.load_fail);
                        break;
                    case LOAD_TIMEOUT:
                        errorMsg.setText(R.string.load_fail);
                        break;
                    case NO_AD:
                        errorMsg.setText(R.string.noaddcallback);
                        break;
                    case SHOW:
                        adCodeDisplay.setText(banner.getAdCode());
                        break;
                    case HIDE:
                        errorMsg.setText("");
                        adCodeDisplay.setText(R.string.banner_hide);
                        break;
                }
            }
        });
        banner.setDeepLinkListener(new AdController.DeepLinkListener() {
            @Override
            public void onDeepLink(String deepLinkAddress) {
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).changeToInternalFragment(deepLinkAddress);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_other_banner, container, false);

        LinearLayout creativeList = root.findViewById(R.id.creative_list);
        LinearLayout bannerContainer = root.findViewById(R.id.banner_container);
        adCodeDisplay = root.findViewById(R.id.adcode_display);
        errorMsg = root.findViewById(R.id.banner_error);
        customAdcode = root.findViewById(R.id.custom_adcode);
        customAdcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                if (result == EditorInfo.IME_ACTION_DONE){
                    String customCode = customAdcode.getText().toString();
                    // 1. hide the Banner
                    banner.hide();
                    // 2. change ad code
                    banner.setAdCode(customCode);
                    // 3. reload Banner
                    banner.loadAd();
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

        // Set ad code
//        banner.setAdCode(adCodes[0]);

        // Add the Banner view to the Layout holding it
        banner.bindToView(bannerContainer);

        // load ad for display
//        banner.loadAd();

        return root;
    }

    @Override
    public void onClick(View v) {
        // 1. hide the Banner
        banner.hide();
        // 2. change ad code
        banner.setAdCode((String) v.getTag());
        // 3. reload Banner
        banner.loadAd();
    }

    @Override
    public void onDestroy() {
        // destroy instance
        banner.destroy();

        super.onDestroy();
    }
}
