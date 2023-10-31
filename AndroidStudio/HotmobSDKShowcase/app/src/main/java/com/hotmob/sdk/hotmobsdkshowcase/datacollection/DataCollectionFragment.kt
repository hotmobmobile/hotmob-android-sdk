package com.hotmob.sdk.hotmobsdkshowcase.datacollection


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentDataCollectionBinding
import com.hotmob.sdk.module.datacollection.HotmobDataCollection
import com.hotmob.sdk.module.reload.HotmobReloadManager

/**
 * A simple [Fragment] subclass.
 *
 */
class DataCollectionFragment : androidx.fragment.app.Fragment() {
    private var _binding: FragmentDataCollectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDataCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendEventButton.setOnClickListener {
            val dataMap = HashMap<String, Any>()
            dataMap["param1"] = "ABC"
            dataMap["param2"] = "EFG"
            dataMap["param3"] = "123"
            HotmobDataCollection.captureEvent(
                requireContext(),
                "test_user_click",
                dataMap
            )
        }
        HotmobReloadManager
    }

    override fun onStart() {
        super.onStart()

        HotmobDataCollection.captureEvent(
            requireContext(),
            "test_user_action",
            "enter_page"
        )
    }
}
