package com.hotmob.preloaddemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.hotmob.preloaddemo.home.HomeFragment
import com.hotmob.preloaddemo.home.dummy.DummyItem
import com.hotmob.preloaddemo.MainFragment
import com.hotmob.sdk.ad.HotmobInterstitial
import com.hotmob.sdk.module.reload.HotmobReloadManager

class MainActivity : AppCompatActivity(), HomeFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set Resume Interstitial here, so that this Interstitial will only show when resume app but not launch app
        val interstitial = HotmobInterstitial("ResumeApp", "hotmob_android_popup_inapp")
        HotmobReloadManager.instance.setResumeInterstitial(this, interstitial, false)   // false for not showing ad immediately

        val action: String? = intent?.action
        val data: Uri? = intent?.data
        Log.d("MainActivity", "Intent action: $action, data: $data")

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, MainFragment())
            .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val action: String? = intent?.action
        val data: Uri? = intent?.data
        Log.d("MainActivity", "New intent action: $action, data: $data")
    }

    override fun onListFragmentInteraction(item: DummyItem?) {

    }
}
