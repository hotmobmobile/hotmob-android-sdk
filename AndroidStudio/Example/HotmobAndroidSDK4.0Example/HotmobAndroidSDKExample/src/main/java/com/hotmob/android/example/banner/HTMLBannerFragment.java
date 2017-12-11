package com.hotmob.android.example.banner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class HTMLBannerFragment extends BaseFragment implements View.OnClickListener{

    private HotmobManagerListener hotmobManagerListener;
    private String currentAdCode;

    private TextView adCodeDisplay, errorMsg;
    private LinearLayout bannerContainer;
    private View bannerView;

    public HTMLBannerFragment() {
        // Required empty public constructor
        hotmobManagerListener = new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
                HTMLBannerFragment.this.bannerView = bannerView;
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
                ((MainActivity) HTMLBannerFragment.this.getActivity()).changeToInternalFragment(url);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_html_banner, container, false);

        LinearLayout directClickCreativeList = root.findViewById(R.id.creative_list_direct_click);
        LinearLayout HTMLCreativeList = root.findViewById(R.id.creative_list_html_mraid);
        bannerContainer = root.findViewById(R.id.banner_container);
        adCodeDisplay = root.findViewById(R.id.adcode_display);
        errorMsg = root.findViewById(R.id.banner_error);

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
        getBanner(directClickAdCodes[0]);

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

        return root;
    }

    @Override
    public void onClick(View v) {
        getBanner((String) v.getTag());
    }

    private void getBanner(String adCode){
        HotmobManager.getBanner(this, hotmobManagerListener,
                HotmobManager.getScreenWidth(getActivity()), "HTMLBanner", adCode);
        currentAdCode = adCode;
        errorMsg.setText("");
    }
}
