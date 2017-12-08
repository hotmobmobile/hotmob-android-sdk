package com.hotmob.android.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hotmob.android.example.banner.HTMLBannerFragment;
import com.hotmob.android.example.banner.MediationBannerFragment;
import com.hotmob.android.example.banner.OtherBannerFragment;
import com.hotmob.android.example.banner.VideoBannerFragment;
import com.hotmob.android.example.data.DataExampleFragment;
import com.hotmob.android.example.listview.ListViewFragment;
import com.hotmob.android.example.popup.HTMLPopupFragment;
import com.hotmob.android.example.popup.MediationPopupFragment;
import com.hotmob.android.example.popup.OtherPopupFragment;
import com.hotmob.android.example.popup.VideoPopupFragment;
import com.hotmob.android.example.recycleview.RecycleItemFragment;
import com.hotmob.android.example.recycleview.RecyclerViewItem;
import com.hotmob.android.example.reload.AddFragmentActivity;
import com.hotmob.android.example.reload.ReplaceFragmentActivity;
import com.hotmob.android.example.reload.viewpager.ViewPagerFragment;
import com.hotmob.android.example.srcollview.ScrollViewFragment;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

public class MainActivity extends BaseActivity
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

        HotmobManager.start(this);
        HotmobManager.setDebug(true);

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

        HotmobManagerListener listener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);
                Log.d(TAG, "popup openInternalCallback " + url);
                changeToInternalFragment(url);
            }

            public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                Log.d(TAG, "hotmobBannerIsReadyChangeSoundSettings: " + isSoundEnable);
            }
        };

        HotmobManager.getPopup(this, listener, "launch_popup",
                getResources().getStringArray(R.array.html_popup_adcodes)[0], true);
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
        if (id == R.id.action_location) {
            requestLocationPermission();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_html_banner) {
            changeFragment(item, new HTMLBannerFragment());
        } else if (id == R.id.nav_video_banner) {
            changeFragment(item, new VideoBannerFragment());
        } else if (id == R.id.nav_mediation_banner) {
            changeFragment(item, new MediationBannerFragment());
        } else if (id == R.id.nav_other_banner) {
            changeFragment(item, new OtherBannerFragment());
        } else if (id == R.id.nav_html_popup) {
            changeFragment(item, new HTMLPopupFragment());
        } else if (id == R.id.nav_video_popup) {
            changeFragment(item, new VideoPopupFragment());
        } else if (id == R.id.nav_mediation_popup) {
            changeFragment(item, new MediationPopupFragment());
        } else if (id == R.id.nav_other_popup) {
            changeFragment(item, new OtherPopupFragment());
        } else if (id == R.id.nav_reload_add_f) {
            Intent intent = new Intent(this, AddFragmentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_reload_replace_f) {
            Intent intent = new Intent(this, ReplaceFragmentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_view_pager) {
            changeFragment(item, new ViewPagerFragment());
        } else if (id == R.id.nav_scrollview) {
            changeFragment(item, new ScrollViewFragment());
        } else if (id == R.id.nav_listview) {
            changeFragment(item, new ListViewFragment());
        } else if (id == R.id.nav_recycleview) {
            changeFragment(item, new RecycleItemFragment());
        } else if (id == R.id.nav_skip_resume_popup) {
            HotmobManager.skipComingPopupResume();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.google.com"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_data_normal) {
            changeFragment(item, new DataExampleFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
