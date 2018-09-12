package com.hotmob.android.example.autoreload.pagewithnoad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hotmob.android.example.R;
import com.hotmob.sdk.module.autoreload.ReloadManager;
import com.hotmob.sdk.module.location.LocationManager;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AdFragment())
                    .commit();
        }
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
