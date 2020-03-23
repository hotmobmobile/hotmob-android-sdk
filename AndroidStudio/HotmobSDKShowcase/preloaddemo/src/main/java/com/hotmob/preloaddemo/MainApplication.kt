package com.hotmob.preloaddemo

import android.app.Application
import com.hotmob.sdk.HotmobSDKApplication
import com.hotmob.sdk.module.reload.HotmobReloadManager
import com.hotmob.sdk.settings.HotmobSettingsManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HotmobSDKApplication().onCreate(this)

        HotmobSettingsManager.debug = true
        // set Preload Interstitial
        HotmobReloadManager.instance.setPreloadInterstitial("Interpage", "hotmob_android_google_ad")
    }
}