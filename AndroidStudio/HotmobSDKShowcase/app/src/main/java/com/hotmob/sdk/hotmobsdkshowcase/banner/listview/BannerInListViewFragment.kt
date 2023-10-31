package com.hotmob.sdk.hotmobsdkshowcase.banner.listview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ListView
import com.hotmob.sdk.ad.HotmobAdEvent
import com.hotmob.sdk.ad.HotmobAdListener
import com.hotmob.sdk.ad.HotmobBanner
import com.hotmob.sdk.ad.webview.AdWebView
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview.dummy.DummyItem
import kotlin.collections.set

/**
 * A fragment representing a list of Items.
 */
class BannerInListViewFragment : androidx.fragment.app.Fragment(), HotmobAdListener {

    private var listView: ListView? = null
    private var listAdapter: ListItemListViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_banner_listview, container, false)

        // Set the adapter
        if (view is ListView) {
            with(view) {
                val dummyItems = ArrayList<DummyItem>()
                for (i in 1..30) {
                    dummyItems.add(DummyItem(
                        id = i.toString(),
                        content = "Item $i"
                    ))
                }
                // setup Hotmob Ad and put in adapter
                val ad1 = HotmobBanner(context)
                ad1.identifier = "List Banner 1"
                ad1.adCode = context.resources.getStringArray(R.array.banner_click_action_adcodes)[0]
                ad1.focusableAd = true

                val ad2 = HotmobBanner(context)
                ad2.identifier = "List Banner 2"
                ad2.adCode = context.resources.getStringArray(R.array.banner_click_action_adcodes)[2]
                ad2.focusableAd = false

                val ads = HashMap<Int, HotmobBanner>()
                ads[5] = ad1
                ads[15] = ad2
                listAdapter = ListItemListViewAdapter(context, dummyItems, ads)
                adapter = listAdapter
            }
            listView = view
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter!!.adMap.values.forEach {
            it.listener = this
        }
    }

    override fun onDestroyView() {
        listAdapter!!.adMap.values.forEach {
            it.destroy()
        }
        super.onDestroyView()
    }

    @SuppressLint("SetTextI18n")
    override fun onAdEvent(adEvent: HotmobAdEvent) {
        when (adEvent) {
            HotmobAdEvent.SHOW -> showingCallback()
            else -> return
        }
    }
    private fun showingCallback() {
        val adWebView: AdWebView? = listView?.findViewById(R.id.HTMLWebView)
        adWebView?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event != null && event.action != MotionEvent.ACTION_UP) {
                    listView?.requestDisallowInterceptTouchEvent(true)
                } else {
                    (v as AdWebView)?.isClicked = true
                }
                return false
            }
        })
    }
}
