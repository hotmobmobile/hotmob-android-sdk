package com.hotmob.android.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.sdk.manager.HotmobManager;

/**
 * This is a recommended coding style to include all essential lifecycle event for HotmobSDK to
 * perform auto reload.
 * You can use other implementation method to include these 2 HotmobHandler callings into your
 * Fragment. Be sure that you have include ALL these into ALL of your Fragment
 */

public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HotmobManager.setCurrentFragment(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        HotmobManager.setCurrentFragment(this);
        super.onResume();
    }
}