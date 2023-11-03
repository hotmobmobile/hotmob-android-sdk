package com.hotmob.sdk.hotmobsdkshowcase.interstitial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobAdEvent
import com.hotmob.sdk.ad.HotmobAdListener
import com.hotmob.sdk.ad.HotmobInterstitial
import com.hotmob.sdk.hotmobsdkshowcase.MainActivity
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentInterstitialitemListBinding

/**
 * A fragment representing a list of Items.
 */
class InterstitialShowcaseFragment : androidx.fragment.app.Fragment(),
    InterstitialItemAdapter.ItemClickListener,
    HotmobAdListener,
    HotmobAdDeepLinkListener {

    private val interstitial = HotmobInterstitial("ShowcaseInterstitial", "")
    private var _binding: FragmentInterstitialitemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInterstitialitemListBinding.inflate(inflater, container, false)
        interstitial.listener = this
        interstitial.deepLinkListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        with(binding.list) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

            val interstitialArray = mutableListOf<InterstitialItem>()

            val clickActionList = resources.getStringArray(R.array.click_actions)
            val clickActionAdCodeList = resources.getStringArray(R.array.interstitial_click_action_adcodes)
            for (i in clickActionList.indices) {
                interstitialArray.add(
                    InterstitialItem(
                        name = clickActionList[i],
                        adCode = clickActionAdCodeList[i]
                    ))
            }

            val mediateList = resources.getStringArray(R.array.mediate_interstitial)
            val mediateAdCodeList = resources.getStringArray(R.array.mediate_interstitial_adcode)
            for (i in mediateList.indices) {
                interstitialArray.add(
                    InterstitialItem(
                        name = mediateList[i],
                        adCode = mediateAdCodeList[i]
                    ))
            }

            adapter = InterstitialItemAdapter(
                interstitialArray,
                this@InterstitialShowcaseFragment
            )
        }

        binding.customAdCode.setOnEditorActionListener { _, actionId, _ ->
            val result = actionId and EditorInfo.IME_MASK_ACTION
            if (result == EditorInfo.IME_ACTION_DONE) {
                val customCode = binding.customAdCode.text.toString()
                // 1. hide the Banner
                onItemClick(InterstitialItem("Custom", customCode))
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitial.listener = null
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

    override fun onAdEvent(adEvent: HotmobAdEvent) {
        if (adEvent == HotmobAdEvent.NO_AD) {
            Toast.makeText(context, "No ad returned", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDeepLink(deepLink: String) {
        if (context is MainActivity) {
            (context as MainActivity).goToDeepPage(deepLink)
        }
    }
}
