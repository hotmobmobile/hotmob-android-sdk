package com.hotmob.android.example;

import android.support.v7.app.AppCompatActivity;

import com.hotmob.sdk.handler.HotmobHandler;
import com.hotmob.sdk.manager.HotmobManager;

/**
 * This is a recommended coding style to include all essential lifecycle event for HotmobSDK to
 * perform auto reload.
 * You can use other implementation method to include these 5 HotmobHandler callings into your
 * Activity. Be sure that you have include ALL these into ALL of your Activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if (HotmobManager.isHotmobBannerOverlappedOnWindow(this) || HotmobHandler.getInstance(this).hotmobBackButtonPressed()) {
            HotmobHandler.getInstance(this).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        HotmobHandler.getInstance(this).onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        HotmobHandler.getInstance(this).onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HotmobHandler.getInstance(this).onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        HotmobHandler.getInstance(this).onPause();
    }
}
