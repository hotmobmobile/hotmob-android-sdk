package com.hotmob.sdk.hotmobsdkshowcase.banner.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.hotmob.sdk.ad.HotmobBanner
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview.dummy.DummyItem
import com.hotmob.sdk.hotmobsdkshowcase.databinding.ListitemDummyitemBinding

class ListItemListViewAdapter (
    context: Context,
    values: List<DummyItem>,
    val adMap: Map<Int, HotmobBanner>
) : ArrayAdapter<DummyItem>(context, 0, values) {

    companion object {
        const val NORMAL = 0
        const val AD = 1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewType = getItemViewType(position)
        var view = convertView
        if (view == null || view.tag != viewType) {
            view = if (viewType == AD) {
                LayoutInflater.from(context).inflate(R.layout.hotmob_ad_viewholder, parent, false)
            } else {
                LayoutInflater.from(context).inflate(R.layout.listitem_dummyitem, parent, false)
            }
            view?.tag = viewType
        }
        if (viewType == AD) {
            adMap[position]?.bindToView(view as ViewGroup)
        } else {
            val item = getItem(position)
            val binding = ListitemDummyitemBinding.bind(view!!)
            binding.itemNumber.text = item!!.id
            binding.content.text = item.content
        }
        return view!!
    }

    override fun getItem(position: Int): DummyItem? {
        return if (getItemViewType(position) == AD) {
            null
        } else {
            // calculate number of item shift by adding ads
            var numberOfAdAbove = 0
            adMap.keys.forEach {
                if (it < position)
                    numberOfAdAbove++
            }
            super.getItem(position-numberOfAdAbove)
        }
    }

    /**
     * determine view type by checking ad position on adMap
     * @param position Int
     */
    override fun getItemViewType(position: Int): Int {
        return if (adMap[position] == null)
            NORMAL
        else
            AD
    }

    override fun getCount(): Int {
        return super.getCount() + adMap.size
    }
}