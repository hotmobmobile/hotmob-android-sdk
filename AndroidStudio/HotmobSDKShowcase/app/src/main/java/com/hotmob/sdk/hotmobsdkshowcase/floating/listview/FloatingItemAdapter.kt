package com.hotmob.sdk.hotmobsdkshowcase.floating.listview


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentFloatingitemBinding

/**
 * [RecyclerView.Adapter] that can display a [FloatingItem] and makes a call to the
 * specified [ItemClickListener].
 */
class FloatingItemAdapter(
    private val values: List<FloatingItem>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<FloatingItemAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as FloatingItem
        listener.onItemClick(item)
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

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FragmentFloatingitemBinding.bind(view)
        val name: TextView = binding.itemName
        val adCode: TextView = binding.adCode

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

    interface ItemClickListener {
        fun onItemClick(floatingItem: FloatingItem)
    }
}
