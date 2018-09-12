package com.hotmob.android.example.autoreload.disableinterstitialresume;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hotmob.android.example.R;
import com.hotmob.sdk.module.autoreload.ReloadManager;
import com.hotmob.sdk.module.location.LocationManager;

public class NoResumeInterstitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_resume_interstitial);

        // disable resume Interstitial
        ReloadManager.disableResumeInterstitial();
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

        // enable resume Interstitial again when page close
        ReloadManager.enableResumeInterstitial();
    }
}
