package com.hotmob.preloaddemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hotmob.preloaddemo.dashboard.DashboardFragment
import com.hotmob.preloaddemo.home.HomeFragment
import com.hotmob.preloaddemo.notification.NotificationFragment

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            1 -> DashboardFragment()
            2 -> NotificationFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}