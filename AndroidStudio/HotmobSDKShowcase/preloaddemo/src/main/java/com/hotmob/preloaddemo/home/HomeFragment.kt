package com.hotmob.preloaddemo.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hotmob.sdk.ad.HotmobBanner
import com.hotmob.preloaddemo.R
import com.hotmob.preloaddemo.databinding.FragmentHomeitemListBinding

import com.hotmob.preloaddemo.home.dummy.DummyItem
import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [HomeFragment.OnListFragmentInteractionListener] interface.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeitemListBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeitemListBinding.inflate(inflater, container, false)

        // Set the adapter
        with(binding.root) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            val dummyItems = ArrayList<DummyItem>()
            for (i in 1..30) {
                dummyItems.add(DummyItem(
                    id = i.toString(),
                    content = "Item $i",
                    details = "Details $i"
                ))
            }
            // setup Hotmob Ad and put in adapter
            val ad1 = HotmobBanner(context)
            ad1.identifier = "List Banner 1"
            ad1.adCode = "hotmob_android_v2_video_inapp_banner"
            ad1.focusableAd = false
            val ad2 = HotmobBanner(context)
            ad2.identifier = "List Banner 2"
//                ad2.adCode = "hotmob_android_v2_html_inapp_banner_lrec"
            ad2.adCode = "hotmob_android_v2_celtra_banner"
            ad2.focusableAd = false
            val ads = HashMap<Int, HotmobBanner>()
            ads[0] = ad1
            ads[5] = ad2
            adapter = MyHomeItemRecyclerViewAdapter(dummyItems, listener, ads)
        }
        return binding.root
    }

    override fun onDestroyView() {
        (binding.list.adapter as? MyHomeItemRecyclerViewAdapter)?.adMap?.values?.forEach {
            it.destroy()
        }
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onPause() {
        super.onPause()
        (binding.list.adapter as? MyHomeItemRecyclerViewAdapter)?.hideBanners()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: DummyItem?)
    }

}
