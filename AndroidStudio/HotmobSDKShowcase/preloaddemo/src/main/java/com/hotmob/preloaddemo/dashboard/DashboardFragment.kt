package com.hotmob.preloaddemo.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.hotmob.preloaddemo.R

import com.hotmob.preloaddemo.databinding.FragmentDashbroadBinding
import com.hotmob.sdk.ad.HotmobFloating

/**
 * A simple [Fragment] subclass.
 *
 */
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashbroadBinding? = null
    private val binding get() = _binding!!
    private var floating: HotmobFloating? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashbroadBinding.inflate(inflater, container, false)
        floating?.hide()

        if(floating == null) {
            floating = HotmobFloating(requireContext(), (614).toFloat())
            floating?.identifier = "Floating"
            floating?.adCode = "hotmob_android_banner_standard"
            val contentLayout = activity?.findViewById(R.id.content) as View
            print("view.height : " + view?.height)
            floating?.bindToView(binding.root as ViewGroup)
        } else {
            floating?.adCode = "hotmob_android_banner_standard"
        }
        floating?.loadAd()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onDestroyView() {
        binding.banner.destroy()
        floating?.destroy()
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        binding.banner.hide()
        floating?.hide(false)
    }
}
