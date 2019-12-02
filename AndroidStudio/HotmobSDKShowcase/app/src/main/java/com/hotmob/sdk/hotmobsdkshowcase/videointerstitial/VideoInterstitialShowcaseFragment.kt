package com.hotmob.sdk.hotmobsdkshowcase.videointerstitial

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobInterstitial
import com.hotmob.sdk.hotmobsdkshowcase.MainActivity
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.interstitial.InterstitialItem
import com.hotmob.sdk.hotmobsdkshowcase.interstitial.InterstitialItemAdapter
import kotlinx.android.synthetic.main.fragment_interstitialitem_list.*

/**
 * A fragment representing a list of Items.
 */
class VideoInterstitialShowcaseFragment : androidx.fragment.app.Fragment(), InterstitialItemAdapter.ItemClickListener, HotmobAdDeepLinkListener {

    private val interstitial = HotmobInterstitial("ShowcaseInterstitial", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_interstitialitem_list, container, false)
        interstitial.deepLinkListener = this

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customAdCode.visibility = View.GONE

        // Set the adapter
        with(list) {
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
