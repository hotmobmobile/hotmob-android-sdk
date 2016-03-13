package hotmob.com.hotmob_android_sdk_fragment_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.hotmob.sdk.manager.HotmobManager;

/**
 * Created by choiwingchiu on 29/2/2016.
 */
public class HotmobDemoLaunchActivity extends Activity {
    private Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotmob_demo_launch);

        DisplayMetrics displaySize = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaySize);

        HotmobManager.start(this);
        HotmobManager.setDebug(true);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initMainActivity();
            }
        }, 1000);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initMainActivity(){
        Intent intent = new Intent(this, HotmobDemoMainActivity.class);
        startActivity(intent);
    }
}
