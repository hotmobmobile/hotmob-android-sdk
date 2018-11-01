Hotmob
======

Mobile Advertising with Hotmob, the first and largest mobile ad network in Hong Kong, where monetizes the mobile taffic of the top ranked publishers into revenue and meanwhile connects advertisers to target audience effectively.

Visit http://www.hot-mob.com/ for more details.

How To Get Started
------------------

* [Download Hotmob Android SDK](https://github.com/hotmobmobile/hotmob-android-sdk/archive/master.zip) and try out the included sample project
* Read the ["Getting Started" guide](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/Getting-Started), ["Overview"](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/Overview) , or [other articles on the Wiki](https://github.com/hotmobmobile/hotmob-android-sdk/wiki)
* Check out the [documentation](https://github.com/hotmobmobile/hotmob-android-sdk/wiki) for a comprehensive look at all of the APIs available in Hotmob SDK
* Read the [Hotmob SDK 4.0 Migration Guide](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/HotmobSDK-4.0-Migration-Guide) for an overview of the architectural changes from 3.0 or below

Integration 
---------------

### Android Studio

1. Add Hotmob repositories in your project gradle.
```groovy
repositories {
    maven {
        credentials {
            username <Hotmob_provided_username>
            password <Hotmob_provided_password>
        }
        url "http://sdk.hot-mob.com/artifactory/libs-release-local"
    }
}
```
You need to request for unique credentials for Hotmob repositories per application.

2. Import HotmobSDK dependency.
```groovy
dependencies {
    compile 'com.hotmob.sdk:hotmob_android_sdk:4.6.1'
}
```

#### Migrating to HotmobSDK 4.6

If you are already using previous version of HotmobSDK, you need to do the following steps in order to upgrade your SDK to the latest 4.6.

1. Remove HotmobSDK module and add HotmobSDK dependency in Gradle.

2. Remove all HotmobSDK related activities in your Android Manifest.

After removing all Hotmob activities your Manifest should look like [this](https://github.com/hotmob-kenwong/hotmob-android-sdk/blob/master/AndroidStudio/Example/HotmobAndroidSDK4.0Example/HotmobAndroidSDKExample/src/main/AndroidManifest.xml).

### Eclipse

Eclipse implementation of Hotmob SDK is no longer supported. HotmobSDK.jar will be provided per request for Eclipse project integration.

Requirements
------------
| HotmobSDK Version     | Minimum Android Target         | Notes |
| --------              |---------                       |-------|
| 4.4.x                 | Android level 15               |   Data Collection<br />Enhanced Logging for debug   |
| 4.3.x                 | Android level 15               |   Remove Eclipse support<br />New ad exchange support   |
| 4.x                   | Android level 15               |   New advertisement format   |
| 3.x                   | Android level 9                |   Architecture optimization    |

## Implement

1. Start the HotmobSDK in first activity in your project.

``` java
//Start HotmobSDK service
HotmobManager.start(this);
//To enable debug mode on HotmobSDK
HotmobManager.setDebug(true);
```

2. Following the [BaseActivity.java](https://github.com/hotmob-kenwong/hotmob-android-sdk/blob/master/AndroidStudio/Example/HotmobAndroidSDK4.0Example/HotmobAndroidSDKExample/src/main/java/com/hotmob/android/example/BaseActivity.java) and [BaseFragment.java](https://github.com/hotmob-kenwong/hotmob-android-sdk/blob/master/AndroidStudio/Example/HotmobAndroidSDK4.0Example/HotmobAndroidSDKExample/src/main/java/com/hotmob/android/example/BaseFragment.java) in HotmobSDK example project to modify your base Activity and Fragment class.

If you don't have any base class, you can put all the required Hotmob lifecycle calling in your Activity and Fragment.

Please refer to [Getting Started](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/Getting-Started#integrate-hotmob-android-sdk-in-activity-based-application) for details.

> You can try to implement following code to confirm `HotmobSDK`  is functional in your project.
``` java
// Add to `onCreate` at Launcher activity in your project
HotmobManager.getPopup(this, listener, "launch_popup", "hotmob_uat_android_image_inapp_popup", true);
```

Basic Usage
-----------

### Popup
A Hotmob Launch Popup can be created by the following steps:

1. Create the HotmobManagerListener for the callback method.

```java
HotmobManagerListener listener = new HotmobManagerListener() {
     @Override
     public void didLoadBanner(View bannerView) {
     }
};
```

2. Request the HotmobPopup. Suggested to put it in the first show Activity.

```java
HotmobManager.getPopup(this, listener, identifier, adCode, true);
```

**Note:**

1. You can set any String value into ```identifier```. The value of ```identifier``` should be different for each popup/banner.  
2. For ```adCode```, please contact Hotmob to obtain suitable ad code.  

### Banner
To create the Hotmob Banner can refercence following step.

1. Create the HotmobManagerListener for the callback method.

```java
HotmobManagerListener listener = new HotmobManagerListener() {
     @Override
     public void didLoadBanner(View bannerView) {
         mBannerLayout.addView(bannerView);
     }
};
```

2. Request the HotmobBanner.

```java
HotmobManager.getBanner(this, listener, width, identifier, adCode);
```

**Note:**

1. You can set any String value into identifier. The value of identifier should be different for each popup/banner.  
2. For adCode, please contact Hotmob to obtain suitable ad code.  
3. For width, it is recommended to use HotmobManager.getScreenWidth(activity) if the banner width is equal to screen width. Otherwise, you should input a value in pixel.

```java
// Activity
HotmobManager.getBanner(this, listener, HotmobManager.getScreenWidth(this), identifier, adCode);

// Fragment
HotmobManager.getBanner(this, listener, HotmobManager.getScreenWidth(getActivity()), identifier, adCode);
```

3. Integrate callback method `didLoadBanner()`: This method will be called when the HotmobBanner ready. Add the banner into your banner container as a subview in order to display the banner.

```java
@Override
public void didLoadBanner(View bannerView) {
     mBannerLayout.addView(bannerView);
}
```

4. Integrate callback method `didHideBanner()`: This method will be called when the HotmobBanner was closed. You should modify your app layout to no HotmobBanner situation.

```java
@Override
public void didHideBanner(View bannerView) {
    // Add your implementation here.
}
```

5. Integrate callback method `onResizeBanner()`: This method will be called when the size of HotmobBanner was changed. You should modify your app layout to fulfill the new banner size.

```java
@Override
public void onResizeBanner(View bannerView) {
    // Add your implementation here.
}
```

**Note:**

Normally, you can set your banner container height to 'wrap_content', and Hotmob Banner will automactically do resizing for you.

```xml
<LinearLayout
    android:id="@+id/banner_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical" />
```

### Banner in ListView
To create the Hotmob Banner in ListView, please refer to the steps in [Banner](#banner), and add the following additional steps:

#### ListView

1. In your list item class, add a View to hold a banner and getBanner() in the initialization state of the class.

```java
public class ListViewListItem {
    public String name;
    public LinearLayout bannerLayout;   // a LinearLayout stored in list item to hold Hotmob banner view

    public ListViewListItem(String name) {
        this.name = name;
    }

    // create a constructor to getBanner() to store in the LinearLayout
    public ListViewListItem(Fragment rootElement, String identifier, String adCode) {
        bannerLayout = new LinearLayout(rootElement.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        bannerLayout.setLayoutParams(layoutParams);
        HotmobManagerListener listener = new HotmobManagerListener(){
            @Override
            public void didLoadBanner(View bannerView) {
                bannerLayout.addView(bannerView);
            }
        };
        HotmobManager.getBanner(rootElement, listener,
                HotmobManager.getScreenWidth(rootElement.getActivity()),
                identifier, adCode);
    }
}
```

You can use Activity in the rootElement if your ListView is inside an Activity.

2. In your Adapter, add logic to identify a banner item and toogle the banner view display.

```java
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    // Get the data item for this position
    ListViewListItem item = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_listview, parent, false);
    }
    if (item != null) {
        // Lookup view for data population
        TextView name = convertView.findViewById(R.id.name);
        ViewGroup bannerLayout = convertView.findViewById(R.id.banner_container);

        // check whether the item is a banner or not
        // first, remove all view in bannerLayout
        bannerLayout.removeAllViews();
        if (item.bannerLayout != null){
            // if item is a banner, hide all other view and add the banner
            name.setVisibility(GONE);
            bannerLayout.setVisibility(VISIBLE);
            bannerLayout.addView(item.bannerLayout);
        }else{
            // if item is not a banner, hide banner view and show normal content
            name.setVisibility(VISIBLE);
            bannerLayout.setVisibility(GONE);
            name.setText(item.name);
        }

    }
    // Return the completed view to render on screen
    return convertView;
}
```

3. Set OnScrollListener for the ListView, override `onScroll()` method to enable auto-pause feature for Video Banner.

```java
listView.setOnScrollListener(new AbsListView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        HotmobManager.updateBannerPosition(activity);
    }
});
```

**Note:**

Please refer to the exmaple app for more details on the implementation of Banner inside a [ListView](https://github.com/hotmob-kenwong/hotmob-android-sdk/tree/master/AndroidStudio/Example/HotmobAndroidSDK4.0Example/HotmobAndroidSDKExample/src/main/java/com/hotmob/android/example/listview). And also for [RecycleView](https://github.com/hotmob-kenwong/hotmob-android-sdk/tree/master/AndroidStudio/Example/HotmobAndroidSDK4.0Example/HotmobAndroidSDKExample/src/main/java/com/hotmob/android/example/recycleview) and [ScrollView](https://github.com/hotmob-kenwong/hotmob-android-sdk/tree/master/AndroidStudio/Example/HotmobAndroidSDK4.0Example/HotmobAndroidSDKExample/src/main/java/com/hotmob/android/example/srcollview).

---
Other usage can refercence to wiki [Integrating Banner](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/Integrating-Banner), [Integrating Popup](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/Integrating-Popup), [Integrating Native Video Ads](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/Integrating-Native-Video-Ads), [Integrating Third Party Ad Network](https://github.com/hotmobmobile/hotmob-android-sdk/wiki/Integrating-Third-Party-Ad-Network-for-Android).

Contact
-------
Website: [http://www.hot-mob.com](http://www.hot-mob.com/)
