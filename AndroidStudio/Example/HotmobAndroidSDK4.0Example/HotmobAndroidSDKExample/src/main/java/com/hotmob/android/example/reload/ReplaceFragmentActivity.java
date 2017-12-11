package com.hotmob.android.example.reload;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hotmob.android.example.BaseActivity;
import com.hotmob.android.example.R;

import java.util.Locale;

/**
 * Created by ken on 14/11/2017.
 */

public class ReplaceFragmentActivity extends BaseActivity {

    protected final String TAG = this.getClass().getSimpleName();

    private int fragment_counter = 1;
    private int activity_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_replacefragment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            activity_counter = bundle.getInt("activity_counter");
        }else{
            activity_counter = 1;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, BlankFragment.newInstance(activity_counter, fragment_counter))
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
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, BlankFragment.newInstance(activity_counter, ++fragment_counter))
                        .addToBackStack(null)
                        .commit();
            }
        });

        findViewById(R.id.add_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReplaceFragmentActivity.this, ReplaceFragmentActivity.class);
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
        } else {
            super.onBackPressed();
        }
    }
}
