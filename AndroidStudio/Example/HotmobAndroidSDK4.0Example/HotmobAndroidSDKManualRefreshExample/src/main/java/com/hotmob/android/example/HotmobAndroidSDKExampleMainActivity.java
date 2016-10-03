package com.hotmob.android.example;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

public class HotmobAndroidSDKExampleMainActivity extends HotmobAndroidSDKExampleBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotmob_android_sdkexample_main);

        DisplayMetrics displaySize = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaySize);

        HotmobManager.start(this);
        HotmobManager.setDebug(true);

        HotmobManagerListener listener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
            }
        };

        // TODO: Call launch popup
        AdHelper.getPopup(this, "launch_popup", "hotmob_android_example_launch_popup", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hotmob_android_sdkexample_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
