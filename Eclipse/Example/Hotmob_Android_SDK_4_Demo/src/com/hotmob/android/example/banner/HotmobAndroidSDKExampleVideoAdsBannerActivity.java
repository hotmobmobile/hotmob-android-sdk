package com.hotmob.android.example.banner;

import android.os.Bundle;

import com.hotmob.android.example.HotmobAndroidSDKExampleBaseActivity;
import com.hotmob.android.example.R;
import com.hotmob.android.example.R.layout;
import com.hotmob.sdk.handler.HotmobHandler;

public class HotmobAndroidSDKExampleVideoAdsBannerActivity extends HotmobAndroidSDKExampleBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotmob_android_sdkexample_video_ads_banner);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
