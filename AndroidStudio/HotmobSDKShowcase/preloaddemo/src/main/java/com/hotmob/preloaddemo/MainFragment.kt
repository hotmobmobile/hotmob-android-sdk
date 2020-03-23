package com.hotmob.preloaddemo


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hotmob.sdk.module.reload.HotmobReloadManager
import com.hotmob.sdk.module.reload.ShowPreloadedAdEvent
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    private var currentItemId: Int = R.id.navigation_home   // for keep tracking current page

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        showInterstitialBeforeChangePage(item.itemId)
        return@OnNavigationItemSelectedListener true
    }

    /**
     * Determine if the tab action is a page change and if it does, should show an Interstitial or not.
     */
    private fun showInterstitialBeforeChangePage(itemId: Int) {
        Log.d("Main", "showInterstitialBeforeChangePage itemId $itemId")
        if (itemId != currentItemId) {
            currentItemId = itemId
            context?.run {
                HotmobReloadManager.instance.showPreloadInterstitial(this, "Interpage") { event ->
                    Log.d("Main", "showPreloadInterstitial status $event")
                    when (event) {
                        ShowPreloadedAdEvent.SHOW -> {
                            // do nothing to wait for ad show
                        }
                        ShowPreloadedAdEvent.SHOWING -> {
                            // do nothing as ad already showing
                        }
                        ShowPreloadedAdEvent.FAIL,
                        ShowPreloadedAdEvent.NO_AD,
                        ShowPreloadedAdEvent.HIDE -> {
                            // no ad or ad is closed, can change page now
                            changePageTo(itemId)
                        }
                    }
                }
            }
        }
    }

    private fun changePageTo(itemId: Int) {
        Log.d("Main", "changePageTo itemId $itemId")
        when (itemId) {
            R.id.navigation_home -> {
                viewPager.currentItem = 0
            }
            R.id.navigation_dashboard -> {
                viewPager.currentItem = 1
            }
            R.id.navigation_notifications -> {
                viewPager.currentItem = 2
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        viewPager.adapter = MainPagerAdapter(childFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(p0: Int) {
                // Special code here to avoid showing ad when swiping page
                // set currentItemId before changing navigation so showInterstitialBeforeChangePage()
                // will not call ad
                when (p0) {
                    1 -> {
                        currentItemId = R.id.navigation_dashboard
                        navigation.selectedItemId = R.id.navigation_dashboard
                    }
                    2 -> {
                        currentItemId = R.id.navigation_notifications
                        navigation.selectedItemId = R.id.navigation_notifications
                    }
                    else -> {
                        currentItemId = R.id.navigation_home
                        navigation.selectedItemId = R.id.navigation_home
                    }
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }
        })
    }
}
