package com.hotmob.android.example.autoreload.pagewithnoad;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.android.example.R;

/**
 * Fragment with no ad.
 * Please note that there can be no Hotmob SDK implementation is this Fragment
 */
public class NoAdFragment extends Fragment {
    public final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_no_ad, container, false);
    }
}
