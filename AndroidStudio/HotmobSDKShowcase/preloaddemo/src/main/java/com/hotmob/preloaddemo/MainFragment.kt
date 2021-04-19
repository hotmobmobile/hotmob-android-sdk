package com.hotmob.preloaddemo


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hotmob.preloaddemo.databinding.FragmentMainBinding
import com.hotmob.sdk.module.reload.HotmobReloadManager
import com.hotmob.sdk.module.reload.ShowPreloadedAdEvent


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
                HotmobReloadManager.instance.showPreloadInterstitial(this, "Interpage") { event, deeplink ->
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
                        ShowPreloadedAdEvent.DEEP_LINK -> {
                            Toast.makeText(this, "Deep link $deeplink Received", Toast.LENGTH_SHORT).show()
                            binding.viewPager.currentItem = 2
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
                binding.viewPager.currentItem = 0
            }
            R.id.navigation_dashboard -> {
                binding.viewPager.currentItem = 1
            }
            R.id.navigation_notifications -> {
                binding.viewPager.currentItem = 2
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        binding.viewPager.adapter = MainPagerAdapter(childFragmentManager)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(p0: Int) {
                // Special code here to avoid showing ad when swiping page
                // set currentItemId before changing navigation so showInterstitialBeforeChangePage()
                // will not call ad
                when (p0) {
                    1 -> {
                        currentItemId = R.id.navigation_dashboard
                        binding.navigation.selectedItemId = R.id.navigation_dashboard
                    }
                    2 -> {
                        currentItemId = R.id.navigation_notifications
                        binding.navigation.selectedItemId = R.id.navigation_notifications
                    }
                    else -> {
                        currentItemId = R.id.navigation_home
                        binding.navigation.selectedItemId = R.id.navigation_home
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
