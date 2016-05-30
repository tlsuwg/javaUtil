/**
 * 
 */
package com.example.com.suyi.phone.call.app;

import com.example.com.suyi.phone.call.CallManger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author "suwg" 2016Äê5ÔÂ16ÈÕ
 */
public class CallService extends Service {

	Context mContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */

	CallManger mCallManger;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this;

		mCallManger = new CallManger(mContext);
		mCallManger.onCreate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStart(android.content.Intent, int)
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		mCallManger.onStart(intent, startId);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mCallManger.onDestroy();
		super.onDestroy();
	}
	

	public static void stop(Context mContext2) {
		// TODO Auto-generated method stub
		CallApp.getInstance().stopService(new Intent(mContext2,CallService.class));
		
	}

}
