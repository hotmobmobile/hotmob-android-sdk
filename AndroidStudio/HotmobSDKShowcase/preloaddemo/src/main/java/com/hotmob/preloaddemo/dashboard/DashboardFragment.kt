package com.hotmob.preloaddemo.dashboard


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.hotmob.preloaddemo.R
import kotlinx.android.synthetic.main.fragment_dashbroad.*

/**
 * A simple [Fragment] subclass.
 *
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashbroad, container, false)
    }

    override fun onDestroyView() {
        banner.destroy()
        super.onDestroyView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("DashboardFragment", "setUserVisibleHint($isVisibleToUser)")
        banner?.hide()
    }
}
