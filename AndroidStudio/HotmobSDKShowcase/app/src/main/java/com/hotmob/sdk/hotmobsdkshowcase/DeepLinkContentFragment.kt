package com.hotmob.sdk.hotmobsdkshowcase


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hotmob.sdk.hotmobsdkshowcase.databinding.FragmentDeepLinkContentBinding

private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [DeepLinkContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DeepLinkContentFragment : androidx.fragment.app.Fragment() {
    private var param1: String? = null
    private var _binding: FragmentDeepLinkContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            param1 = getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeepLinkContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deepLinkAddress.text = param1
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment DeepLinkContentFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            DeepLinkContentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
