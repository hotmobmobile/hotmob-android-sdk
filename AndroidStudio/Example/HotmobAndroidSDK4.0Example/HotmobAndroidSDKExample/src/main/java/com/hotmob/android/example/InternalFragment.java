package com.hotmob.android.example;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InternalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InternalFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "internalUrl";

    private String internalUrl;


    public InternalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param internalUrl Parameter 1.
     * @return A new instance of fragment InternalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InternalFragment newInstance(String internalUrl) {
        InternalFragment fragment = new InternalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, internalUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            internalUrl = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_internal, container, false);
        ((TextView) root.findViewById(R.id.url)).setText(internalUrl);
        return root;
    }

}
