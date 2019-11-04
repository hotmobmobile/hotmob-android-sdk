package com.hotmob.sdk.hotmobsdkshowcase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.hotmob.sdk.hotmobsdkshowcase.banner.BannerShowcaseFragment
import com.hotmob.sdk.hotmobsdkshowcase.banner.listview.BannerInListViewFragment
import com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview.BannerInRecycleViewFragment
import com.hotmob.sdk.hotmobsdkshowcase.datacollection.DataCollectionFragment
import com.hotmob.sdk.hotmobsdkshowcase.interstitial.InterstitialShowcaseFragment
import com.hotmob.sdk.hotmobsdkshowcase.videobanner.VideoBannerShowcaseFragment
import com.hotmob.sdk.hotmobsdkshowcase.videointerstitial.VideoInterstitialShowcaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, BannerShowcaseFragment())
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val targetPage: androidx.fragment.app.Fragment = when (item.itemId) {
            R.id.nav_banner_recycleview -> BannerInRecycleViewFragment()
            R.id.nav_banner_listview -> BannerInListViewFragment()
            R.id.nav_video_banner_showcase -> VideoBannerShowcaseFragment()
            R.id.nav_interstitial_showcase -> InterstitialShowcaseFragment()
            R.id.nav_video_interstitial_showcase -> VideoInterstitialShowcaseFragment()
            R.id.nav_data_collection -> DataCollectionFragment()
            else -> BannerShowcaseFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, targetPage)
            .commit()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun goToDeepPage(address: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, DeepLinkContentFragment.newInstance(address))
            .addToBackStack(null)
            .commit()
    }
}
