package com.hotmob.android.example.autoreload.launchinterstitial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.android.example.R;
import com.hotmob.sdk.core.controller.Banner;
import com.hotmob.sdk.core.controller.Interstitial;
import com.hotmob.sdk.module.autoreload.ReloadManager;
import com.hotmob.sdk.module.location.LocationManager;

public class Page1Activity extends AppCompatActivity {

    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        findViewById(R.id.open_page2_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page1Activity.this, Page2Activity.class);
                startActivity(intent);
            }
        });

        // Create launch app Interstitial
        Interstitial interstitial = new Interstitial.Builder(this)
                .setIdentifier("LaunchInterstitial")
                .build();
        interstitial.setAdCode(getResources().getStringArray(R.array.html_interstitial_adcodes)[0]);

        ReloadManager.setResumeInterstitial(interstitial);

        // Create Banner
        ViewGroup bottomBannerLayout = findViewById(R.id.banner_container);

        if (banner == null) {
            // Create Banner Controller Object
            banner = new Banner.Builder(this)
                    .setIdentifier("Pager1Banner")
                    .build();

            // Set ad code
            banner.setAdCode(getResources().getStringArray(R.array.image_banner_adcodes)[0]);

            // set the Banner to page "ViewPage(N)", this name should be sync in parent
            ReloadManager.setAutoReload(banner, this);
        }

        // Add the Banner view to the Layout holding it
        banner.bindToView(bottomBannerLayout);

        // NO NEED to call loadAd() as ReloadManager will handle it
    }

    @Override
    protected void onResume() {
        super.onResume();

        // reload manager
        ReloadManager.setCurrentPage(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // reload manager
        ReloadManager.activityOnStart(this);

        // Location Manager
        LocationManager.activityOnStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // reload manager
        ReloadManager.activityOnStop();

        // Location Manager
        LocationManager.activityOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // reload manager
        ReloadManager.pageOnDestroy(this);
    }
}
