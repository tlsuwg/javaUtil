/**
 * 
 */
package com.example.com.suyi.phone.call;

import java.util.LinkedList;

import android.os.Environment;

import com.example.com.suyi.domain.SendPhoneErr;
import com.example.com.suyi.phone.call.util.FileUtile;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author "suwg" 2016��5��27��
 */
public class PhoneLogCollection {
	LinkedList<String> list = new LinkedList<>();
	
	String flag="--&----------------------------------------------&--";

	public PhoneLogCollection() {
		super();
		// TODO Auto-generated constructor stub
		list.add(flag);
	}



	/**
	 * @param line
	 */
	public void addLog(String line) {
		// TODO Auto-generated method stub
		list.add(line);
	}

	/**
	 * @param externalStorageDirectory
	 * @param string
	 */
	public void sendToServer() {
		// TODO Auto-generated method stub
		Su.LogW("log  sendToServer");
		list.add(flag);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				 FileUtile.saveStrings(list,
				 Environment.getExternalStorageDirectory(),
				 "phoneSimLog.pho");
				 
				 Su.LogW("�洢��SD�� phoneSimLog.pho");

//				String info = StringUtile.getClearList(list);
//				try {
//					throw new SendPhoneErr(info);
//				} catch (Exception e) {
//					CrashReport.postCatchedException(e);
//					Su.LogW("���͵�Phonelog:" + info);
//				}

				

			}
		}).start();
	}

}
