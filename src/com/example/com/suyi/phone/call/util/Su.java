/**
 * 
 */
package com.example.com.suyi.phone.call.util;

import android.util.Log;

import com.example.com.suyi.phone.call.app.CallApp;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author "suwg" 2016年5月17日
 */
public class Su {

	public static void LogW(String info) {
		Log.w("suwg", info);
	}
	
	public static void LogE(String info) {
		// TODO Auto-generated method stub
		Log.e("suwg", info);
	}

	/**
	 * @param string
	 * @param e
	 */
	public static void LogE(String string, Exception e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
	}

	public static void MonitoringException(Exception e, String onlykey,
			String info) {
		BuglyLog.w("主机信息:", CallApp.getInstance().phoneInfo);
		BuglyLog.w("细节标签:", onlykey);
		BuglyLog.w("细节内容", info);
		CrashReport.postCatchedException(e);
		LogE(info, e);
	}

	
}
