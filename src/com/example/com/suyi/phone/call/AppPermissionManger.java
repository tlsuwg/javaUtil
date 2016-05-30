/**
 * 
 */
package com.example.com.suyi.phone.call;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.example.com.suyi.domain.PermissionName;
import com.example.com.suyi.domain.SuContextErrException;
import com.example.com.suyi.phone.call.app.CallApp;

/**
 * @author "suwg" 2016年5月30日
 */
public class AppPermissionManger {

	public static final String READ_LOGS_PERMISSION = "android.permission.READ_LOGS";

	Context mContext;

	private volatile static AppPermissionManger singleton;

	private AppPermissionManger() {
		mContext = CallApp.getInstance();
	}

	public static AppPermissionManger getSingleton() {
		if (singleton == null) {
			synchronized (AppPermissionManger.class) {
				if (singleton == null) {
					singleton = new AppPermissionManger();
				}
			}
		}
		return singleton;
	}

	public void getAppPemission() {

		PackageManager pm = mContext.getPackageManager();
		PackageInfo info;
		try {
			info = pm.getPackageInfo("com.android.mms",
					PackageManager.GET_PERMISSIONS);
			// String result = null;
			String[] packagePermissions = info.requestedPermissions;
			if (packagePermissions != null) {
				for (int i = 0; i < packagePermissions.length; i++) {
					Log.v("result", packagePermissions[i]);
				}
			} else {
				Log.v("name", info.packageName + ": no permissions");
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	String pas[] = new String[] { 
			"android.permission.READ_PHONE_STATE",
			"android.permission.PROCESS_OUTGOING_CALLS",
			"android.permission.CALL_PHONE",
			"android.permission.READ_CONTACTS", "android.permission.SEND_SMS",
			"android.permission.INTERNET",
			"android.permission.WRITE_EXTERNAL_STORAGE",
			"android.permission.ACCESS_NETWORK_STATE",
			"android.permission.ACCESS_WIFI_STATE",
			"android.permission.VIBRATE", "android.permission.WAKE_LOCK",
			"android.permission.MODIFY_AUDIO_SETTINGS",
			"android.permission.RECORD_AUDIO" };

	public void checkPermissionGranted() throws SuContextErrException {
		for (String pa : pas) {
			String name = PermissionName.getName(pa);
			boolean ishas = checkPermissionGranted(name);
			if (!ishas)
				throw new SuContextErrException("权限缺失：" + name);
		}
	}

	boolean checkPermissionGranted(String info) {
		PackageManager pm = CallApp.getInstance().getPackageManager();
		return pm.checkPermission(info, CallApp.getInstance().getPackageName()) == PackageManager.PERMISSION_GRANTED;
	}

	public boolean isReadLogsPermissionGranted() {

		return checkPermissionGranted(READ_LOGS_PERMISSION);

	}

}
