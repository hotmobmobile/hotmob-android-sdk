package com.hotmob.sdk.hotmobsdkshowcase.videobanner


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobAdEvent
import com.hotmob.sdk.ad.HotmobAdListener
import com.hotmob.sdk.hotmobsdkshowcase.MainActivity
import com.hotmob.sdk.hotmobsdkshowcase.R
import kotlinx.android.synthetic.main.fragment_video_banner_showcase.*


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoBannerShowcaseFragment : Fragment(), View.OnClickListener, HotmobAdListener, HotmobAdDeepLinkListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_banner_showcase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickActionList = resources.getStringArray(R.array.video_click_actions)
        val clickActionAdCodeList = resources.getStringArray(R.array.video_banner_click_action_adcodes)
        for (i in clickActionList.indices) {
            val button = Button(context)
            button.text = clickActionList[i]
            button.tag = clickActionAdCodeList[i]
            button.setOnClickListener(this)
            clickActions.addView(button)
        }

        showFloatingObstacle.setOnClickListener {
            floatingObstacle.visibility = View.VISIBLE
        }
        floatingObstacle.setOnClickListener {
            floatingObstacle.visibility = View.GONE
        }

        banner.obstacleViews.add(floatingObstacle)
    }

    override fun onStart() {
        super.onStart()
        banner.listener = this
        banner.deepLinkListener = this
    }

    override fun onStop() {
        super.onStop()
        banner.listener = null
        banner.deepLinkListener = null
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
            else -> return
        }
    }

    override fun onDeepLink(deepLink: String) {
        if (context is MainActivity) {
            (context as MainActivity).goToDeepPage(deepLink)
        }
    }
}
