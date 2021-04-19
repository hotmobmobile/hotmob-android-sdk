package com.hotmob.preloaddemo.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hotmob.sdk.ad.HotmobBanner
import com.hotmob.sdk.helper.HotmobAdViewHolder
import com.hotmob.preloaddemo.R
import com.hotmob.preloaddemo.databinding.FragmentHomeitemBinding


import com.hotmob.preloaddemo.home.HomeFragment.OnListFragmentInteractionListener
import com.hotmob.preloaddemo.home.dummy.DummyItem


/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyHomeItemRecyclerViewAdapter(
    private val values: List<DummyItem>,
    private val listener: OnListFragmentInteractionListener?,
    val adMap: Map<Int, HotmobBanner>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val NORMAL = 0
        const val AD = 1
    }

    fun hideBanners() {
        adMap.values.forEach {
            it.hide()
        }
    }

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            listener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == AD) {
            HotmobAdViewHolder(HotmobAdViewHolder.getAdViewHolderView(parent))  // use Hotmob HotmobAdViewHolder
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_homeitem, parent, false)
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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
            (holder as ViewHolder).mIdView.text = item.id
            holder.mContentView.text = item.content
            holder.mView.setOnClickListener(onClickListener)
            holder.mView.tag = item
        }
    }

    override fun getItemCount(): Int = values.size + adMap.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val binding = FragmentHomeitemBinding.bind(mView)
        val mIdView: TextView = binding.itemNumber
        val mContentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
