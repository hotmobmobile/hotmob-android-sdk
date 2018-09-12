package com.hotmob.android.example.compatibility;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.Locale;


public class LagacyActivity extends BaseActivity {
    public final String LOG_TAG = this.getClass().getSimpleName();

    private int fragment_counter = 1;
    private int activity_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lagacy);

        HotmobManager.start(this);
        HotmobManager.setDebug(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            activity_counter = bundle.getInt("activity_counter");
        }else{
            activity_counter = 1;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, LagacyFragment.newInstance(activity_counter, fragment_counter))
                    .commit();
        }

        ((TextView) findViewById(R.id.activity_name)).setText(
                String.format(
                        Locale.ENGLISH,
                        getString(R.string.reload_activity_title),
                        activity_counter
                )
        );

        findViewById(R.id.replace_fragment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, LagacyFragment.newInstance(activity_counter, ++fragment_counter))
                        .addToBackStack(null)
                        .commit();
            }
        });

        findViewById(R.id.add_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LagacyActivity.this, LagacyActivity2.class);
                intent.putExtra("activity_counter", activity_counter+1);
                startActivity(intent);
            }
        });

        HotmobManagerListener listener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);
                Log.d(LOG_TAG, "popup openInternalCallback " + url);
            }

            public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                Log.d(LOG_TAG, "hotmobBannerIsReadyChangeSoundSettings: " + isSoundEnable);
            }
        };

        HotmobManager.getPopup(this, listener, "launch_popup",
                getResources().getStringArray(R.array.html_interstitial_adcodes)[0], true);

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
            fragment_counter--;
        } else {
            super.onBackPressed();
        }
    }

}
