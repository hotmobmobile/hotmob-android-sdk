package com.hotmob.android.example.banner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotmob.android.example.MainActivity;
import com.hotmob.android.example.R;
import com.hotmob.sdk.core.controller.AdController;
import com.hotmob.sdk.core.controller.Banner;

/**
 * A simple {@link Fragment} subclass.
 */
public class HTMLBannerFragment extends Fragment implements View.OnClickListener{

    private TextView adCodeDisplay, errorMsg;
    private Banner banner;

    private int customWidth;

    public HTMLBannerFragment() {
        // Required empty public constructor
        customWidth = -1;
    }

    public static HTMLBannerFragment newInstance(int customWidth) {
        HTMLBannerFragment fragment = new HTMLBannerFragment();
        fragment.customWidth = customWidth;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Banner Controller Object
        Banner.Builder builder = new Banner.Builder(getContext())
                .setIdentifier("HTMLBanner");
        if (customWidth > 0) {
            builder = builder.setWidth(customWidth);
        }
        banner = builder.build();

        banner.setListener(new Banner.Listener() {
            @Override
            public void onAdEvent(AdController.AdEvent event) {
                switch (event) {
                    case START_LOADING:
                        errorMsg.setText("");
                        adCodeDisplay.setText(R.string.loading);
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
        View root = inflater.inflate(R.layout.fragment_html_banner, container, false);

        LinearLayout directClickCreativeList = root.findViewById(R.id.creative_list_direct_click);
        LinearLayout HTMLCreativeList = root.findViewById(R.id.creative_list_html_mraid);
        RelativeLayout bannerContainer = root.findViewById(R.id.banner_container);
        adCodeDisplay = root.findViewById(R.id.adcode_display);
        errorMsg = root.findViewById(R.id.banner_error);

        root.findViewById(R.id.width_full_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, HTMLBannerFragment.newInstance(-1))
                            .commit();
                }
            }
        });
        root.findViewById(R.id.width_320_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, HTMLBannerFragment.newInstance(320))
                            .commit();
                }
            }
        });
        root.findViewById(R.id.width_200_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, HTMLBannerFragment.newInstance(200))
                            .commit();
                }
            }
        });

        String[] directClickButtonLabels = getResources().getStringArray(R.array.creative_actions);
        String[] directClickAdCodes = getResources().getStringArray(R.array.image_banner_adcodes);

        for (int i=0;i<directClickButtonLabels.length;i++) {
            String label = directClickButtonLabels[i];
            String adCode = directClickAdCodes[i];
            Button button = (Button) inflater.inflate(R.layout.creative_button, container, false);
            button.setText(label);
            button.setTag(adCode);
            button.setOnClickListener(this);
            directClickCreativeList.addView(button);
        }

        String firstAdCode = directClickAdCodes[0];

        directClickButtonLabels = getResources().getStringArray(R.array.html_creative_actions);
        directClickAdCodes = getResources().getStringArray(R.array.html_banner_adcodes);

        for (int i=0;i<directClickButtonLabels.length;i++) {
            String label = directClickButtonLabels[i];
            String adCode = directClickAdCodes[i];
            Button button = (Button) inflater.inflate(R.layout.creative_button, container, false);
            button.setText(label);
            button.setTag(adCode);
            button.setOnClickListener(this);
            HTMLCreativeList.addView(button);
        }

        // Set ad code
        banner.setAdCode(firstAdCode);

        // Add the Banner view to the Layout holding it
        banner.bindToView(bannerContainer);

        // load ad for display
        banner.loadAd();

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
