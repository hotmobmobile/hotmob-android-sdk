package com.hotmob.android.sdk.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.hotmob.sdk.handler.HotmobHandler;

public class HotmobDemoMultipleFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotmob_demo_multiple_fragment);

        Log.d("Test", "activity onCreate");

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                Log.d("Test", "SavedInstanceState exist");
                return;
            }

            Log.d("Test", "Start creating fragment");

            HotmobDemoMultipleFragmentActivityFragment firstFragment = new HotmobDemoMultipleFragmentActivityFragment();

            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        HotmobHandler.getInstance(this).onStart();
        Log.d("Test", "activity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        HotmobHandler.getInstance(this).onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HotmobHandler.getInstance(this).onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HotmobHandler.getInstance(this).onResume();
        Log.d("Test", "activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        HotmobHandler.getInstance(this).onPause();
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

}
