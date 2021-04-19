package com.hotmob.sdk.hotmobsdkshowcase.videobanner


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentVideoBannerShowcaseBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoBannerShowcaseFragment : androidx.fragment.app.Fragment(), View.OnClickListener, HotmobAdListener, HotmobAdDeepLinkListener {
    private var _binding: FragmentVideoBannerShowcaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVideoBannerShowcaseBinding.inflate(inflater, container, false)
        return binding.root
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
            binding.clickActions.addView(button)
        }

        binding.showFloatingObstacle.setOnClickListener {
            binding.floatingObstacle.visibility = View.VISIBLE
        }
        binding.floatingObstacle.setOnClickListener {
            binding.floatingObstacle.visibility = View.GONE
        }

        binding.banner.obstacleViews.add(binding.floatingObstacle)
        binding.banner.listener = this
        binding.banner.deepLinkListener = this
    }

    override fun onDestroyView() {
        binding.banner.destroy()
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        binding.banner.hide()
        binding.currentAdCode.text = v!!.tag.toString()
        binding.banner.adCode = binding.currentAdCode.text.toString()
        binding.banner.loadAd()
    }

    @SuppressLint("SetTextI18n")
    override fun onAdEvent(adEvent: HotmobAdEvent) {
        when (adEvent) {
            HotmobAdEvent.START_LOADING -> binding.bannerStatus.text = "Start Loading"
            HotmobAdEvent.LOADED -> binding.bannerStatus.text = "Loaded"
            HotmobAdEvent.NO_AD -> binding.bannerStatus.text = "No ad returned"
            HotmobAdEvent.SHOW -> binding.bannerStatus.text = "Showing"
            HotmobAdEvent.HIDE -> binding.bannerStatus.text = "Hidden"
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
