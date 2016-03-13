package hotmob.com.hotmob_android_sdk_fragment_demo;

import com.hotmob.sdk.handler.HotmobHandler;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

/**
 * Created by choiwingchiu on 29/2/2016.
 */
public class HotmobDemoBaseActivity extends SlidingActivity {
    @Override
    protected void onStart() {
        super.onStart();

        HotmobHandler.getInstance(this).onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        HotmobHandler.getInstance(this).onStop();
    }

    @Override
    public void onBackPressed() {
        if (HotmobHandler.getInstance(this).hotmobBackButtonPressed()){
            HotmobHandler.getInstance(this).onBackPressed();
        }else{
//            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HotmobHandler.getInstance(this).onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        HotmobHandler.getInstance(this).onPause();
    }
}
