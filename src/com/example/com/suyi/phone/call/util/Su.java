/**
 * 
 */
package com.example.com.suyi.phone.call.util;

import android.util.Log;

import com.example.com.suyi.phone.call.app.CallApp;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author "suwg" 2016��5��17��
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
		BuglyLog.w("������Ϣ:", CallApp.getInstance().phoneInfo);
		BuglyLog.w("ϸ�ڱ�ǩ:", onlykey);
		BuglyLog.w("ϸ������", info);
		CrashReport.postCatchedException(e);
		LogE(info, e);
	}

	
}
