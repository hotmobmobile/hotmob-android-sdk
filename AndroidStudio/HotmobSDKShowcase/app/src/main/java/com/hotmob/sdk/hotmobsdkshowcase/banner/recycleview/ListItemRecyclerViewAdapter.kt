package com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hotmob.sdk.ad.HotmobBanner
import com.hotmob.sdk.helper.HotmobAdViewHolder
import com.hotmob.sdk.hotmobsdkshowcase.R


import com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview.dummy.DummyItem

import kotlinx.android.synthetic.main.listitem_dummyitem.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem]
 * inherit RecyclerView.Adapter<RecyclerView.ViewHolder> <-- PAY ATTENTION!!
 */
class ListItemRecyclerViewAdapter(
    private val values: List<DummyItem>,
    val adMap: Map<Int, HotmobBanner>
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    companion object {
        const val NORMAL = 0
        const val AD = 1
    }

    /**
     * @param parent
     * @param viewType
     * @return RecyclerView.ViewHolder <-- PAY ATTENTION!!
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return if (viewType == AD) {
            HotmobAdViewHolder(HotmobAdViewHolder.getAdViewHolderView(parent))  // use Hotmob HotmobAdViewHolder
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_dummyitem, parent, false)
            ViewHolder(view)
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

    /**
     * Need to separate ViewHolder logic here
     * @param holder RecyclerView.ViewHolder <-- PAY ATTENTION!!
     * @param position Int
     */
    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        // calculate number of item shift by adding ads
        var numberOfAdAbove = 0
        adMap.keys.forEach {
            if (it < position)
                numberOfAdAbove++
        }
        val itemType = getItemViewType(position)
//        Log.d("onBindViewHolder", "position: $position itemType: $itemType")

        if (itemType == AD) {
            (holder as HotmobAdViewHolder).putAdView(adMap[position]!!)
        } else {
            val item = values[position-numberOfAdAbove]
            (holder as ListItemRecyclerViewAdapter.ViewHolder).idView.text = item.id
            holder.contentView.text = item.content
        }
    }

    override fun getItemCount(): Int = values.size + adMap.size

    inner class ViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val idView: TextView = view.item_number
        val contentView: TextView = view.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
