package hotmob.com.hotmob_android_sdk_fragment_demo;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;

import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import hotmob.com.hotmob_android_sdk_fragment_demo.Banner.HotmobDemoBannersActivityFragment;
import hotmob.com.hotmob_android_sdk_fragment_demo.menu.HotmobDemoMenuFragment;

public class HotmobDemoMainActivity extends HotmobDemoBaseActivity {
    private SlidingMenu slidingMenu;
    private HotmobDemoMenuFragment menuFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);

        DisplayMetrics displaySize = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaySize);

        setBehindContentView(R.layout.fragment_menu);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //menu Fragment
        menuFragment = new HotmobDemoMenuFragment();
        fragmentTransaction.replace(R.id.menu, menuFragment);

        //main Fragment
        HotmobDemoBannersActivityFragment firstFragment = new HotmobDemoBannersActivityFragment(HotmobDemoBannersActivityFragment.DisplayType.DisplayTypeImage);
        fragmentTransaction.replace(R.id.content, firstFragment);

        fragmentTransaction.commit();

        slidingMenu = new SlidingMenu(this);
        slidingMenu.setShadowWidth(50);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffset(60);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        HotmobManagerListener listener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                super.didLoadBanner(bannerView);
            }
        };

        HotmobManager.getPopup(this, listener, "launch_popup", "hotmob_uat_android_image_inapp_popup", true, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_MENU){
            slidingMenu.toggle();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
