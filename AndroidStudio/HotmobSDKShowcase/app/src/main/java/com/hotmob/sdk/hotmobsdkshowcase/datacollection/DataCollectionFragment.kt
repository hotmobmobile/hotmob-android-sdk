package com.hotmob.sdk.hotmobsdkshowcase.datacollection


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hotmob.sdk.module.datacollection.HotmobDataCollection

import com.hotmob.sdk.hotmobsdkshowcase.R
import com.hotmob.sdk.module.reload.HotmobReloadManager
import kotlinx.android.synthetic.main.fragment_data_collection.*
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 *
 */
class DataCollectionFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendEventButton.setOnClickListener {
            val dataMap = HashMap<String, Any>()
            dataMap["param1"] = "ABC"
            dataMap["param2"] = "EFG"
            dataMap["param3"] = "123"
            HotmobDataCollection.captureEvent(
                context!!,
                "test_user_click",
                dataMap
            )
        }
        HotmobReloadManager
    }

    override fun onStart() {
        super.onStart()

        HotmobDataCollection.captureEvent(
            context!!,
            "test_user_action",
            "enter_page"
        )
    }
}
