package com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hotmob.sdk.ad.HotmobBanner
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview.dummy.DummyItem
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.forEach
import kotlin.collections.set

/**
 * A fragment representing a list of Items.
 */
class BannerInRecycleViewFragment : androidx.fragment.app.Fragment() {

    private var columnCount = 1
    private var listAdapter: ListItemRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_banner_recycleview, container, false)

        // Set the adapter
        if (view is androidx.recyclerview.widget.RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> androidx.recyclerview.widget.LinearLayoutManager(context)
                    else -> androidx.recyclerview.widget.GridLayoutManager(context, columnCount)
                }

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
                ad1.focusableAd = false
                ad1.animated = true
                val ad2 = HotmobBanner(context)
                ad2.identifier = "List Banner 2"
                ad2.adCode = context.resources.getStringArray(R.array.banner_click_action_adcodes)[2]
                ad2.focusableAd = false
                val ads = HashMap<Int, HotmobBanner>()
                ads[0] = ad1
                ads[15] = ad2
                listAdapter = ListItemRecyclerViewAdapter(dummyItems, ads)
                adapter = listAdapter
            }
        }
        return view
    }

    override fun onDestroyView() {
        listAdapter!!.adMap.values.forEach {
            it.destroy()
        }
        super.onDestroyView()
    }
}
