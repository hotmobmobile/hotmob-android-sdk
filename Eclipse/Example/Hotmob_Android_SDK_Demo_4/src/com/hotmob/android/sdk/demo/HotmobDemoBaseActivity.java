package com.hotmob.android.sdk.demo;

import com.hotmob.sdk.handler.HotmobHandler;

import android.app.Activity;

public class HotmobDemoBaseActivity extends Activity{
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		HotmobHandler.getInstance(this).onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		HotmobHandler.getInstance(this).onStop();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (!HotmobHandler.getInstance(this).hotmobBackButtonPressed()) {
			super.onBackPressed();
		}else{
			HotmobHandler.getInstance(this).onBackPressed();
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		HotmobHandler.getInstance(this).onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HotmobHandler.getInstance(this).onResume();
	}
}
