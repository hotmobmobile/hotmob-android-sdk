package com.hotmob.android.example.autoreload.changefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hotmob.android.example.R;
import com.hotmob.sdk.module.autoreload.ReloadManager;
import com.hotmob.sdk.module.location.LocationManager;

import java.util.Locale;

/**
 * Demonstrating how auto-reload logic of Banner is done using Hotmob ReloadManager
 * when Fragment transaction is using "add" or "replace"
 *
 */
public class ChangeFragmentActivity extends AppCompatActivity {
    public final String LOG_TAG = this.getClass().getSimpleName();
    public static final String ACTIVITY_COUNTER = "activity_counter";
    public static final String TRANSACTION_TYPE = "transaction_type";

    public enum TransactionType {
        ADD, REPLACE
    }
    private TransactionType transactionType;
    private int fragment_counter;
    private int activity_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addfragment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            activity_counter = bundle.getInt(ACTIVITY_COUNTER, 1);
            transactionType = (TransactionType) bundle.getSerializable(TRANSACTION_TYPE);
        }else{
            activity_counter = 1;
            transactionType = TransactionType.REPLACE;
        }
        fragment_counter = 1;

        if (transactionType.equals(TransactionType.ADD)) {
            ((Button) findViewById(R.id.replace_fragment_btn)).setText(R.string.reload_add_fragment);
        } else {
            ((Button) findViewById(R.id.replace_fragment_btn)).setText(R.string.reload_replace_fragment);
        }

        if (savedInstanceState == null) {
            // pass the page name to Fragment for Banner reload
            Fragment fragment = ContentFragment.newInstance(getCurrentPageName());
            if (transactionType.equals(TransactionType.ADD)) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
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
                // pass the page name to Fragment for Banner reload
                Fragment fragment = ContentFragment.newInstance(getCurrentPageName());
                if (transactionType.equals(TransactionType.ADD)) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container, fragment)
                            .addToBackStack(getCurrentPageName())
                            .commit();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(getCurrentPageName())
                            .commit();
                }

                // change current page
                ReloadManager.setCurrentPage(getCurrentPageName());
            }
        });

        findViewById(R.id.add_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeFragmentActivity.this, ChangeFragmentActivity.class);
                intent.putExtra(ACTIVITY_COUNTER, activity_counter+1);
                intent.putExtra(TRANSACTION_TYPE, transactionType);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
            fragment_counter--;

            // change current page
            ReloadManager.setCurrentPage(getCurrentPageName());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*
            This setCurrentPage() is for:
            - Inform ReloadManager for the 1st page to display
            - When back form another Activity
         */
        ReloadManager.setCurrentPage(getCurrentPageName());
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

    private String getCurrentPageName() {
        return "Page_A" + activity_counter + "F" + fragment_counter;
    }
}
