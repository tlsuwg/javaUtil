/**
 * 
 */
package com.example.com.suyi.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author "suwg"
 * 2016Äê5ÔÂ19ÈÕ
 */
public class SuActivity extends Activity {
	
	protected Context mContext;
	protected boolean isOnTop;
	protected Handler mHandler;
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isOnTop=true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		mHandler=new Handler();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isOnTop=false;
	}
}
