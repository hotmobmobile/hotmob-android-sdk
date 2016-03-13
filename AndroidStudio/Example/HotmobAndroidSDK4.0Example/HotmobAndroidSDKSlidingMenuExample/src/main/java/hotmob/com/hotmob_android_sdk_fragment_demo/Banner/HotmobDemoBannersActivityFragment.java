package hotmob.com.hotmob_android_sdk_fragment_demo.Banner;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hotmob.sdk.handler.HotmobHandler;
import com.hotmob.sdk.manager.HotmobManager;
import com.hotmob.sdk.manager.HotmobManagerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hotmob.com.hotmob_android_sdk_fragment_demo.R;


public class HotmobDemoBannersActivityFragment extends Fragment {

    protected final String TAG = this.getClass().getName();

    private ArrayList<View> list;
    private ListView listView;

    private DisplayType displayType = DisplayType.DisplayTypeImage;

    public enum DisplayType{
        DisplayTypeUnknown, DisplayTypeImage, DisplayTypeHTML, DisplayTypeVideo;

        public static int intValue(DisplayType displayType) {
            switch (displayType) {
                case DisplayTypeImage: {
                    return 0;
                }
                case DisplayTypeHTML: {
                    return 1;
                }
                case DisplayTypeVideo: {
                    return 2;
                }
                default: {
                    return -1;
                }
            }
        }

        public static DisplayType valueOfInt(int value) {
            switch (value) {
                case 0:
                    return DisplayTypeImage;
                case 1:
                    return DisplayTypeHTML;
                case 2:
                    return DisplayTypeVideo;
                default:
                    return DisplayTypeUnknown;
            }
        }
    }

    public HotmobDemoBannersActivityFragment(){

    }

    public HotmobDemoBannersActivityFragment(DisplayType mDisplayType) {
        displayType = mDisplayType;
    }

    public void setDisplayType(DisplayType mDisplayType){
        displayType = mDisplayType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotmob_demo_banners_activity, container, false);

        listView = (ListView)view.findViewById(R.id.banners_list_view);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                updateBannerPosition();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HotmobManager.setCurrentFragment(this);
        this.createList();
        HotmobHandler.getInstance(this.getActivity()).onFragmentViewCreated();
    }

    private void updateBannerPosition(){
        HotmobManager.updateBannerPosition(this.getActivity());
    }

    private void createList() {
        String[] titles;
        String[] adCodes;

        if (displayType == DisplayType.DisplayTypeImage) {
            titles = new String[]{
                    "Direct Click to In-app Browser",
                    "Direct Click to External Browser",
                    "Direct Click to Google Play Store",
                    "Direct Click to Dial",
                    "Direct Click to SMS",
                    "Direct Click to E-mail",
                    "Direct Click to Video",
                    "Direct Click to Youtube",
                    "Direct Click to Internal Action"
            };

            adCodes = new String [] {
                    "hotmob_uat_android_image_inapp_banner",
                    "hotmob_uat_android_image_external_banner",
                    "hotmob_uat_android_image_playstore_banner",
                    "hotmob_uat_android_image_dial_banner",
                    "hotmob_uat_android_image_sms_banner",
                    "hotmob_uat_android_image_email_banner",
                    "hotmob_uat_android_image_video_banner",
                    "hotmob_uat_android_image_youtube_banner",
                    "hotmob_uat_android_image_internal_banner"
            };
        } else if (displayType == DisplayType.DisplayTypeHTML) {
            titles = new String[] {
                    "Motion 1",
                    "Motion 2",
                    "Motion 3",
                    "Celtra Campaign (Expand)",
                    "Celtra Campaign (Resize)",
                    "Sizmek Campaign",
            };

            adCodes = new String[] {
                    "hotmob_uat_android_html_motion_1_banner",
                    "hotmob_uat_android_html_motion_2_banner",
                    "hotmob_uat_android_html_motion_3_banner",
                    "hotmob_uat_android_html_celtra_banner",
                    "hotmob_uat_android_html_celtra_banner_2",
                    "hotmob_uat_android_html_sizmek_banner",

            };
        } else if (displayType == DisplayType.DisplayTypeVideo) {
            titles = new String[] {
                    "Video Ads",
                    "Direct Click to Google Play Store",
                    "Direct Click to Dial",
                    "Direct Click to SMS",
                    "Direct Click to E-mail",
                    "Direct Click to Video",
                    "Direct Click to Youtube",
            };

            adCodes = new String[] {
                    "hotmob_uat_android_video_inapp_banner",
                    "hotmob_uat_android_image_playstore_banner",
                    "hotmob_uat_android_image_dial_banner",
                    "hotmob_uat_android_image_sms_banner",
                    "hotmob_uat_android_image_email_banner",
                    "hotmob_uat_android_image_video_banner",
                    "hotmob_uat_android_image_youtube_banner",
            };
        } else {
            titles = new String[] {};
            adCodes = new String[] {};
        }

        if (list == null) {
            list = new ArrayList<>();
        }

        for (int i=0; i<titles.length; i++) {
            createListItem(titles[i], adCodes[i]);
        }

        CellArrayAdapter cellArrayAdapter = new CellArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(cellArrayAdapter);
    }

    private void createListItem(String title, String adCode) {
        //1.) Add Title View
        LayoutInflater titleInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = titleInflater.inflate(R.layout.list_title_cell_view, null);
        TextView cellTextView = (TextView)titleView.findViewById(R.id.list_cell_text_label);
//        cellTextView.setText(title);
        list.add(titleView);

        //2.) Add Banner View
        LayoutInflater bannerInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bannerView = bannerInflater.inflate(R.layout.list_banner_cell_view, null);
        final LinearLayout bannerLayout = (LinearLayout)bannerView.findViewById(R.id.list_banner_cell_layout);

        HotmobManagerListener listener = new HotmobManagerListener() {
            @Override
            public void didLoadBanner(View bannerView) {
                bannerLayout.addView(bannerView);
            }

            @Override
            public void openInternalCallback(View bannerView, String url) {
                super.openInternalCallback(bannerView, url);
                Toast.makeText(HotmobDemoBannersActivityFragment.this.getActivity(), "openInternalCallback", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResizeBanner(View bannerView) {
                Log.i(TAG, "onResizeBanner");
                super.onResizeBanner(bannerView);
            }

            // Please note:
            // isSoundEnable == true --> Unmute
            // isSoundEnable == false --> Mute
            public void hotmobBannerIsReadyChangeSoundSettings(boolean isSoundEnable) {
                if (isSoundEnable) {
//                    Toast.makeText(HotmobDemoBannersActivityFragment.this.getActivity(), "Banner Direct: Unmute!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(HotmobDemoBannersActivityFragment.this.getActivity(), "Banner Direct: Mute!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        HotmobManager.getBanner(this, listener, HotmobManager.getScreenWidth(getActivity()), adCode, adCode);
        list.add(bannerView);
    }

    private class CellArrayAdapter extends ArrayAdapter<View> {
        private HashMap<Integer, View> mIdMap;

        public CellArrayAdapter(Context context, int textViewResourceId,List<View> objects) {
            super(context, textViewResourceId, objects);

            mIdMap = new HashMap<>();

            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(i, objects.get(i));
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return mIdMap.get(position);
        }
    }
}
