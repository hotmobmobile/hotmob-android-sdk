package com.hotmob.android.example;

import android.app.Activity;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;

import com.hotmob.sdk.handler.HotmobHandler;
import com.hotmob.sdk.manager.HotmobManager;

/**
 * Created by Hotmob Ltd. on 2016/01/29.
 */
public class HotmobAndroidSDKExampleBaseActivity extends Activity {
    protected void onStart() {
        super.onStart();
        HotmobHandler.getInstance(this).onStart();
    }

    protected void onStop() {
        super.onStop();
        HotmobHandler.getInstance(this).onStop();
    }

    public void onBackPressed() {
        if (!HotmobHandler.getInstance(this).hotmobBackButtonPressed()){
            super.onBackPressed();
        }else {
            HotmobHandler.getInstance(this).onBackPressed();
        }
    }

    protected void onResume() {
        super.onResume();
        HotmobHandler.getInstance(this).onResume();
    }

    protected void onPause() {
        super.onPause();
        HotmobHandler.getInstance(this).onPause();
    }
}
