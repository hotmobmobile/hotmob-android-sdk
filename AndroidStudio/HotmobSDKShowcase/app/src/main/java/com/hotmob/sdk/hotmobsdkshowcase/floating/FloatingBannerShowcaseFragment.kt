package com.hotmob.sdk.hotmobsdkshowcase.floating


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobAdEvent
import com.hotmob.sdk.ad.HotmobAdListener
import com.hotmob.sdk.ad.HotmobFloating
import com.hotmob.sdk.hotmobsdkshowcase.MainActivity
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentFloatingShowcaseBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class FloatingBannerShowcaseFragment : Fragment(), View.OnClickListener, HotmobAdListener, HotmobAdDeepLinkListener {
    private var _binding: FragmentFloatingShowcaseBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var currentBanner = 0
    private var floating: HotmobFloating? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFloatingShowcaseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bannerSizeTab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                hideAllBanner()
//                currentBanner = p0!!.position
//                getCurrentBanner().visibility = View.VISIBLE
//                getCurrentBanner().adCode = binding.currentAdCode.text.toString()
//                getCurrentBanner().loadAd()
                floating = context?.let { HotmobFloating(it) }
                floating?.identifier = "Floating"
                floating?.adCode = binding.currentAdCode.text.toString()
//                floating?.layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )

                floating?.bindToView(view as ViewGroup)
                floating?.loadAd()
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
        })

        val clickActionList = resources.getStringArray(R.array.click_actions)
        val clickActionAdCodeList = resources.getStringArray(R.array.banner_click_action_adcodes)
        for (i in clickActionList.indices) {
            val button = Button(context)
            button.text = clickActionList[i]
            button.tag = clickActionAdCodeList[i]
            button.setOnClickListener(this)
            binding.clickActions.addView(button)
        }

        val htmlCreativeList = resources.getStringArray(R.array.banner_html)
        val htmlCreativeAdCodeList = resources.getStringArray(R.array.banner_html_adcodes)
        for (i in htmlCreativeList.indices) {
            val button = Button(context)
            button.text = htmlCreativeList[i]
            button.tag = htmlCreativeAdCodeList[i]
            button.setOnClickListener(this)
            binding.htmlCreative.addView(button)
        }

        binding.customAdCode.setOnEditorActionListener { _, actionId, _ ->
            val result = actionId and EditorInfo.IME_MASK_ACTION
            if (result == EditorInfo.IME_ACTION_DONE) {
                val customCode = binding.customAdCode.text.toString()
                // 1. hide the Banner
                floating?.hide()
                // 2. change ad code
                floating?.adCode = customCode
                // 3. reload Banner
                floating?.loadAd()
            }
            false
        }

        floating?.listener = this
        floating?.deepLinkListener = this
    }

    override fun onDestroyView() {
        floating?.destroy()
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        floating?.hide()
        binding.currentAdCode.text = v!!.tag.toString()

        val displayMetrics = DisplayMetrics()
        (context as Activity?)?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        if(floating == null) {
            floating = HotmobFloating(requireContext())
            floating?.identifier = "Floating"
            floating?.adCode = binding.currentAdCode.text.toString()
            floating?.bindToView(binding.root as ViewGroup)
            floating?.loadAd()
//            floating?.layoutParams = FrameLayout.LayoutParams(width, height)
        } else {
            floating?.adCode = binding.currentAdCode.text.toString()
            floating?.loadAd()
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onAdEvent(adEvent: HotmobAdEvent) {
        when (adEvent) {
            HotmobAdEvent.START_LOADING -> binding.bannerStatus.text = "Start Loading"
            HotmobAdEvent.LOADED -> binding.bannerStatus.text = "Loaded"
            HotmobAdEvent.NO_AD -> binding.bannerStatus.text = "No ad returned"
            HotmobAdEvent.SHOW -> binding.bannerStatus.text = "Showing"
            HotmobAdEvent.HIDE -> binding.bannerStatus.text = "Hidden"
//            HotmobAdEvent.RESIZE -> Toast.makeText(context, "Banner resized", Toast.LENGTH_SHORT).show()
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

    private fun hideAllBanner() {
        floating?.adCode = ""
        floating?.hide()

    }

}
