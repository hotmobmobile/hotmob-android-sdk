package com.hotmob.android.example.autoreload.disableinterstitialresume;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hotmob.android.example.R;
import com.hotmob.sdk.core.controller.Interstitial;
import com.hotmob.sdk.module.autoreload.ReloadManager;
import com.hotmob.sdk.module.location.LocationManager;

public class DisableInterstitialDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disable_interstitial_demo);

        findViewById(R.id.skip_resume_interstitial_once).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call this before going out app
                ReloadManager.disableResumeInterstitialOnce();

                // go out app
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.page_with_no_resume_interstitial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisableInterstitialDemoActivity.this, NoResumeInterstitialActivity.class);
                startActivity(intent);
            }
        });

        // Create launch app Interstitial
        Interstitial interstitial = new Interstitial.Builder(this)
                .setIdentifier("LaunchInterstitial")
                .build();
        interstitial.setAdCode(getResources().getStringArray(R.array.html_interstitial_adcodes)[0]);

        ReloadManager.setResumeInterstitial(interstitial);
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
}
