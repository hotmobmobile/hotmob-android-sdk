package com.hotmob.android.example.compatibility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotmob.sdk.manager.HotmobManager;

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