package com.hotmob.android.example.reload;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hotmob.android.example.BaseActivity;
import com.hotmob.android.example.R;
import com.hotmob.sdk.manager.HotmobManager;

import java.util.Locale;

/**
 * Created by ken on 14/11/2017.
 */

public class AddFragmentActivity extends BaseActivity {

    protected final String TAG = this.getClass().getSimpleName();

    private int fragment_counter = 1;
    private int activity_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addfragment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            activity_counter = bundle.getInt("activity_counter");
        }else{
            activity_counter = 1;
        }

        if (savedInstanceState == null) {
            String currentFragmentTag = "Fragment"+fragment_counter;
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, BlankFragment.newInstance(activity_counter, fragment_counter), currentFragmentTag)
                    .commit();
        }

        ((TextView) findViewById(R.id.activity_name)).setText(
                String.format(
                        Locale.ENGLISH,
                        getString(R.string.reload_activity_title),
                        activity_counter
                )
        );

        findViewById(R.id.replace_fragment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_counter++;
                String currentFragmentTag = "Fragment"+fragment_counter;
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, BlankFragment.newInstance(activity_counter, fragment_counter), currentFragmentTag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        findViewById(R.id.add_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFragmentActivity.this, AddFragmentActivity.class);
                intent.putExtra("activity_counter", activity_counter+1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
            fragment_counter--;

            // If use "add" to add new fragment, it is required to get the display fragment back for
            // setCurrentFragment() and do reload
            BlankFragment addFragment = (BlankFragment) getSupportFragmentManager().findFragmentByTag("Fragment"+fragment_counter);
            Log.i(TAG, "onBackPressed() - addFragment: "+addFragment.getFragmentCounter());
            HotmobManager.setCurrentFragment(addFragment);
            Log.i(TAG, "onBackPressed() - getCurrentFragment(): "+HotmobManager.getCurrentFragment());
            addFragment.callBanner();
        } else {
            super.onBackPressed();
        }
    }
}
