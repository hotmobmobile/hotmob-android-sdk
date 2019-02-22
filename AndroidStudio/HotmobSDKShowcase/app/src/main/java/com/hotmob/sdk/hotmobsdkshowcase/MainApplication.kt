package com.hotmob.sdk.hotmobsdkshowcase

import android.app.Application
import com.hotmob.sdk.HotmobSDKApplication
import com.hotmob.sdk.settings.HotmobSettingsManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        HotmobSDKApplication().onCreate(this)
        HotmobSettingsManager.debug = true
    }
}