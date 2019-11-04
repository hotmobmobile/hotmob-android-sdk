package com.hotmob.sdk.hotmobsdkshowcase.banner.listview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
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
class BannerInListViewFragment : androidx.fragment.app.Fragment() {

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
                ad1.focusableAd = false
                val ad2 = HotmobBanner(context)
                ad2.identifier = "List Banner 2"
                ad2.adCode = context.resources.getStringArray(R.array.banner_click_action_adcodes)[2]
                ad2.focusableAd = false
                val ads = HashMap<Int, HotmobBanner>()
                ads[0] = ad1
                ads[15] = ad2
                listAdapter = ListItemListViewAdapter(context, dummyItems, ads)
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
