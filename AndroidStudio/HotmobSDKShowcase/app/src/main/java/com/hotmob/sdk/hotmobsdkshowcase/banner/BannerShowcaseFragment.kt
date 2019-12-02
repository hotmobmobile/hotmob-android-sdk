package com.hotmob.sdk.hotmobsdkshowcase.banner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobAdEvent
import com.hotmob.sdk.ad.HotmobAdListener
import com.hotmob.sdk.hotmobsdkshowcase.MainActivity
import com.hotmob.sdk.hotmobsdkshowcase.R
import kotlinx.android.synthetic.main.fragment_banner_showcase.*

class BannerShowcaseFragment : androidx.fragment.app.Fragment(), View.OnClickListener, HotmobAdListener, HotmobAdDeepLinkListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banner_showcase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickActionList = resources.getStringArray(R.array.click_actions)
        val clickActionAdCodeList = resources.getStringArray(R.array.banner_click_action_adcodes)
        for (i in clickActionList.indices) {
            val button = Button(context)
            button.text = clickActionList[i]
            button.tag = clickActionAdCodeList[i]
            button.setOnClickListener(this)
            clickActions.addView(button)
        }

        val htmlCreativeList = resources.getStringArray(R.array.banner_html)
        val htmlCreativeAdCodeList = resources.getStringArray(R.array.banner_html_adcodes)
        for (i in htmlCreativeList.indices) {
            val button = Button(context)
            button.text = htmlCreativeList[i]
            button.tag = htmlCreativeAdCodeList[i]
            button.setOnClickListener(this)
            htmlCreative.addView(button)
        }

        customAdCode.setOnEditorActionListener { _, actionId, _ ->
            val result = actionId and EditorInfo.IME_MASK_ACTION
            if (result == EditorInfo.IME_ACTION_DONE) {
                val customCode = customAdCode.text.toString()
                // 1. hide the Banner
                banner.hide()
                // 2. change ad code
                banner.adCode = customCode
                // 3. reload Banner
                banner.loadAd()
            }
            false
        }
        banner.listener = this
        banner.deepLinkListener = this
    }

    override fun onDestroyView() {
        banner.destroy()
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        banner.hide()
        currentAdCode.text = v!!.tag.toString()
        banner.adCode = currentAdCode.text.toString()
        banner.loadAd()
    }

    @SuppressLint("SetTextI18n")
    override fun onAdEvent(adEvent: HotmobAdEvent) {
        when (adEvent) {
            HotmobAdEvent.START_LOADING -> bannerStatus.text = "Start Loading"
            HotmobAdEvent.LOADED -> bannerStatus.text = "Loaded"
            HotmobAdEvent.NO_AD -> bannerStatus.text = "No ad returned"
            HotmobAdEvent.SHOW -> bannerStatus.text = "Showing"
            HotmobAdEvent.HIDE -> bannerStatus.text = "Hidden"
            HotmobAdEvent.RESIZE -> Toast.makeText(context, "Banner resized", Toast.LENGTH_SHORT).show()
            HotmobAdEvent.VIDEO_MUTE -> Toast.makeText(context, "Ad video mute", Toast.LENGTH_SHORT).show()
            HotmobAdEvent.VIDEO_UNMUTE -> Toast.makeText(context, "Ad video unmute", Toast.LENGTH_SHORT).show()
            else -> return
        }
    }

    override fun onDeepLink(deepLink: String) {
        if (context is MainActivity) {
            (context as MainActivity).goToDeepPage(deepLink)
        }
    }
}