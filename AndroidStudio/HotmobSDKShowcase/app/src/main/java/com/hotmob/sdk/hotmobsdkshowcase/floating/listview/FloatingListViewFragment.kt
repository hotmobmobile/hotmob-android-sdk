package com.hotmob.sdk.hotmobsdkshowcase.floating.listview

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.Toast
import com.hotmob.sdk.ad.HotmobAdDeepLinkListener
import com.hotmob.sdk.ad.HotmobAdEvent
import com.hotmob.sdk.ad.HotmobAdListener
import com.hotmob.sdk.ad.HotmobFloating
import com.hotmob.sdk.hotmobsdkshowcase.MainActivity
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentFloatingListBinding


/**
 * A fragment representing a list of Items.
 */
class FloatingListViewFragment : androidx.fragment.app.Fragment(),
    FloatingItemAdapter.ItemClickListener,
    HotmobAdListener,
    HotmobAdDeepLinkListener {
    private var _binding: FragmentFloatingListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var floating: HotmobFloating? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFloatingListBinding.inflate(inflater, container, false)
        floating?.listener = this
        floating?.deepLinkListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        with(binding.list) {
            layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(context)

            val floatingArray = mutableListOf<FloatingItem>()

            val expandableList = resources.getStringArray(R.array.floating_banner)
            val expandableAdCodeList = resources.getStringArray(R.array.floating_banner_adcode)
            for (i in expandableList.indices) {
                floatingArray.add(
                    FloatingItem(
                        name = expandableList[i],
                        adCode = expandableAdCodeList[i]
                    ))
            }

            adapter = FloatingItemAdapter(
                floatingArray,
                this@FloatingListViewFragment
            )
        }

        binding.customAdCode.setOnEditorActionListener { _, actionId, _ ->
            val result = actionId and EditorInfo.IME_MASK_ACTION
            if (result == EditorInfo.IME_ACTION_DONE) {
                val customCode = binding.customAdCode.text.toString()
                // 1. hide the Banner
                onItemClick(FloatingItem("Custom", customCode))
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        floating?.listener = null
        floating?.deepLinkListener = null
        floating?.destroy()
    }

    override fun onItemClick(floatingItem: FloatingItem) {
        Log.d("FloatingShowcase", "${floatingItem.name} clicked.")
        floating?.hide()

        if(floating == null) {
            floating = HotmobFloating(requireContext())
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            params.gravity = Gravity.BOTTOM and Gravity.END

            floating?.layoutParams = params
            floating?.identifier = "Floating"
            floating?.adCode = floatingItem.adCode
            val contentLayout = activity?.findViewById(R.id.content) as FrameLayout
            floating?.bindToView(contentLayout as ViewGroup)
        } else {
            floating?.adCode = floatingItem.adCode
            floating?.loadAd()
        }
    }

    override fun onAdEvent(adEvent: HotmobAdEvent) {
        if (adEvent == HotmobAdEvent.NO_AD) {
            Toast.makeText(context, "No ad returned", Toast.LENGTH_SHORT).show()
        } else if (adEvent == HotmobAdEvent.VIDEO_UNMUTE) {
            Log.d("FloatingShowcase", "Video unmute")
        }
    }

    override fun onDeepLink(deepLink: String) {
        if (context is MainActivity) {
            (context as MainActivity).goToDeepPage(deepLink)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        floating?.listener = null
        floating?.deepLinkListener = null
        floating?.destroy()
        val contentLayout = activity?.findViewById(R.id.content) as FrameLayout
        contentLayout.removeView(floating)
    }
}
