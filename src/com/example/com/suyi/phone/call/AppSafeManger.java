/**
 * 
 */
package com.example.com.suyi.phone.call;

import android.content.Context;

import com.example.com.suyi.phone.call.app.CallApp;

/**
 * @author "suwg" 2016Äê5ÔÂ30ÈÕ
 */
public class AppSafeManger {

	private volatile static AppSafeManger singleton;
	Context mContext;
	private AppSafeManger() {
		mContext = CallApp.getInstance();
	}

	public static AppSafeManger getSingleton() {
		if (singleton == null) {
			synchronized (AppPermissionManger.class) {
				if (singleton == null) {
					singleton = new AppSafeManger();
				}
			}
		}
		return singleton;
	}
	
	
	

}
