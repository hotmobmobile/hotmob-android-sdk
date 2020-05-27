package com.hotmob.preloaddemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.hotmob.sdk.ad.*
import com.hotmob.sdk.module.reload.HotmobReloadManager

class SplashActivity : AppCompatActivity(), HotmobAdListener, HotmobAdDeepLinkListener,
    LifecycleObserver {

    private val interstitial = HotmobInterstitial("LaunchApp", "hotmob_android_google_ad", false)
    private var selfLoadingComplete = false
    private var interstitialDeepLink = ""
    private var goToMainAfterResume = false
    private var isAppActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        interstitial.listener = this
        interstitial.deepLinkListener = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onResume() {
        super.onResume()
        if (goToMainAfterResume) {
            // For case of Interstitial already show before
            changeToMainPage()
        } else if (interstitial.adState == HotmobAdState.INIT) {
            // Preload ad
            interstitial.loadAd(this)

            // demonstrate loading time
            Handler(Looper.getMainLooper()).postDelayed({
                selfLoadingComplete = true
                shouldShowInterstitial()
            }, 3000)
        }
    }

    override fun onDestroy() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        super.onDestroy()
    }

    private fun shouldShowInterstitial() {
        Log.d("Splash", "shouldShowInterstitial $selfLoadingComplete")
        // Determine showing Ad or switching page
        if (interstitial.adState == HotmobAdState.LOADED && selfLoadingComplete) {
            interstitial.showAd(this)
        } else if (interstitial.adState == HotmobAdState.NO_AD && selfLoadingComplete) {
            changeToMainPage()
        }
    }

    private fun changeToMainPage() {
        Log.d("Splash", "changeToMainPage")
        interstitial.listener = null
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onAdEvent(adEvent: HotmobAdEvent) {
        Log.d("Splash", "on event $adEvent")
        when (adEvent) {
            HotmobAdEvent.LOADED -> {
                shouldShowInterstitial()
            }
            HotmobAdEvent.NO_AD -> {
                shouldShowInterstitial()
            }
            HotmobAdEvent.HIDE -> {
                // delay 500ms to wait in case of deep link click or switching app by ad landing
                // deep link event is fired right after hide event
                Handler(Looper.getMainLooper()).postDelayed({
                    // Deep link action here
                    if (!interstitialDeepLink.isBlank())
                        Toast.makeText(this, "Deep link $interstitialDeepLink Received", Toast.LENGTH_SHORT).show()
                    // Check app active
                    if (isAppActive) {
                        // Safe to change page
                        changeToMainPage()
                    } else {
                        // Should not open new Activity when app is not active, wait for next resume app
                        goToMainAfterResume = true
                    }
                }, 500)
            }
            else -> {}
        }
    }

    override fun onDeepLink(deepLink: String) {
        Log.d("Splash", "on deep link $deepLink")
        interstitialDeepLink = deepLink
    }

    // Track App activity
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Log.d("Splash", "App in background")
        isAppActive = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Log.d("Splash", "App in foreground")
        isAppActive = true
    }
}
