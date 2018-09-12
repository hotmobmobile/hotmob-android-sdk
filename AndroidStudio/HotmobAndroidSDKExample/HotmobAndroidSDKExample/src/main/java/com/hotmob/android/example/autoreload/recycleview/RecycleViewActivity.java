package com.hotmob.android.example.autoreload.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hotmob.android.example.R;
import com.hotmob.android.example.scrollableview.recycleview.RecycleItemFragment;
import com.hotmob.android.example.scrollableview.recycleview.RecyclerViewItem;
import com.hotmob.sdk.module.autoreload.ReloadManager;
import com.hotmob.sdk.module.location.LocationManager;

/**
 * Demonstrating how auto-reload logic of Banner is done using Hotmob ReloadManager in RecycleView
 */
public class RecycleViewActivity extends AppCompatActivity implements RecycleItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new RecycleViewFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // reload manager
        ReloadManager.activityOnStart(this);

        // Location Manager
        LocationManager.activityOnStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // reload manager
        ReloadManager.activityOnStop();

        // Location Manager
        LocationManager.activityOnStop();
    }

    @Override
    public void onListFragmentInteraction(RecyclerViewItem item) {

    }
}
