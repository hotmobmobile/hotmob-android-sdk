package com.hotmob.sdk.hotmobsdkshowcase.interstitial


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentInterstitialitemBinding
import com.hotmob.sdk.hotmobsdkshowcase.interstitial.InterstitialItemAdapter.ItemClickListener

/**
 * [RecyclerView.Adapter] that can display a [InterstitialItem] and makes a call to the
 * specified [ItemClickListener].
 */
class InterstitialItemAdapter(
    private val values: List<InterstitialItem>,
    private val listener: ItemClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<InterstitialItemAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as InterstitialItem
            listener.onItemClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_interstitialitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.name.text = item.name
        holder.adCode.text = item.adCode

        with(holder.view) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val binding = FragmentInterstitialitemBinding.bind(view)
        val name: TextView = binding.itemName
        val adCode: TextView = binding.adCode

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

    interface ItemClickListener {
        fun onItemClick(interstitialItem: InterstitialItem)
    }
}
