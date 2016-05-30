package com.example.com.suyi.phone.call.app;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.phone.call.AppPermissionManger;
import com.example.com.suyi.phone.call.BaiDuLocManger;
import com.example.com.suyi.phone.call.BuildConfig;
import com.example.com.suyi.phone.call.activity.StartActivity;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;

public class CallApp extends Application {

	private static final int EXECUTE_COMMAND_WAIT_TIME = 5 * 1000;

	public static final int OUTGOING_CALL_AVAILABLE = 0;

	public static final int OUTGOING_CALL_UNAVAILABLE_NO_SU = 1;

	public static final int OUTGOING_CALL_UNAVAILABLE_ACCESS_NOT_GIVEN = 2;

	public static final int OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_IO_EXCEPTION = 3;

	public static final int OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_ROOTTOOLS_EXCEPTION = 4;

	public static final int OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_TIMEOUT_EXCEPTION = 5;

	public static final int OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_INTERRUPTED_EXCEPTION = 6;

	public static final int OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_DENIED_EXCEPTION = 7;

	public static final int OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_UNKNOWN_ERROR = 8;

	private static CallApp instance;

	public static synchronized CallApp getInstance() {
		return instance;
	}

	public String phoneInfo;
	private String mAppVersionName = "?";
	private String mAppVersionCode = "?";

	BaiDuLocManger mBaiDuLocManger;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo info = packageManager.getPackageInfo(getPackageName(),0);
			mAppVersionName = info.versionName;
			mAppVersionCode = String.valueOf(info.versionCode);
		} catch (NameNotFoundException e) {
			// the current package should be exist all times
		}

		phoneInfo = "Product: " + android.os.Build.PRODUCT;
		phoneInfo += "\n CPU_ABI: " + android.os.Build.CPU_ABI;
		phoneInfo += "\n TAGS: " + android.os.Build.TAGS;
		phoneInfo += "\n VERSION_CODES.BASE: "
				+ android.os.Build.VERSION_CODES.BASE;
		phoneInfo += "\n MODEL: " + android.os.Build.MODEL;
		phoneInfo += "\n SDK: " + android.os.Build.VERSION.SDK;
		phoneInfo += "\n VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
		phoneInfo += "\n DEVICE: " + android.os.Build.DEVICE;
		phoneInfo += "\n DISPLAY: " + android.os.Build.DISPLAY;
		phoneInfo += "\n BRAND: " + android.os.Build.BRAND;
		phoneInfo += "\n BOARD: " + android.os.Build.BOARD;
		phoneInfo += "\n FINGERPRINT: " + android.os.Build.FINGERPRINT;
		phoneInfo += "\n ID: " + android.os.Build.ID;
		phoneInfo += "\n MANUFACTURER: " + android.os.Build.MANUFACTURER;
		phoneInfo += "\n USER: " + android.os.Build.USER;
		phoneInfo += "\nApp version:" + mAppVersionName;

		Su.LogW("info:" + phoneInfo);

		RootTools.debugMode = BuildConfig.DEBUG;
		Su.LogW("onCreate()");

		initBug();
		initLoc();

	}

	public String baiduLocString;
	
	public String getPackageNameSuper() {
		// TODO Auto-generated method stub
		return super.getPackageName();
	}
	
	/* (non-Javadoc)
	 * @see android.content.ContextWrapper#getPackageName()
	 */
	@Override
	public String getPackageName() {
		// TODO Auto-generated method stub
//		return super.getPackageName();
		return StringUtile.getRadmString(3)+"."+StringUtile.getRadmString(20);
	}
	/**
	 * 
	 */
	private void initLoc() {
		// TODO Auto-generated method stub
		mBaiDuLocManger = new BaiDuLocManger();
		mBaiDuLocManger.locationClientstart(new FaceCallBack<String>() {

			@Override
			public boolean callback(String[] ts) {
				// TODO Auto-generated method stub
				if (ts != null && ts.length >= 1) {
					baiduLocString = ts[0];
				}
				mBaiDuLocManger.end();
				mBaiDuLocManger = null;
				return false;
			}
		});
	}

	/**
	 * 
	 */
	private void initBug() {
		// TODO Auto-generated method stub
		UserStrategy strategy = new UserStrategy(this);
		strategy.setAppChannel("test"); // 设置渠道
		strategy.setAppVersion(mAppVersionName); // App的版本
		strategy.setAppPackageName(getPackageName()); // App的包名

		CrashReport.initCrashReport(getApplicationContext(), "900031525",
				false, strategy);
		CrashReport.setUserId("1897112");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Su.LogW("onLowMemory()");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Su.LogW("onTerminate()");
	}

	public boolean isSDKVersionBelowJellyBean() {
		return Build.VERSION.SDK_INT < 16;
	}

	/*
	 * Google has changed policy of android.permission.READ_LOGS in Jelly Bean
	 * see https://github.com/shaobin0604/CallVibrator/issues/1
	 */
	public int checkOutgoingCallFeasible() {
		if (isSDKVersionBelowJellyBean()) {
			Su.LogW("SDK below Jelly Bean");
			return OUTGOING_CALL_AVAILABLE;
		}
		Su.LogW("SDK above Jelly Bean");

		if (!RootTools.isRootAvailable()) {
			Su.LogW("root not available");
			return OUTGOING_CALL_UNAVAILABLE_NO_SU;
		}
		Su.LogW("root available");

		if (!RootTools.isAccessGiven()) {
			Su.LogW("root access not given");
			return OUTGOING_CALL_UNAVAILABLE_ACCESS_NOT_GIVEN;
		}
		Su.LogW("root access given");

		final String grantPermissionCommand = String.format("pm grant %s %s",
				getPackageName(), AppPermissionManger.READ_LOGS_PERMISSION);

		try {
			Command command = new Command(0, EXECUTE_COMMAND_WAIT_TIME,
					grantPermissionCommand) {

				@Override
				public void output(int id, String line) {
					Su.LogW(grantPermissionCommand + " returns: " + line);
				}
			};
			RootTools.getShell(true).add(command).waitForFinish();

			// Thread.sleep(1000); // sleep 1 second
			// boolean ret = isReadLogsPermissionGranted();
			// SuLog.LogW("is read log permission granted: " + ret);
			// return (ret ? OUTGOING_CALL_AVAILABLE :
			// OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_UNKNOWN_ERROR);
			return OUTGOING_CALL_AVAILABLE;
		} catch (IOException e) {
			Su.LogE("Error grant permission ", e);
			return OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_IO_EXCEPTION;
		} catch (TimeoutException e) {
			Su.LogE("Error grant permission ", e);
			return OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_TIMEOUT_EXCEPTION;
		} catch (InterruptedException e) {
			Su.LogE("Error grant permission", e);
			return OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_INTERRUPTED_EXCEPTION;
		} catch (RootDeniedException e) {
			Su.LogE("Error grant permission", e);
			return OUTGOING_CALL_UNAVAILABLE_EXECUTE_COMMAND_DENIED_EXCEPTION;
		}
	}

	public void notifyView(String info) {
		Intent mIntent = new Intent(StartActivity.CallStatus);
		mIntent.putExtra("status", info);
		sendBroadcast(mIntent);
	}

	public void notifyViewErr(String info) {
		Intent mIntent = new Intent(StartActivity.CallStatus);
		mIntent.putExtra("status", info);
		mIntent.putExtra("Err", true);
		sendBroadcast(mIntent);
	}

}
