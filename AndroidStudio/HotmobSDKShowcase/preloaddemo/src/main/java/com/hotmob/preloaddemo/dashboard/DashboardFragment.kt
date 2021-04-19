package com.hotmob.preloaddemo.dashboard


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.hotmob.preloaddemo.databinding.FragmentDashbroadBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashbroadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashbroadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.banner.destroy()
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        binding.banner.hide()
    }
}
