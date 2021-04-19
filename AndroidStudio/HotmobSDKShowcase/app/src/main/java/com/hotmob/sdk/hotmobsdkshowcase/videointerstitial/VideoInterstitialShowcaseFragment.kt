package com.hotmob.sdk.hotmobsdkshowcase.videointerstitial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobInterstitial
import com.hotmob.sdk.hotmobsdkshowcase.MainActivity
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentInterstitialitemListBinding
import com.hotmob.sdk.hotmobsdkshowcase.interstitial.InterstitialItem
import com.hotmob.sdk.hotmobsdkshowcase.interstitial.InterstitialItemAdapter

/**
 * A fragment representing a list of Items.
 */
class VideoInterstitialShowcaseFragment : androidx.fragment.app.Fragment(), InterstitialItemAdapter.ItemClickListener, HotmobAdDeepLinkListener {
    private var _binding: FragmentInterstitialitemListBinding? = null
    private val binding get() = _binding!!

    private val interstitial = HotmobInterstitial("ShowcaseInterstitial", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInterstitialitemListBinding.inflate(inflater, container, false)
        interstitial.deepLinkListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customAdCode.visibility = View.GONE

        // Set the adapter
        with(binding.list) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

            val interstitialArray = mutableListOf<InterstitialItem>()

            val clickActionList = resources.getStringArray(R.array.video_click_actions)
            val clickActionAdCodeList = resources.getStringArray(R.array.video_interstitial_click_action_adcodes)
            for (i in clickActionList.indices) {
                interstitialArray.add(
                    InterstitialItem(
                        name = clickActionList[i],
                        adCode = clickActionAdCodeList[i]
                    ))
            }

            adapter = InterstitialItemAdapter(
                interstitialArray,
                this@VideoInterstitialShowcaseFragment
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitial.deepLinkListener = null
    }

    override fun onItemClick(interstitialItem: InterstitialItem) {
        Log.d("InterstitialShowcase", "${interstitialItem.name} clicked.")
        val c = context
        if (c != null) {
            interstitial.adCode = interstitialItem.adCode
            interstitial.loadAd(c)
        }
    }

    override fun onDeepLink(deepLink: String) {
        if (context is MainActivity) {
            (context as MainActivity).goToDeepPage(deepLink)
        }
    }
}
