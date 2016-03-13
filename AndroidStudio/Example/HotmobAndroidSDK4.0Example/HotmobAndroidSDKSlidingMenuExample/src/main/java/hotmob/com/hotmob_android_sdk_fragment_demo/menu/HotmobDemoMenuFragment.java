package hotmob.com.hotmob_android_sdk_fragment_demo.menu;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.util.Log;

import hotmob.com.hotmob_android_sdk_fragment_demo.Banner.HotmobDemoBannersActivityFragment;
import hotmob.com.hotmob_android_sdk_fragment_demo.HotmobDemoMainActivity;
import hotmob.com.hotmob_android_sdk_fragment_demo.R;


public class HotmobDemoMenuFragment extends PreferenceFragment implements OnPreferenceClickListener {

    private int index = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        addPreferencesFromResource(R.xml.menu);
        findPreference("a").setOnPreferenceClickListener(this);
        findPreference("b").setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.i(this.getTag(), "onPreferenceClick : " + preference.getKey());
        String key = preference.getKey();
        FragmentManager fragmentManager = ((HotmobDemoMainActivity)getActivity()).getFragmentManager();
        HotmobDemoBannersActivityFragment mHotmobDemoBannersActivityFragment;
        switch (key){
            case "a":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                mHotmobDemoBannersActivityFragment = (HotmobDemoBannersActivityFragment)fragmentManager.findFragmentByTag("A");
                fragmentManager.beginTransaction()
                        .replace(R.id.content, mHotmobDemoBannersActivityFragment == null ?new HotmobDemoBannersActivityFragment(HotmobDemoBannersActivityFragment.DisplayType.DisplayTypeImage):mHotmobDemoBannersActivityFragment ,"A")
                        .commit();
                break;
            case "b":
                if(index == 1) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 1;
                mHotmobDemoBannersActivityFragment = (HotmobDemoBannersActivityFragment)fragmentManager.findFragmentByTag("B");
                fragmentManager.beginTransaction()
                        .replace(R.id.content, mHotmobDemoBannersActivityFragment == null ?new HotmobDemoBannersActivityFragment(HotmobDemoBannersActivityFragment.DisplayType.DisplayTypeHTML):mHotmobDemoBannersActivityFragment ,"B")
                        .commit();
                break;
            case "c":
                break;
            case "d":
                if(index == 3) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 3;

                break;
            case "e":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "f":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "g":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "h":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "i":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "j":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "k":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "l":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "m":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "n":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "o":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            case "p":
                if(index == 0) {
                    ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
                    return true;
                }
                index = 0;
                break;
            default:
                break;

        }
        ((HotmobDemoMainActivity)getActivity()).getSlidingMenu().toggle();
        return false;
    }
}
