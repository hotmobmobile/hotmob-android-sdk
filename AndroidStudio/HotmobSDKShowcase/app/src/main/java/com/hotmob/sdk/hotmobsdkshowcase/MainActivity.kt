package com.hotmob.sdk.hotmobsdkshowcase

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.hotmob.sdk.hotmobsdkshowcase.banner.BannerShowcaseFragment
import com.hotmob.sdk.hotmobsdkshowcase.banner.listview.BannerInListViewFragment
import com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview.BannerInRecycleViewFragment
import com.hotmob.sdk.hotmobsdkshowcase.databinding.ActivityMainBinding
import com.hotmob.sdk.hotmobsdkshowcase.datacollection.DataCollectionFragment
import com.hotmob.sdk.hotmobsdkshowcase.floating.FloatingBannerShowcaseFragment
import com.hotmob.sdk.hotmobsdkshowcase.floating.listview.FloatingListViewFragment
import com.hotmob.sdk.hotmobsdkshowcase.interstitial.InterstitialShowcaseFragment
import com.hotmob.sdk.hotmobsdkshowcase.videobanner.VideoBannerShowcaseFragment
import com.hotmob.sdk.hotmobsdkshowcase.videointerstitial.VideoInterstitialShowcaseFragment

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.appBar.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

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
            R.id.nav_floating_showcase -> FloatingBannerShowcaseFragment()
            R.id.nav_floating_list -> FloatingListViewFragment()
            R.id.nav_data_collection -> DataCollectionFragment()
            else -> BannerShowcaseFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, targetPage)
            .commit()

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun goToDeepPage(address: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, DeepLinkContentFragment.newInstance(address))
            .addToBackStack(null)
            .commit()
    }

}
