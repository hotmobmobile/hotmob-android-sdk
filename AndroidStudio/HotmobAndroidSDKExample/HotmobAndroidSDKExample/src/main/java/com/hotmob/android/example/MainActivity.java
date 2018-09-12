package com.hotmob.android.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hotmob.android.example.autoreload.changefragment.ChangeFragmentActivity;
import com.hotmob.android.example.autoreload.disableinterstitialresume.DisableInterstitialDemoActivity;
import com.hotmob.android.example.autoreload.launchinterstitial.Page1Activity;
import com.hotmob.android.example.autoreload.pagewithnoad.SimpleActivity;
import com.hotmob.android.example.autoreload.recycleview.RecycleViewActivity;
import com.hotmob.android.example.autoreload.viewpager.ViewPagerActivity;
import com.hotmob.android.example.banner.HTMLBannerFragment;
import com.hotmob.android.example.banner.MediationBannerFragment;
import com.hotmob.android.example.banner.OtherBannerFragment;
import com.hotmob.android.example.banner.VideoBannerFragment;
import com.hotmob.android.example.compatibility.LagacyActivity;
import com.hotmob.android.example.data.DataExampleFragment;
import com.hotmob.android.example.interstitial.HTMLInterstitialFragment;
import com.hotmob.android.example.interstitial.MediationInterstitialFragment;
import com.hotmob.android.example.interstitial.OtherInterstitialFragment;
import com.hotmob.android.example.interstitial.VideoInterstitialFragment;
import com.hotmob.android.example.scrollableview.listview.ListViewFragment;
import com.hotmob.android.example.scrollableview.recycleview.RecycleItemFragment;
import com.hotmob.android.example.scrollableview.recycleview.RecyclerViewItem;
import com.hotmob.android.example.scrollableview.srcollview.ScrollViewFragment;
import com.hotmob.sdk.module.autoreload.ReloadManager;
import com.hotmob.sdk.module.datacollection.DataCollection;
import com.hotmob.sdk.module.location.LocationManager;
import com.hotmob.sdk.module.settings.SettingsManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecycleItemFragment.OnListFragmentInteractionListener{

    public static String TAG = MainActivity.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private ActionBarDrawerToggle toggle;
    private MenuItem prevItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SettingsManager.refreshSettings(this);
        SettingsManager.setDebug(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                    onBackPressed();
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new HTMLBannerFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                if (getSupportFragmentManager().getBackStackEntryCount() == 1
                        && getSupportActionBar() != null){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    toggle.setDrawerIndicatorEnabled(true);
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_location:
                requestLocationPermission();
                return true;
            case R.id.action_reload_toggle:
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(String.format(
                                getString(R.string.reload_toggle_msg),
                                String.valueOf(ReloadManager.isAutoReloadEnable())
                        ))
                        .setPositiveButton(R.string.reload_toggle_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReloadManager.enableAutoReload();
                            }
                        })
                        .setNegativeButton(R.string.reload_toggle_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReloadManager.disableAutoReload();
                            }
                        })
                        .create().show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.nav_html_banner:
                changeFragment(item, new HTMLBannerFragment());
                break;
            case R.id.nav_video_banner:
                changeFragment(item, new VideoBannerFragment());
                break;
            case R.id.nav_mediation_banner:
                changeFragment(item, new MediationBannerFragment());
                break;
            case R.id.nav_other_banner:
                changeFragment(item, new OtherBannerFragment());
                break;
            case R.id.nav_html_interstitial:
                changeFragment(item, new HTMLInterstitialFragment());
                break;
            case R.id.nav_video_interstitial:
                changeFragment(item, new VideoInterstitialFragment());
                break;
            case R.id.nav_mediation_interstitial:
                changeFragment(item, new MediationInterstitialFragment());
                break;
            case R.id.nav_other_interstitial:
                changeFragment(item, new OtherInterstitialFragment());
                break;
            case R.id.nav_scrollview:
                changeFragment(item, new ScrollViewFragment());
                break;
            case R.id.nav_listview:
                changeFragment(item, new ListViewFragment());
                break;
            case R.id.nav_recycleview:
                changeFragment(item, new RecycleItemFragment());
                break;
            case R.id.nav_reload_add_fragment:
                intent = new Intent(this, ChangeFragmentActivity.class);
                intent.putExtra(ChangeFragmentActivity.TRANSACTION_TYPE, ChangeFragmentActivity.TransactionType.ADD);
                startActivity(intent);
                break;
            case R.id.nav_reload_replace_fragment:
                intent = new Intent(this, ChangeFragmentActivity.class);
                intent.putExtra(ChangeFragmentActivity.TRANSACTION_TYPE, ChangeFragmentActivity.TransactionType.REPLACE);
                startActivity(intent);
                break;
            case R.id.nav_reload_viewpager:
                intent = new Intent(this, ViewPagerActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_reload_recycleview:
                intent = new Intent(this, RecycleViewActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_launch_interstitial:
                intent = new Intent(this, Page1Activity.class);
                startActivity(intent);
                break;
            case R.id.nav_fragment_with_no_ad:
                intent = new Intent(this, SimpleActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_skip_resume_interstitial:
                intent = new Intent(this, DisableInterstitialDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_deep_link_interstitial:
                intent = new Intent(this, com.hotmob.android.example.autoreload.deeplinkinterstitial.SimpleActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_data_normal:
                changeFragment(item, new DataExampleFragment());
                break;
            case R.id.nav_backward_compatible:
                intent = new Intent(this, LagacyActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeToInternalFragment(String internalUrl){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, InternalFragment.newInstance(internalUrl))
                .addToBackStack(null)
                .commitAllowingStateLoss(); // avoid Android bug on .commit() trigger state loss
        if (getSupportActionBar() != null) {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults[0] < 0)
                    Log.v(TAG, "ACCESS_FINE_LOCATION Failed");
                else
                    Log.v(TAG, "ACCESS_FINE_LOCATION Permission Granted");
            }

        }
    }

    @Override
    public void onListFragmentInteraction(RecyclerViewItem item) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Location Manager
        LocationManager.activityOnStart(this);

        // Start a new session for Hotmob Data Collection
        DataCollection.startNewSession();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Location Manager
        LocationManager.activityOnStop();
    }

    private void changeFragment(MenuItem item, Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
        if (prevItem != null)
            prevItem.setChecked(false);
        item.setChecked(true);
        prevItem = item;
    }

    private void requestLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Request Location")
                        .setMessage("ACCESS_FINE_LOCATION")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Permission Granted")
                    .create()
                    .show();
        }
    }
}
